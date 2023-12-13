package net.njsharpe.docu.csv;

import net.njsharpe.docu.annotation.Column;
import net.njsharpe.docu.annotation.Converter;
import net.njsharpe.docu.annotation.Serializer;
import net.njsharpe.docu.convert.Convert;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.convert.TypeSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

public class TypedCsvFileWriter<T> extends CsvFileWriter implements CsvColumnMapper {

    private final Class<T> type;
    private final Map<Integer, Field> columnMap;

    public TypedCsvFileWriter(Class<T> type, OutputStream stream) {
        super(stream);
        this.type = type;
        this.columnMap = this.buildColumnMapForType(type);
    }

    public void writeRowAs(T t) throws IOException {
        if(t == null) {
            return;
        }

        String[] row = new String[this.columnMap.size()];
        try {
            for (Map.Entry<Integer, Field> entry : this.columnMap.entrySet()) {
                int index = entry.getKey();

                Field field = entry.getValue();
                if (field == null) continue;
                field.setAccessible(true);

                Class<?> type = field.getType();

                // Parse converters first to allow primitive type overrides
                Class<? extends TypeSerializer<?>> convOrSer = this.getConverterOrSerializer(field);
                if (convOrSer != null) {
                    row[index] = this.serialize(t, field, convOrSer);
                    continue;
                }

                if (type.isPrimitive()) {
                    Class<? extends TypeConverter<?>> serializer = Convert.STRING_TO_PRIMITIVE_MAP.get(type);
                    row[index] = this.serialize(t, field, serializer);
                    continue;
                }

                // Escape early if string to string
                if (type == String.class) {
                    row[index] = (String) field.get(t);
                    continue;
                }

                boolean converted = false;

                for (Map.Entry<Class<?>, Class<? extends TypeConverter<?>>> e : Convert.STRING_TO_COMPLEX_MAP.entrySet()) {
                    if (e.getKey() == type) {
                        row[index] = this.serialize(t, field, e.getValue());
                        converted = true;
                        break;
                    }
                }

                if(!converted) {
                    // Throw when could not convert for any other type
                    throw new IllegalStateException("Cannot convert from '%s' to 'String' without a converter!"
                            .formatted(field.getType().getSimpleName()));
                }
            }
        } catch(InaccessibleObjectException ex) {
            throw new RuntimeException("Could not find or access field via reflection!", ex);
        } catch(NoSuchMethodException ex) {
            throw new RuntimeException("Could not find or access method via reflection!", ex);
        } catch(Exception ex) {
            throw new RuntimeException("Unknown error, see inner error details!", ex);
        }

        this.writeRow(Row.wrap(row));
    }

    private Class<? extends TypeSerializer<?>> getConverterOrSerializer(Field field) {
        if(field.isAnnotationPresent(Converter.class)) {
            return field.getAnnotation(Converter.class).value();
        }
        if(field.isAnnotationPresent(Serializer.class)) {
            return field.getAnnotation(Serializer.class).value();
        }
        return null;
    }

    private String serialize(T t, Field field, Class<? extends TypeSerializer<?>> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method convert = clazz.getDeclaredMethod("serialize", Object.class);
        Constructor<? extends TypeSerializer<?>> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        TypeSerializer<?> tc = constructor.newInstance();
        return (String) convert.invoke(tc, field.get(t));
    }

}
