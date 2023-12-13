package net.njsharpe.docu.convert;

@FunctionalInterface
public interface TypeDeserializer<T> {

    T deserialize(String string);

}
