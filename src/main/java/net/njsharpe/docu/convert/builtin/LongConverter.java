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
 * {@link Long} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LongConverter implements TypeConverter<Long> {

    @Getter(lazy = true)
    private static final LongConverter instance = new LongConverter();

    @Override
    @Nullable
    public Long deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Long.parseLong(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Long l) {
        return Make.toSafeString(l);
    }

}
