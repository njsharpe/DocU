package net.njsharpe.docu;

import net.njsharpe.docu.annotation.Column;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Person {

    @Column(0)
    public int id;

    @Column(1)
    public String lastName;

    @Column(2)
    public String firstName;

    @Column(3)
    @Nullable
    public Character middleInitial;

    @Column(4)
    public int age;

    @Column(5)
    @Nullable
    public Integer householdId;

    private Person() {
        // Required for use internally
    }

    public Person(int id, @NotNull String lastName, @NotNull String firstName, int age) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Person p)) return false;
        return p.id == this.id &&
                p.firstName.equals(this.firstName) &&
                p.lastName.equals(this.lastName) &&
                p.age == this.age;
    }

    @Override
    public int hashCode() {
        return this.id * this.firstName.hashCode() * this.lastName.hashCode() * this.age;
    }
}
