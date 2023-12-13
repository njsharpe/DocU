package net.njsharpe.docu.annotation;

import net.njsharpe.docu.convert.TypeDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Deserializer {

    Class<? extends TypeDeserializer<?>> value();

}
