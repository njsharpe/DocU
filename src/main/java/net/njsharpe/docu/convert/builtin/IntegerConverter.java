package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegerConverter implements TypeConverter<Integer> {

    @Getter(lazy = true)
    private static final IntegerConverter instance = new IntegerConverter();

    @Override
    public Integer deserialize(String string) {
        return Integer.parseInt(string);
    }

    @Override
    public String serialize(Integer i) {
        return i == null ? null : i.toString();
    }

}
