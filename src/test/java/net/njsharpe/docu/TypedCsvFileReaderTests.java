package net.njsharpe.docu;

import net.njsharpe.docu.csv.TypedCsvFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TypedCsvFileReaderTests extends CsvReaderData {

    private final Person[] people = new Person[] {
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
        try(ByteArrayInputStream stream = new ByteArrayInputStream(this.data.getBytes(StandardCharsets.UTF_8));
            TypedCsvFileReader<Person> csv = new TypedCsvFileReader<>(Person.class, stream, false)) {
            this.assertContents(csv);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void fromFileTest() {
        try(InputStream stream = this.getClass().getClassLoader().getResourceAsStream("data.csv");
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
            Assertions.assertEquals(this.people[index++], t);
        }
    }

}
