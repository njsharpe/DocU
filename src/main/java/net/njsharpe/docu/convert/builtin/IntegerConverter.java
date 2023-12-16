package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IntegerConverter implements TypeConverter<Integer> {

    @Getter(lazy = true)
    private static final IntegerConverter instance = new IntegerConverter();

    @Override
    public Integer deserialize(String string) {
        return Make.tryGetOrDefault(() -> Integer.parseInt(string), null);
    }

    @Override
    public String serialize(Integer i) {
        return Make.toSafeString(i);
    }

}
