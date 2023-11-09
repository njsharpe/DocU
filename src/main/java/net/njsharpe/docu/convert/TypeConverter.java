package net.njsharpe.docu.convert;

@FunctionalInterface
public interface TypeConverter<T> {

    T convert(String string);

}
