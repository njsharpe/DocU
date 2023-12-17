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
 * {@link Short} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShortConverter implements TypeConverter<Short> {

    @Getter(lazy = true)
    private static final ShortConverter instance = new ShortConverter();

    @Override
    @Nullable
    public Short deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> Short.parseShort(string), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Short s) {
        return Make.toSafeString(s);
    }

}
