package net.njsharpe.docu.convert;

import lombok.experimental.UtilityClass;
import net.njsharpe.docu.convert.builtin.*;

import java.util.*;

/**
 * FOR INTERNAL USE ONLY. NON-DOCUMENTED UTILITY CLASS
 */
@UtilityClass
public final class Convert {

    /**
     * FOR INTERNAL USE ONLY. <br />
     * {@link Map} of {@link TypeConverter} to use to convert from a
     * {@link String} to its complex object type.
     */
    public static final Map<Class<?>, Class<? extends TypeConverter<?>>> STRING_TO_COMPLEX_MAP = new HashMap<>();

    /**
     * FOR INTERNAL USE ONLY. <br />
     * {@link Map} of {@link TypeConverter} to use to convert from a
     * {@link String} to its primitive object type.
     */
    public static final Map<Class<?>, Class<? extends TypeConverter<?>>> STRING_TO_PRIMITIVE_MAP = new HashMap<>();

    static {
        // Character skipped, custom logic required
        STRING_TO_COMPLEX_MAP.put(Byte.class, ByteConverter.class);
        STRING_TO_COMPLEX_MAP.put(Integer.class, IntegerConverter.class);
        STRING_TO_COMPLEX_MAP.put(Long.class, LongConverter.class);
        STRING_TO_COMPLEX_MAP.put(Short.class, ShortConverter.class);
        STRING_TO_COMPLEX_MAP.put(Float.class, FloatConverter.class);
        STRING_TO_COMPLEX_MAP.put(Double.class, DoubleConverter.class);
        STRING_TO_COMPLEX_MAP.put(Boolean.class, BooleanConverter.class);
        STRING_TO_COMPLEX_MAP.put(Character.class, CharacterConverter.class);

        // Character skipped, custom logic required
        STRING_TO_PRIMITIVE_MAP.put(byte.class, ByteConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(int.class, IntegerConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(long.class, LongConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(short.class, ShortConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(float.class, FloatConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(double.class, DoubleConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(boolean.class, BooleanConverter.class);
        STRING_TO_PRIMITIVE_MAP.put(char.class, CharacterConverter.class);
    }

}
