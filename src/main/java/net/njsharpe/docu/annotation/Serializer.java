package net.njsharpe.docu.annotation;

import net.njsharpe.docu.convert.TypeSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to define a {@link TypeSerializer} for a field. Used
 * for custom serialization logic of non-standard field types.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

    /**
     * @return the {@link TypeSerializer} class to use for this field
     */
    Class<? extends TypeSerializer<?>> value();

}
