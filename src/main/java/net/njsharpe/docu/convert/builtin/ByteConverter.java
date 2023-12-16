package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ByteConverter implements TypeConverter<Byte> {

    @Getter(lazy = true)
    private static final ByteConverter instance = new ByteConverter();

    @Override
    public Byte deserialize(String string) {
        return Make.tryGetOrDefault(() -> Byte.parseByte(string), null);
    }

    @Override
    public String serialize(Byte b) {
        return Make.toSafeString(b);
    }

}
