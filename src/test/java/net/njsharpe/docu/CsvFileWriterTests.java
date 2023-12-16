package net.njsharpe.docu;

import net.njsharpe.docu.csv.CsvFileWriter;
import net.njsharpe.docu.csv.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CsvFileWriterTests extends CsvWriterData {

    @Test
    public void toMemoryTest() {
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

    @Test
    public void toFileTest() throws IOException {
        boolean isCi = Boolean.parseBoolean(System.getenv("CI"));
        // Skip running this test if the environment is being run in GitHub Actions
        // We cannot post a file within the running environment, for some reason?
        if(isCi) {
            return;
        }
        File file = Files.createTempFile("raw", ".csv").toFile();
        try(FileOutputStream stream = new FileOutputStream(file);
            CsvFileWriter csv = new CsvFileWriter(stream)) {
            for(Row row : this.data) {
                csv.writeRow(row);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            Assertions.assertTrue(file.exists());
            Assertions.assertEquals(this.expected.length(), file.length());
        }
    }

}
