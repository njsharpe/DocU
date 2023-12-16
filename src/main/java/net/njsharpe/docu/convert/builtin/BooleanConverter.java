package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanConverter implements TypeConverter<Boolean> {

    @Getter(lazy = true)
    private static final BooleanConverter instance = new BooleanConverter();

    @Override
    public Boolean deserialize(String string) {
        return Make.tryGetOrDefault(() -> Boolean.parseBoolean(string), null);
    }

    @Override
    public String serialize(Boolean b) {
        return Make.toSafeString(b);
    }

}
