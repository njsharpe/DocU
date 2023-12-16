package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterConverter implements TypeConverter<Character> {

    @Getter(lazy = true)
    private static final CharacterConverter instance = new CharacterConverter();

    @Override
    public Character deserialize(String string) {
        return Make.tryGetOrDefault(() -> string.charAt(0), null);
    }

    @Override
    public String serialize(Character c) {
        return Make.toSafeString(c);
    }

}
