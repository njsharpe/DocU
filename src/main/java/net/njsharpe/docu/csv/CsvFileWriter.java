package net.njsharpe.docu.csv;

import net.njsharpe.docu.util.Make;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * An {@link OutputStreamWriter} wrapper designed specifically for use in
 * writing CSV files. Data written to a CSV file in inputted as a {@link Row}
 * object or {@link String} array.
 */
public class CsvFileWriter extends OutputStreamWriter {

    private final Charset charset;

    private final byte[] lineSeparatorBytes;

    /**
     * Creates a new instance of a {@link CsvFileWriter}.
     *
     * @param stream an {@link OutputStream} to wrap
     */
    public CsvFileWriter(@NotNull OutputStream stream) {
        super(stream);
        this.charset = Objects.requireNonNull(Make.tryGetOrDefault(() ->
                Charset.forName(this.getEncoding()), Charset.defaultCharset()));

        // Windows: [0: 0x0D, 1: 0x0A]
        // Unix: [0: 0x0A]
        this.lineSeparatorBytes = System.lineSeparator().getBytes(this.charset);
    }

    /**
     * Write a row to the {@link OutputStream}. This method's functionality is
     * affected by any {@link java.nio.file.OpenOption} defined when creating
     * the stream.
     *
     * @param cells a {@link String} array defining the structure of the row
     * @throws IOException if an I/O error occurs
     */
    public void writeRow(@NotNull String... cells) throws IOException {
        this.writeRow(Row.wrap(cells));
    }

    /**
     * Write a row to the {@link OutputStream}. This method's functionality is
     * affected by any {@link java.nio.file.OpenOption} defined when creating
     * the stream.
     *
     * @param row a {@link Row} object defining the structure of the row
     * @throws IOException if an I/O error occurs
     */
    public void writeRow(@NotNull Row row) throws IOException {
        while(row.hasNext()) {
            String cell = row.next();

            if(!cell.isEmpty()) {
                // Cell contains a comma, must be quoted
                if(cell.chars().anyMatch(c -> c == ',')) {
                    String quoted = "\"%s\"".formatted(cell);
                    this.write(quoted);
                } else {
                    this.write(cell);
                }
            }

            if(row.hasNext()) {
                this.write(',');
            }
        }

        this.write(new String(this.lineSeparatorBytes, this.charset));
        this.flush();
    }

}
