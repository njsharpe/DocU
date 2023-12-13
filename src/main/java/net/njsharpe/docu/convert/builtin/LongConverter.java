package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LongConverter implements TypeConverter<Long> {

    @Getter(lazy = true)
    private static final LongConverter instance = new LongConverter();

    @Override
    public Long deserialize(String string) {
        return Long.parseLong(string);
    }

    @Override
    public String serialize(Long l) {
        return l == null ? null : l.toString();
    }

}
