package net.njsharpe.docu.csv;

import net.njsharpe.docu.util.Make;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class CsvFileReader extends InputStreamReader {

    private final boolean hasHeaders;
    private final boolean skipHeaders;

    private final byte[] lineSeparatorBytes;

    private int index;

    public CsvFileReader(InputStream stream) {
        this(stream, false);
    }

    public CsvFileReader(InputStream stream, boolean hasHeaders) {
        this(stream, hasHeaders, true);
    }

    public CsvFileReader(InputStream stream, boolean hasHeaders, boolean skipHeaders) {
        super(stream);
        this.hasHeaders = hasHeaders;
        this.skipHeaders = skipHeaders;

        String ls = System.lineSeparator();
        // Windows: [0: 0x0D, 1: 0x0A]
        // Unix: [0: 0x0A]
        this.lineSeparatorBytes = Make.tryGetOrDefault(() ->
                ls.getBytes(this.getEncoding()), ls.getBytes());

        this.index = 0;
    }

    public Row readRow() throws IOException {
        Row row = this.readRowBytes();
        if(this.index == 1 && this.hasHeaders && this.skipHeaders) return this.readRow();
        return row;
    }

    private Row readRowBytes() throws IOException {
        List<Character> chars = new ArrayList<>();

        int c;
        // Initial check for EOF
        if(this.isEOF(c = this.read())) {
            return null;
        }

        // Read all characters until first char in line separator
        // Null byte check required for null terminated input
        while(c != this.lineSeparatorBytes[0] && !this.isEOF(c)) {
            chars.add((char) c);
            c = this.read();
        }

        // Remove potential extra char in line separator for Windows devices
        // Additional check if null terminated to not overflow with skip
        if(this.lineSeparatorBytes.length == 2 && !this.isEOF(c)) {
            this.skip(1);
        }

        this.index++;

        String content = chars.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        return Row.wrap(this.split(content, ','));
    }

    private boolean isEOF(int c) {
        return c == -1 || c == 0;
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

}