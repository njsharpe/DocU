package net.njsharpe.docu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CsvFileReaderTests extends CsvFileData {

    private final String[][] people = new String[][] {
            new String[] { "1",     "Smith",        "John",     "",     "26",   ""  },
            new String[] { "2",     "Jobs",         "Steve",    "",     "55",   ""  },
            new String[] { "3",     "Robertson",    "David",    "G",    "43",   "1" },
            new String[] { "4",     "Robertson",    "Joshua",   "",     "19",   "1" }
    };

    @Test
    public void check() {
        try(InputStream stream = new ByteArrayInputStream(this.data.getBytes(StandardCharsets.UTF_8));
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(stream));
            CsvFileReader csv = new CsvFileReader(reader, false)) {
            Row row;
            int index = 0;
            while((row = csv.read()) != null) {
                while(row.hasNext()) {
                    String cell = row.next();
                    Assertions.assertEquals(this.people[index][row.cell()], cell);
                }
                index++;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
