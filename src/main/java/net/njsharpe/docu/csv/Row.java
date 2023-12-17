package net.njsharpe.docu.csv;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * A wrapper {@link Iterator} object for declaring the cells in a row in
 * relation to the structure of a XSV document. This structure is allowed and
 * expected to be used by any implementing code for the iteration of the cells
 * in a XSV document.
 */
public class Row implements Iterator<String> {

    private final String[] cells;
    private int cursor;

    private Row(String[] cells) {
        this.cells = cells;
    }

    /**
     * @return the index of the current cell
     */
    public int cell() {
        return this.cursor - 1;
    }

    /**
     * @return {@code true} if there are no cells in this {@link Iterator}
     */
    public boolean isEmpty() {
        return this.cells.length == 0;
    }

    @Override
    public boolean hasNext() {
        return this.cursor + 1 <= this.cells.length;
    }

    @Override
    @NotNull
    public String next() {
        return this.cells[this.cursor++];
    }

    /**
     * Static wrapper method to construct a {@link Row} from a {@link String}
     * array of any size.
     *
     * @param cells a {@link String} array of values
     * @return an instance of the {@link Row} object
     */
    public static Row wrap(@NotNull String... cells) {
        return new Row(cells);
    }

}
