package net.njsharpe.docu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvFileWriterTests extends CsvWriterData {

    @Test
    public void check() {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream();
            CsvFileWriter csv = new CsvFileWriter(stream)) {
            for(Row row : this.data) {
                csv.writeRow(row);
            }
            Assertions.assertEquals(this.expected, stream.toString(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
