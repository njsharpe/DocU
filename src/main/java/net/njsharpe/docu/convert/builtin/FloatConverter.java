package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FloatConverter implements TypeConverter<Float> {

    @Getter(lazy = true)
    private static final FloatConverter instance = new FloatConverter();

    @Override
    public Float deserialize(String string) {
        return Make.tryGetOrDefault(() -> Float.parseFloat(string), null);
    }

    @Override
    public String serialize(Float f) {
        return Make.toSafeString(f);
    }

}
