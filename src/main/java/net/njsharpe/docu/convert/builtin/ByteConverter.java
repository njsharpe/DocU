package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ByteConverter implements TypeConverter<Byte> {

    @Getter(lazy = true)
    private static final ByteConverter instance = new ByteConverter();

    @Override
    public Byte deserialize(String string) {
        return Byte.parseByte(string);
    }

    @Override
    public String serialize(Byte b) {
        return b == null ? null : b.toString();
    }

}
