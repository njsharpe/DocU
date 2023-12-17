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
 * {@link Character} types and {@link String} types.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterConverter implements TypeConverter<Character> {

    @Getter(lazy = true)
    private static final CharacterConverter instance = new CharacterConverter();

    @Override
    @Nullable
    public Character deserialize(@NotNull String string) {
        return Make.tryGetOrDefault(() -> string.charAt(0), null);
    }

    @Override
    @NotNull
    public String serialize(@Nullable Character c) {
        return Make.toSafeString(c);
    }

}
