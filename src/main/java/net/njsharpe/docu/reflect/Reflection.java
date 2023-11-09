package net.njsharpe.docu.reflect;

import java.lang.reflect.Constructor;

public class Reflection {

    public static <T> T createInstanceFromNoArgsConstructor(Class<T> type) {
        T t;
        try {
            Constructor<T> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            t = constructor.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException("Type must have a no-args constructor!");
        }
        return t;
    }

}
