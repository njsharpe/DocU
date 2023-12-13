package net.njsharpe.docu.convert;

@FunctionalInterface
public interface TypeSerializer<T> {

    String serialize(T t);

}
