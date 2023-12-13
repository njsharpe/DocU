package net.njsharpe.docu.csv;

import net.njsharpe.docu.annotation.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public interface CsvColumnMapper {

    default <T> Map<Integer, Field> buildColumnMapForType(Class<T> clazz) {
        Map<Integer, Field> map = new HashMap<>();
        for(Field field : clazz.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if(column == null) continue;
            map.put(column.value(), field);
        }
        return map;
    }

}
