package net.njsharpe.docu.csv;

import net.njsharpe.docu.util.Make;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An {@link InputStreamReader} wrapper designed specifically for use in
 * reading CSV files. Data read from CSV files are outputted as {@link Row}
 * objects to be iterated through by any implementing code.
 */
public class CsvFileReader extends InputStreamReader {

    private final boolean hasHeaders;
    private final boolean skipHeaders;

    private final byte[] lineSeparatorBytes;

    private int index;

    /**
     * Creates a new instance of a {@link CsvFileReader}. This constructor
     * assumes that the input data does not have any headers at the top of the
     * file.
     *
     * @param stream an {@link InputStream} to wrap
     */
    public CsvFileReader(@NotNull InputStream stream) {
        this(stream, false);
    }

    /**
     * Creates a new instance of a {@link CsvFileReader}. This constructor
     * assumes that the implementing code is expected to skip the header row at
     * the top of the file.
     *
     * @param stream an {@link InputStream} to wrap
     * @param hasHeaders {@code true} if the input file has a header row
     */
    public CsvFileReader(@NotNull InputStream stream, boolean hasHeaders) {
        this(stream, hasHeaders, true);
    }

    /**
     * Creates a new instance of a {@link CsvFileReader}.
     *
     * @param stream an {@link InputStream} to wrap
     * @param hasHeaders {@code true} if the input file has a header row
     * @param skipHeaders {@code true} if the reader should skip the header row
     */
    public CsvFileReader(@NotNull InputStream stream, boolean hasHeaders, boolean skipHeaders) {
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

    /**
     * Read the next row of the {@link InputStream}.
     *
     * @return a {@link Row} object defining the structure of this row
     * @throws IOException if an I/O error occurs
     */
    @Nullable
    public Row readRow() throws IOException {
        Row row = this.readRowBytes();
        if(this.index == 1 && this.hasHeaders && this.skipHeaders) return this.readRow();
        return row;
    }

    @Nullable
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

    @NotNull
    private String[] split(@Nullable String string, char delimiter) {
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