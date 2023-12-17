package net.njsharpe.docu.csv;

import net.njsharpe.docu.annotation.Converter;
import net.njsharpe.docu.annotation.Deserializer;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.convert.TypeDeserializer;
import net.njsharpe.docu.reflect.Reflection;
import net.njsharpe.docu.convert.Convert;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.*;
import java.util.Map;

/**
 * An extension of a {@link CsvFileReader} that allows for the reading of a CSV
 * file where each row is mapped to type {@code T}.
 *
 * @param <T> any non-generic object type
 */
public class TypedCsvFileReader<T> extends CsvFileReader {

    private final Class<T> type;
    private final Map<Integer, Field> columnMap;

    /**
     * Creates a new instance of a {@link TypedCsvFileReader}. This constructor
     * assumes that the input data does not have any headers at the top of the
     * file.
     *
     * @param type the class of type {@code T} to convert to
     * @param stream an {@link InputStream} to wrap
     */
    public TypedCsvFileReader(@NotNull Class<T> type, @NotNull InputStream stream) {
        this(type, stream, false);
    }

    /**
     * Creates a new instance of a {@link TypedCsvFileReader}. This constructor
     * assumes that the implementing code is expected to skip the header row at
     * the top of the file.
     *
     * @param type the class of type {@code T} to convert to
     * @param stream an {@link InputStream} to wrap
     * @param hasHeaders {@code true} if the input file has a header row
     */
    public TypedCsvFileReader(@NotNull Class<T> type, @NotNull InputStream stream, boolean hasHeaders) {
        // Force always skip headers for typed reads
        super(stream, hasHeaders, true);
        this.type = type;
        this.columnMap = ColumnMapper.buildColumnMapForType(type);
    }

    /**
     * Read the next row of the {@link InputStream} and convert to type
     * {@code T}.
     *
     * @return an instance of {@code T} that mimics the data in the row
     * @throws IOException if an I/O error occurs
     */
    @Nullable
    public T readRowAs() throws IOException {
        Row row = this.readRow();
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
                Class<? extends TypeDeserializer<?>> convOrDes = this.getConverterOrDeserializer(field);
                if(convOrDes != null) {
                    this.deserialize(t, field, cell, convOrDes);
                    continue;
                }

                if(type.isPrimitive()) {
                    Class<? extends TypeConverter<?>> converter = Convert.STRING_TO_PRIMITIVE_MAP.get(type);
                    this.deserialize(t, field, cell, converter);
                    continue;
                }

                // Escape early if string to string
                if(type == String.class) {
                    field.set(t, cell);
                    continue;
                }

                boolean converted = false;

                for(Map.Entry<Class<?>, Class<? extends TypeConverter<?>>> entry : Convert.STRING_TO_COMPLEX_MAP.entrySet()) {
                    if(entry.getKey() == type) {
                        this.deserialize(t, field, cell, entry.getValue());
                        converted = true;
                        break;
                    }
                }

                if(!converted) {
                    // Throw when could not convert for any other type
                    throw new IllegalStateException("Cannot convert from 'String' to '%s' without a converter!"
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

        return t;
    }

    @Nullable
    private Class<? extends TypeDeserializer<?>> getConverterOrDeserializer(@NotNull Field field) {
        if(field.isAnnotationPresent(Converter.class)) {
            return field.getAnnotation(Converter.class).value();
        }
        if(field.isAnnotationPresent(Deserializer.class)) {
            return field.getAnnotation(Deserializer.class).value();
        }
        return null;
    }

    private void deserialize(@NotNull T t, @NotNull Field field, @NotNull String value, @NotNull Class<? extends TypeDeserializer<?>> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Method convert = clazz.getDeclaredMethod("deserialize", String.class);
        Constructor<? extends TypeDeserializer<?>> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        TypeDeserializer<?> tc = constructor.newInstance();
        field.set(t, convert.invoke(tc, value));
    }

}
