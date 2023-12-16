package net.njsharpe.docu;

import net.njsharpe.docu.csv.TypedCsvFileReader;
import net.njsharpe.docu.dto.Person;
import net.njsharpe.docu.util.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TypedCsvFileReaderTests {

    private final Person[] read = new Person[] {
            new Person(1, "Smith", "John", 26),
            new Person(2, "Jobs", "Steve", 55),
            new Person(3, "Robertson", "David", 43) {{
                middleInitial = 'G';
                householdId = 1;
            }},
            new Person(4, "Robertson", "Joshua", 19) {{
                householdId = 1;
            }}
    };

    @Test
    public void fromMemoryTest() {
        try(ByteArrayInputStream stream = new ByteArrayInputStream(InputOutput.EXAMPLE_FILE_NO_APPEND_BYTES);
            TypedCsvFileReader<Person> csv = new TypedCsvFileReader<>(Person.class, stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void fromFileTest() throws IOException {
        Path path = InputOutput.createNewExampleFileAndPopulateNoAppend();
        try(InputStream stream = Files.newInputStream(path) ;
            TypedCsvFileReader<Person> csv = new TypedCsvFileReader<>(Person.class, stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> void assertContents(TypedCsvFileReader<T> csv) throws IOException {
        T t;
        int index = 0;
        while((t = csv.readRowAs()) != null) {
            Assertions.assertEquals(this.read[index++], t);
        }
    }

}
