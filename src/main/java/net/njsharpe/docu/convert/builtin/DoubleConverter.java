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
 * {@link Double} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoubleConverter implements TypeConverter<Double> {

    @Getter(lazy = true)
    private static final DoubleConverter instance = new DoubleConverter();

    @Override
    @Nullable
    public Double deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Double.parseDouble(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Double d) {
        return Make.toSafeString(d);
    }

}
