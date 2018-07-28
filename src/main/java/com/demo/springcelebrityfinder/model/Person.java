package com.demo.springcelebrityfinder.model;

import java.util.List;
import java.util.Objects;

public class Person {

    private String id;
    private List<Person> personsAcquainted;

    private Person() {
    }

    public Person(String id) {
        this.id = id;
    }

    public Person(String id, List<Person> personsAcquainted) {
        this.id = id;
        this.personsAcquainted = personsAcquainted;
    }

    public String getId() {
        return id;
    }

    public List<Person> getPersonsAcquainted() {
        return personsAcquainted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", personsAcquainted=" + personsAcquainted +
                '}';
    }
}
