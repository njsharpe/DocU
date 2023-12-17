package net.njsharpe.docu.annotation;

import org.jetbrains.annotations.Range;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Labels a field in a Bean as a column to be serialized in a CSV file.
 * Indexing for this annotation begins at 0, but can be placed in any order in
 * the Bean itself.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * @return the column index
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    int value();

}
