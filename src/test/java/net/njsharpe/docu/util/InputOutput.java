package net.njsharpe.docu.util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class InputOutput {

    public static final byte[] EXAMPLE_FILE_NO_APPEND_BYTES =
            ("1,Smith,John,,26," + System.lineSeparator() +
            "2,Jobs,Steve,,55," + System.lineSeparator() +
            "3,Robertson,David,G,43,1" + System.lineSeparator() +
            "4,Robertson,Joshua,,19,1" + System.lineSeparator())
                    .getBytes(StandardCharsets.UTF_8);

    public static final byte[] EXAMPLE_FILE_WITH_APPEND_BYTES =
            ("1,Smith,John,,26," + System.lineSeparator() +
            "2,Jobs,Steve,,55," + System.lineSeparator() +
            "3,Robertson,David,G,43,1" + System.lineSeparator() +
            "4,Robertson,Joshua,,19,1" + System.lineSeparator() +
            "5,Davis,Nathaniel,,19," + System.lineSeparator() +
            "6,Harding,Fredrick,M,73,2" + System.lineSeparator())
                    .getBytes(StandardCharsets.UTF_8);

    public static Path createNewExampleFile() throws IOException {
        return Files.createTempFile("", ".csv");
    }

    public static Path createNewExampleFileAndPopulateNoAppend() throws IOException {
        Path path = InputOutput.createNewExampleFile();
        try(OutputStream stream = Files.newOutputStream(path)) {
            stream.write(EXAMPLE_FILE_NO_APPEND_BYTES);
        }
        if(EXAMPLE_FILE_NO_APPEND_BYTES.length != Files.size(path)) {
            throw new IllegalStateException();
        }
        return path;
    }

    public static Path createNewExampleFileAndPopulateWithAppend() throws IOException {
        Path path = InputOutput.createNewExampleFile();
        try(OutputStream stream = Files.newOutputStream(path)) {
            stream.write(EXAMPLE_FILE_NO_APPEND_BYTES);
        }
        if(EXAMPLE_FILE_NO_APPEND_BYTES.length != Files.size(path)) {
            throw new IllegalStateException();
        }
        return path;
    }

}
