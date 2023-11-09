package net.njsharpe.docu;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

public class CsvFileReader implements Closeable {

    private final Reader reader;
    private final boolean hasHeaders;
    private final boolean skipHeaders;

    private int cursor;

    public CsvFileReader(Reader reader) {
        this(reader, false);
    }

    public CsvFileReader(Reader reader, boolean hasHeaders) {
        this(reader, hasHeaders, true);
    }

    public CsvFileReader(Reader reader, boolean hasHeaders, boolean skipHeaders) {
        this.reader = reader;
        this.hasHeaders = hasHeaders;
        this.skipHeaders = skipHeaders;
        this.cursor = 0;
    }

    public Row read() throws IOException {
        Row row = this.readRow();
        if(this.cursor == 1 && this.hasHeaders && this.skipHeaders) return this.read();
        return row;
    }

    private Row readRow() throws IOException {
        List<Character> chars = new ArrayList<>();

        int c;
        if((c = this.reader.read()) == -1) {
            return null;
        }

        // Read all characters until first \n (0x0A)
        while(c != 0x0A) {
            chars.add((char) c);
            c = this.reader.read();
        }

        this.cursor++;

        // Remove final \r (0x0D) byte for windows-based files
        if(chars.get(chars.size() - 1) == 0x0D) {
            chars.remove(chars.size() - 1);
        }

        String content = chars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        return new Row(this.split(content, ','));
    }

    private String[] split(String string, char delimiter) {
        List<String> parts = new ArrayList<>();
        List<Character> collector = new ArrayList<>();

        boolean quoted = false;

        if(string == null) return new String[0];

        for(char c : string.toCharArray()) {
            if(c == '"') {
                quoted = !quoted;
                continue;
            }
            if(c == delimiter) {
                if(quoted) {
                    collector.add(c);
                    continue;
                }
                parts.add(collector.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining()));
                collector.clear();
                continue;
            }

            collector.add(c);
        }

        if(!collector.isEmpty()) {
            parts.add(collector.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining()));
        }

        return parts.toArray(new String[0]);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

}