package net.njsharpe.docu.convert;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A functional interface used to define custom serialization logic from type
 * {@code T} to a {@link String}.
 *
 * @param <T> any non-generic object type
 */
@FunctionalInterface
public interface TypeSerializer<T> {

    /**
     * Implementation logic to convert from type {@code T} to a {@link String}.
     *
     * @param t an instance of type {@code T}
     * @return the serialized {@link String} for type {@code T}
     */
    @NotNull
    String serialize(@Nullable T t);

}
