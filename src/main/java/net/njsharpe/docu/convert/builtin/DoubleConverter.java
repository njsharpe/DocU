package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DoubleConverter implements TypeConverter<Double> {

    @Getter(lazy = true)
    private static final DoubleConverter instance = new DoubleConverter();

    @Override
    public Double deserialize(String string) {
        return Make.tryGetOrDefault(() -> Double.parseDouble(string), null);
    }

    @Override
    public String serialize(Double d) {
        return Make.toSafeString(d);
    }

}
