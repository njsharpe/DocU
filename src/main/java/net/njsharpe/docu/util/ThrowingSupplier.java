package net.njsharpe.docu.util;

@FunctionalInterface
public interface ThrowingSupplier<T> {

    T get() throws Exception;

}
