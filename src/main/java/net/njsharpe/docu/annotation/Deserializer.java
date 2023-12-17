package net.njsharpe.docu.annotation;

import net.njsharpe.docu.convert.TypeDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to define a {@link TypeDeserializer} for a field. Used
 * for custom deserialization logic of non-standard field types.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Deserializer {

    /**
     * @return the {@link TypeDeserializer} class to use for this field
     */
    Class<? extends TypeDeserializer<?>> value();

}
