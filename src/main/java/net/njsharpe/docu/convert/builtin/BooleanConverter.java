package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BooleanConverter implements TypeConverter<Boolean> {

    @Getter(lazy = true)
    private static final BooleanConverter instance = new BooleanConverter();

    @Override
    public Boolean deserialize(String string) {
        return Boolean.parseBoolean(string);
    }

    @Override
    public String serialize(Boolean b) {
        return b == null ? null : b.toString();
    }

}
