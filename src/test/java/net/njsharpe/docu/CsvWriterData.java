package net.njsharpe.docu;

import net.njsharpe.docu.csv.Row;

public class CsvWriterData {

    protected final Row[] data = {
            Row.wrap("1", "Smith", "John", "", "26", ""),
            Row.wrap("2", "Jobs", "Steve", "", "55", ""),
            Row.wrap("3", "Robertson", "David", "G", "43", "1"),
            Row.wrap("4", "Robertson", "Joshua", "", "19", "1")
    };

    protected final String expected =
            "1,Smith,John,,26," + System.lineSeparator() +
            "2,Jobs,Steve,,55," + System.lineSeparator() +
            "3,Robertson,David,G,43,1" + System.lineSeparator() +
            "4,Robertson,Joshua,,19,1" + System.lineSeparator();

}
