package net.njsharpe.docu;

import net.njsharpe.docu.csv.CsvFileReader;
import net.njsharpe.docu.csv.Row;
import net.njsharpe.docu.util.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvFileReaderTests {

    private final String[][] read = new String[][] {
            new String[] { "1",     "Smith",        "John",     "",     "26",   ""  },
            new String[] { "2",     "Jobs",         "Steve",    "",     "55",   ""  },
            new String[] { "3",     "Robertson",    "David",    "G",    "43",   "1" },
            new String[] { "4",     "Robertson",    "Joshua",   "",     "19",   "1" }
    };

    @Test
    public void fromMemoryTest() {
        try(ByteArrayInputStream stream = new ByteArrayInputStream(InputOutput.EXAMPLE_FILE_NO_APPEND_BYTES);
            CsvFileReader csv = new CsvFileReader(stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void fromFileTest() throws IOException {
        Path path = InputOutput.createNewExampleFileAndPopulateNoAppend();
        try(InputStream stream = Files.newInputStream(path);
            CsvFileReader csv = new CsvFileReader(stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void assertContents(CsvFileReader csv) throws IOException {
        Row row;
        int index = 0;
        while((row = csv.readRow()) != null) {
            while(row.hasNext()) {
                String cell = row.next();
                Assertions.assertEquals(this.read[index][row.cell()], cell);
            }
            index++;
        }
    }

}
