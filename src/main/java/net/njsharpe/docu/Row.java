package net.njsharpe.docu;

import java.util.Iterator;

public class Row implements Iterator<String> {

    private final String[] cells;
    private int cursor;

    private Row(String[] cells) {
        this.cells = cells;
    }

    public int cell() {
        return this.cursor - 1;
    }

    public boolean isEmpty() {
        return this.cells.length == 0;
    }

    @Override
    public boolean hasNext() {
        return this.cursor + 1 <= this.cells.length;
    }

    @Override
    public String next() {
        return this.cells[this.cursor++];
    }

    public static Row wrap(String... cells) {
        return new Row(cells);
    }

}
