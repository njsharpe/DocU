package net.njsharpe.docu;

import net.njsharpe.docu.annotation.Column;
import net.njsharpe.docu.annotation.Converter;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.reflect.MethodCallInfo;
import net.njsharpe.docu.reflect.Reflection;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypedCsvFileReader<T> extends CsvFileReader {

    private final Class<T> type;
    private final Map<Integer, Field> columnMap;

    private static final List<MethodCallInfo> STRING_TO_COMPLEX_LIST = new ArrayList<>();
    private static final Map<Class<?>, MethodCallInfo> STRING_TO_PRIMITIVE_MAP = new HashMap<>();
    static {
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Byte.class, "parseByte", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Integer.class, "parseInt", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Long.class, "parseLong", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Short.class, "parseShort", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Float.class, "parseFloat", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Double.class, "parseDouble", String.class));
        STRING_TO_COMPLEX_LIST.add(MethodCallInfo.of(Boolean.class, "parseBoolean", String.class));

        STRING_TO_PRIMITIVE_MAP.put(byte.class, MethodCallInfo.of(Byte.class, "parseByte", String.class));
        STRING_TO_PRIMITIVE_MAP.put(int.class, MethodCallInfo.of(Integer.class, "parseInt", String.class));
        STRING_TO_PRIMITIVE_MAP.put(long.class, MethodCallInfo.of(Long.class, "parseLong", String.class));
        STRING_TO_PRIMITIVE_MAP.put(short.class, MethodCallInfo.of(Short.class, "parseShort", String.class));
        STRING_TO_PRIMITIVE_MAP.put(float.class, MethodCallInfo.of(Float.class, "parseFloat", String.class));
        STRING_TO_PRIMITIVE_MAP.put(double.class, MethodCallInfo.of(Double.class, "parseDouble", String.class));
        STRING_TO_PRIMITIVE_MAP.put(boolean.class, MethodCallInfo.of(Boolean.class, "parseBoolean", String.class));
    }

    public TypedCsvFileReader(Class<T> type, Reader reader) {
        this(type, reader, false);
    }

    public TypedCsvFileReader(Class<T> type, Reader reader, boolean hasHeaders) {
        this(type, reader, hasHeaders, true);
    }

    public TypedCsvFileReader(Class<T> type, Reader reader, boolean hasHeaders, boolean skipHeaders) {
        super(reader, hasHeaders, skipHeaders);
        this.type = type;
        this.columnMap = this.buildColumnMap();
    }

    public T readAs() throws IOException {
        Row row = this.read();
        if(row == null || row.isEmpty()) return null;
        T t = Reflection.createInstanceFromNoArgsConstructor(this.type);

        try {
            while(row.hasNext()) {
                String cell = row.next();
                Field field = this.columnMap.get(row.cell());
                if(field == null) continue;
                field.setAccessible(true);

                Class<?> type = field.getType();

                // Parse converters first to allow primitive type overrides
                Converter converter = field.getAnnotation(Converter.class);
                if(converter != null) {
                    Class<? extends TypeConverter<?>> clazz = converter.value();
                    Method convert = clazz.getDeclaredMethod("convert", String.class);
                    Constructor<? extends TypeConverter<?>> constructor = clazz.getDeclaredConstructor();
                    TypeConverter<?> tc = constructor.newInstance();
                    field.set(t, convert.invoke(tc, cell));
                    continue;
                }

                if(type.isPrimitive()) {
                    // Custom conversion logic for char primitive, since it does not follow "parseX" convention
                    if(type == char.class) {
                        field.set(t, cell == null || cell.isBlank() ? Defaults.valueOf(type) : cell.charAt(0));
                        continue;
                    }
                    MethodCallInfo callInfo = STRING_TO_PRIMITIVE_MAP.get(type);
                    Method valueOf = callInfo.getSource().getDeclaredMethod(callInfo.getName(), callInfo.getParameters());
                    field.set(t, valueOf.invoke(null, callInfo.getParameters()[0].cast(cell.isBlank() ? Defaults.valueOf(type).toString() : cell)));
                    continue;
                }

                // Custom conversion logic for char primitive, since it does not follow "parseX" convention
                if(type == Character.class) {
                    field.set(t, cell == null || cell.isBlank() ? null : cell.charAt(0));
                    continue;
                }

                if(type == String.class) {
                    field.set(t, cell);
                    continue;
                }

                for(MethodCallInfo callInfo : STRING_TO_COMPLEX_LIST) {
                    if(callInfo.getSource() == type) {
                        Method valueOf = type.getDeclaredMethod(callInfo.getName(), callInfo.getParameters());
                        field.set(t, valueOf.invoke(null, callInfo.getParameters()[0].cast(cell)));
                        break;
                    }
                }

                // Throw when could not convert for any primitive type
                throw new IllegalStateException("Cannot convert from 'String' to '%s' without a converter!"
                        .formatted(field.getType().getSimpleName()));
            }
        } catch(InaccessibleObjectException ex) {
            throw new RuntimeException("Could not find or access field via reflection!", ex);
        } catch(NoSuchMethodException ex) {
            throw new RuntimeException("Could not find or access method via reflection!", ex);
        } catch(Exception ex) {
            throw new RuntimeException("Unknown error!", ex);
        }

        return t;
    }

    private Map<Integer, Field> buildColumnMap() {
        Map<Integer, Field> map = new HashMap<>();
        for(Field field : this.type.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if(column == null) continue;
            map.put(column.value(), field);
        }
        return map;
    }

}
