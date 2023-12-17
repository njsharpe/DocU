package net.njsharpe.docu.convert;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A functional interface used to define custom deserialization logic from a
 * {@link String} to type {@code T}.
 *
 * @param <T> any non-generic object type
 */
@FunctionalInterface
public interface TypeDeserializer<T> {

    /**
     * Implementation logic to convert from a {@link String} to type {@code T}.
     *
     * @param string the serialized {@link String} for type {@code T}
     * @return an instance of type {@code T}
     */
    @Nullable
    T deserialize(@NotNull String string);

}
