package net.njsharpe.docu.util;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * A functional interface modeled after Java's {@link Supplier} interface
 * that allows for the execution of code that could throw an {@link Exception}.
 *
 * @param <T> any non-generic object type
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {

    /**
     * Implementation logic to get type {@code T}.
     *
     * @return an instance of type {@code T}
     * @throws Exception any uncaught {@link Exception}
     */
    @Nullable
    T get() throws Exception;

}
