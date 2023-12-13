package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CharacterConverter implements TypeConverter<Character> {

    @Getter(lazy = true)
    private static final CharacterConverter instance = new CharacterConverter();

    @Override
    public Character deserialize(String string) {
        return string.isEmpty() ? null : string.charAt(0);
    }

    @Override
    public String serialize(Character c) {
        return c == null ? null : c.toString();
    }

}
