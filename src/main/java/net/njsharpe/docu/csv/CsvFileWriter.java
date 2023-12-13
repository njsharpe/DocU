package net.njsharpe.docu.csv;

import net.njsharpe.docu.util.Make;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class CsvFileWriter extends OutputStreamWriter {

    private final Charset charset;

    private final byte[] lineSeparatorBytes;

    public CsvFileWriter(OutputStream stream) {
        super(stream);
        this.charset = Make.tryGetOrDefault(() ->
                Charset.forName(this.getEncoding()), Charset.defaultCharset());

        // Windows: [0: 0x0D, 1: 0x0A]
        // Unix: [0: 0x0A]
        this.lineSeparatorBytes = System.lineSeparator().getBytes(this.charset);
    }

    public void writeRow(String... cells) throws IOException {
        this.writeRow(Row.wrap(cells));
    }

    public void writeRow(Row row) throws IOException {
        while(row.hasNext()) {
            String cell = row.next();

            if(cell != null) {
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
