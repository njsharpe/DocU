package net.njsharpe.docu.convert;

/**
 * A wrapper interface for {@link TypeSerializer} and {@link TypeDeserializer}.
 *
 * @param <T> any non-generic object type
 */
public interface TypeConverter<T> extends TypeSerializer<T>, TypeDeserializer<T> {
}
