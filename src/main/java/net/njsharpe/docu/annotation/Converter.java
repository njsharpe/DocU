package net.njsharpe.docu.annotation;

import net.njsharpe.docu.convert.TypeConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A helper annotation to define both the location of a {@link Deserializer}
 * and {@link Serializer} of a field, if they exist in the same class.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Converter {

    /**
     * @return the {@link TypeConverter} class to use for this field
     */
    Class<? extends TypeConverter<?>> value();

}
