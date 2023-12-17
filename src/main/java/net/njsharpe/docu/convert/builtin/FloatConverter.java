package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Default {@link TypeConverter} used by this library to convert to and from
 * {@link Float} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FloatConverter implements TypeConverter<Float> {

    @Getter(lazy = true)
    private static final FloatConverter instance = new FloatConverter();

    @Override
    @Nullable
    public Float deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Float.parseFloat(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Float f) {
        return Make.toSafeString(f);
    }

}
