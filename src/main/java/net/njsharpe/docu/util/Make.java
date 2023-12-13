package net.njsharpe.docu.util;

public class Make {

    public static <T> T tryGetOrDefault(ThrowingSupplier<T> supplier, T def) {
        try {
            return supplier.get();
        } catch (Exception ex) {
            return def;
        }
    }

}
