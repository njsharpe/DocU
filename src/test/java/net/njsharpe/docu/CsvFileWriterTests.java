package net.njsharpe.docu;

import net.njsharpe.docu.csv.CsvFileWriter;
import net.njsharpe.docu.csv.Row;
import net.njsharpe.docu.util.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CsvFileWriterTests {

    protected final Row[] write = {
            Row.wrap("1",   "Smith",        "John",         "",     "26",   ""),
            Row.wrap("2",   "Jobs",         "Steve",        "",     "55",   ""),
            Row.wrap("3",   "Robertson",    "David",        "G",    "43",   "1"),
            Row.wrap("4",   "Robertson",    "Joshua",       "",     "19",   "1")
    };

    protected final Row[] append = {
            Row.wrap("5",   "Davis",        "Nathaniel",    "",     "19",   ""),
            Row.wrap("6",   "Harding",      "Fredrick",     "M",    "73",   "2")
    };

    @Test
    public void toMemoryTest() {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream();
            CsvFileWriter csv = new CsvFileWriter(stream)) {
            for(Row row : this.write) {
                csv.writeRow(row);
            }
            Assertions.assertArrayEquals(InputOutput.EXAMPLE_FILE_NO_APPEND_BYTES, stream.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void toFileTest() throws IOException {
        Path path = InputOutput.createNewExampleFile();
        try(OutputStream stream = Files.newOutputStream(path);
            CsvFileWriter csv = new CsvFileWriter(stream)) {
            for(Row row : this.write) {
                csv.writeRow(row);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            Assertions.assertTrue(Files.exists(path));
            Assertions.assertEquals(InputOutput.EXAMPLE_FILE_NO_APPEND_BYTES.length, Files.size(path));
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void toFileAppendTest() throws IOException {
        Path path = InputOutput.createNewExampleFileAndPopulateWithAppend();
        try(OutputStream stream = Files.newOutputStream(path, StandardOpenOption.APPEND);
            CsvFileWriter csv = new CsvFileWriter(stream)) {
            for(Row row : this.append) {
                csv.writeRow(row);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            Assertions.assertTrue(Files.exists(path));
            Assertions.assertEquals(InputOutput.EXAMPLE_FILE_WITH_APPEND_BYTES.length, Files.size(path));
        }
    }

}
