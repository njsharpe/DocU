package net.njsharpe.docu;

import net.njsharpe.docu.csv.CsvFileReader;
import net.njsharpe.docu.csv.Row;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CsvFileReaderTests extends CsvReaderData {

    private final String[][] people = new String[][] {
            new String[] { "1",     "Smith",        "John",     "",     "26",   ""  },
            new String[] { "2",     "Jobs",         "Steve",    "",     "55",   ""  },
            new String[] { "3",     "Robertson",    "David",    "G",    "43",   "1" },
            new String[] { "4",     "Robertson",    "Joshua",   "",     "19",   "1" }
    };

    @Test
    public void fromMemoryTest() {
        try(ByteArrayInputStream stream = new ByteArrayInputStream(this.data.getBytes(StandardCharsets.UTF_8));
            CsvFileReader csv = new CsvFileReader(stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void fromFileTest() {
        boolean isCi = Boolean.parseBoolean(System.getenv("CI"));
        // Skip running this test if the environment is being run in GitHub Actions
        // We cannot post a file within the running environment, for some reason?
        if(isCi) {
            return;
        }
        try(InputStream stream = this.getClass().getClassLoader().getResourceAsStream("data.csv");
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
                Assertions.assertEquals(this.people[index][row.cell()], cell);
            }
            index++;
        }
    }

}
