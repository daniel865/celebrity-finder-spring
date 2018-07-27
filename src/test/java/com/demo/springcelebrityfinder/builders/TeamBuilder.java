package com.demo.springcelebrityfinder.builders;

import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;

import java.util.Arrays;
import java.util.List;

public class TeamBuilder {

    public static Team createTeamWithCelebrity() {
        Person celebrity = createCelebrity("A");

        List<Person> members = Arrays.asList(
                celebrity,
                createPerson("B", celebrity),
                createPerson("C", celebrity),
                createPerson("D", celebrity)
        );

        return new Team(members);
    }

    public static Team createTeamWithoutCelebrity() {
        List<Person> members = Arrays.asList(
                createPerson("A", null),
                createPerson("B", null),
                createPerson("C", null),
                createPerson("D", null)
        );

        return new Team(members);
    }

    public static Team createTeamWithMoreThanOneCelebrity() {
        Person celebrity1 = createCelebrity("A");
        Person celebrity2 = createCelebrity("B");

        List<Person> members = Arrays.asList(
                createPerson("C", celebrity1),
                createPerson("D", celebrity2)
        );

        return new Team(members);
    }

    private static Person createCelebrity(String id) {
        return new Person(id, null);
    }

    private static Person createPerson(String id, Person acquainted) {
        return new Person(id, acquainted);
    }

}

