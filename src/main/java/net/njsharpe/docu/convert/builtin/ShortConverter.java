package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShortConverter implements TypeConverter<Short> {

    @Getter(lazy = true)
    private static final ShortConverter instance = new ShortConverter();

    @Override
    public Short deserialize(String string) {
        return Short.parseShort(string);
    }

    @Override
    public String serialize(Short s) {
        return s == null ? null : s.toString();
    }

}
