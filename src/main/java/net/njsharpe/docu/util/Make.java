package net.njsharpe.docu.util;

import org.jetbrains.annotations.NotNull;

public class Make {

    public static <T> T tryGetOrDefault(ThrowingSupplier<T> supplier, T def) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            return def;
        }
    }

    @NotNull
    public static <T> String toSafeString(T t) {
        return t == null ? "" : t.toString();
    }

}
