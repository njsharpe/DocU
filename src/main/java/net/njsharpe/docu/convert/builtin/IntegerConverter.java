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
 * {@link Integer} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegerConverter implements TypeConverter<Integer> {

    @Getter(lazy = true)
    private static final IntegerConverter instance = new IntegerConverter();

    @Override
    @Nullable
    public Integer deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Integer.parseInt(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Integer i) {
        return Make.toSafeString(i);
    }

}
