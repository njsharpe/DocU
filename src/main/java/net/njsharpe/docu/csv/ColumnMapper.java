package net.njsharpe.docu.csv;

import lombok.experimental.UtilityClass;
import net.njsharpe.docu.annotation.Column;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * FOR INTERNAL USE ONLY <br />
 * Simple workaround to extract type mapping logic for XSV documents out to a
 * utility class. Should not be referenced outside of this library or package.
 */
@UtilityClass
final class ColumnMapper {

    /**
     * Builds a {@link Map} of column indices to {@link Field} types for use in
     * typed XSV file readers and writers.
     *
     * @param clazz class to create a map for
     * @return a {@link Map} for column indices to {@link Field} types
     * @param <T> generic type definition for the input class
     */
    @NotNull
    public static <T> Map<Integer, Field> buildColumnMapForType(@NotNull Class<T> clazz) {
        Map<Integer, Field> map = new HashMap<>();
        for(Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if(column == null) continue;
            map.put(column.value(), field);
        }
        return map;
    }

}
