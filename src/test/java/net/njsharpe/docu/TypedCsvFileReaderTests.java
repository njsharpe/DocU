package net.njsharpe.docu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TypedCsvFileReaderTests extends CsvFileData {

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
    public void check() {
        try(InputStream stream = new ByteArrayInputStream(this.data.getBytes(StandardCharsets.UTF_8));
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(stream));
            TypedCsvFileReader<Person> csv = new TypedCsvFileReader<>(Person.class, reader, false)) {
            Person person;
            int index = 0;
            while((person = csv.readAs()) != null) {
                Assertions.assertEquals(this.people[index++], person);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
