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
 * {@link Boolean} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanConverter implements TypeConverter<Boolean> {

    @Getter(lazy = true)
    private static final BooleanConverter instance = new BooleanConverter();

    @Override
    @Nullable
    public Boolean deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Boolean.parseBoolean(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Boolean b) {
        return Make.toSafeString(b);
    }

}
