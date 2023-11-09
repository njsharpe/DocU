package net.njsharpe.docu;

import java.lang.reflect.Array;

public class Defaults {

    @SuppressWarnings("unchecked")
    public static <T> T valueOf(Class<T> clazz) {
        return (T) Array.get(Array.newInstance(clazz, 1), 0);
    }

}
