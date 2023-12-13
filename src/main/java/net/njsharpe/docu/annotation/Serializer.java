package net.njsharpe.docu.annotation;

import net.njsharpe.docu.convert.TypeSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {

    Class<? extends TypeSerializer<?>> value();

}
