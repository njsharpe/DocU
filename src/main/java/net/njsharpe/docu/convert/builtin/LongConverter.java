package net.njsharpe.docu.convert.builtin;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.njsharpe.docu.convert.TypeConverter;
import net.njsharpe.docu.util.Make;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LongConverter implements TypeConverter<Long> {

    @Getter(lazy = true)
    private static final LongConverter instance = new LongConverter();

    @Override
    public Long deserialize(String string) {
        return Make.tryGetOrDefault(() -> Long.parseLong(string), null);
    }

    @Override
    public String serialize(Long l) {
        return Make.toSafeString(l);
    }

}
