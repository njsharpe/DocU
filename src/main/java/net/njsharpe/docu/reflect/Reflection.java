package net.njsharpe.docu.reflect;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * FOR INTERNAL USE ONLY <br />
 * Simple utility class to assist with the use of reflection.
 */
@UtilityClass
public final class Reflection {

    /**
     * A helper function that will attempt to create an instance of type
     * {@code T} from a defined or implicit no-args constructor. This will fail
     * if a no-args constructor could not be found.
     *
     * @param type class to create an instance from
     * @return an instance of {@code T}
     * @param <T> any non-generic object type
     */
    @NotNull
    public static <T> T createInstanceFromNoArgsConstructor(@NotNull Class<T> type) {
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
