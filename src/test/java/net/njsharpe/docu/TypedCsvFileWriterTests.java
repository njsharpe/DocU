package net.njsharpe.docu;

import net.njsharpe.docu.csv.TypedCsvFileWriter;
import net.njsharpe.docu.dto.Person;
import net.njsharpe.docu.util.InputOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TypedCsvFileWriterTests {

    private final Person[] write = new Person[] {
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

    private final Person[] append = new Person[] {
            new Person(5, "Davis", "Nathaniel", 19),
            new Person(6, "Harding", "Fredrick", 73) {{
                middleInitial = 'M';
                householdId = 2;
            }}
    };

    @Test
    public void toMemoryTest() {
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream();
            TypedCsvFileWriter<Person> csv = new TypedCsvFileWriter<>(Person.class, stream)) {
            for(Person person : this.write) {
                csv.writeRowAs(person);
            }
            Assertions.assertArrayEquals(InputOutput.EXAMPLE_FILE_NO_APPEND_BYTES, stream.toByteArray());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "CI", matches = "(?i)true")
    public void toFileTest() throws IOException {
        Path path = InputOutput.createNewExampleFileAndPopulateNoAppend();
        try(OutputStream stream = Files.newOutputStream(path);
            TypedCsvFileWriter<Person> csv = new TypedCsvFileWriter<>(Person.class, stream)) {
            for(Person person : this.write) {
                csv.writeRowAs(person);
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
        Path path = InputOutput.createNewExampleFileAndPopulateNoAppend();
        try(OutputStream stream = Files.newOutputStream(path, StandardOpenOption.APPEND);
            TypedCsvFileWriter<Person> csv = new TypedCsvFileWriter<>(Person.class, stream)) {
            for(Person person : this.append) {
                csv.writeRowAs(person);
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            Assertions.assertTrue(Files.exists(path));
            Assertions.assertEquals(InputOutput.EXAMPLE_FILE_WITH_APPEND_BYTES.length, Files.size(path));
        }
    }

}
