package net.njsharpe.docu;

import net.njsharpe.docu.annotation.Column;

public class Person {

    @Column(0)
    public int id;

    @Column(1)
    public String lastName;

    @Column(2)
    public String firstName;

    @Column(3)
    public char middleInitial;

    @Column(4)
    public int age;

    @Column(5)
    public int householdId;

    private Person() {
        // Required for anonymous construction
    }

    public Person(int id, String lastName, String firstName, int age) {
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
