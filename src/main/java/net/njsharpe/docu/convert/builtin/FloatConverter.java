package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FloatConverter implements TypeConverter<Float> {

    @Getter(lazy = true)
    private static final FloatConverter instance = new FloatConverter();

    @Override
    public Float deserialize(String string) {
        return Float.parseFloat(string);
    }

    @Override
    public String serialize(Float f) {
        return f == null ? null : f.toString();
    }

}
