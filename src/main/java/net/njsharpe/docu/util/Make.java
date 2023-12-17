package net.njsharpe.docu.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * FOR INTERNAL USE ONLY <br />
 * Simple utility class to assist with the creation of objects involving a
 * generic type {@code T}.
 */
@UtilityClass
public final class Make {

    /**
     * A helper method used to attempt to create type {@code T} from within
     * a {@link ThrowingSupplier}. If the code in {@code supplier} throws an
     * {@link Exception}, return the value specified in the {@code def}
     * parameter.
     *
     * @param supplier a {@link ThrowingSupplier} for type {@code T}
     * @param def the default value to return if {@code supplier} throws
     * @return a nullable instance of type {@code T}
     * @param <T> any non-generic object type
     */
    @Nullable
    public static <T> T tryGetOrDefault(ThrowingSupplier<T> supplier, @Nullable T def) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            return def;
        }
    }

    /**
     * A helper method used to convert a nullable instance of type {@code T} to
     * a never-null {@link String}.
     *
     * @param t a nullable instance of {@code T}
     * @return the value of {@code T}, or an empty {@link String}
     * @param <T> any non-generic object type
     */
    @NotNull
    @Contract("_ -> !null")
    public static <T> String toSafeString(@Nullable T t) {
        return t == null ? "" : t.toString();
    }

}
