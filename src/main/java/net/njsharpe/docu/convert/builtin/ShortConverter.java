package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShortConverter implements TypeConverter<Short> {

    @Getter(lazy = true)
    private static final ShortConverter instance = new ShortConverter();

    @Override
    public Short deserialize(String string) {
        return Make.tryGetOrDefault(() -> Short.parseShort(string), null);
    }

    @Override
    public String serialize(Short s) {
        return Make.toSafeString(s);
    }

}
