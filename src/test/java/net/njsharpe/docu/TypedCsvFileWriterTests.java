package net.njsharpe.docu;

import net.njsharpe.docu.csv.TypedCsvFileWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TypedCsvFileWriterTests extends CsvWriterData {

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
    public void toMemoryTest() {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream();
            TypedCsvFileWriter<Person> csv = new TypedCsvFileWriter<>(Person.class, stream)) {
            for(Person person : this.people) {
                csv.writeRowAs(person);
            }
            Assertions.assertEquals(this.expected, stream.toString(StandardCharsets.UTF_8));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    public void toFileTest() throws IOException {
        File file = Files.createTempFile("typed", ".csv").toFile();
        try(FileOutputStream stream = new FileOutputStream(file);
            TypedCsvFileWriter<Person> csv = new TypedCsvFileWriter<>(Person.class, stream)) {
            for(Person person : this.people) {
                csv.writeRowAs(person);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            Assertions.assertTrue(file.exists());
            Assertions.assertEquals(this.expected.length(), file.length());
        }
    }

}
