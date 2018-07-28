package com.demo.springcelebrityfinder.builders;

import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;

import java.util.Arrays;
import java.util.List;

public class TeamBuilder {

    public static Team createTeamWithCelebrity() {
        Person anna = new Person("Anna");
        Person bjor = new Person("Bjor");
        Person catlin = new Person("Catlin");
        Person david = new Person("David");

        Person annaWithRelations = createPersonWithRelations(anna, Arrays.asList(bjor, catlin, david));
        Person catlinWithRelations = createPersonWithRelations(catlin, Arrays.asList(anna, bjor));
        Person davidWithRelations = createPersonWithRelations(david, Arrays.asList(anna, bjor, catlin));

        return new Team(Arrays.asList(annaWithRelations, catlinWithRelations, davidWithRelations, bjor));
    }

    public static Team createTeamWithoutCelebrity() {
        Person anna = new Person("Anna");
        Person bjor = new Person("Bjor");
        Person catlin = new Person("Catlin");
        Person david = new Person("David");

        Person annaWithRelations = createPersonWithRelations(anna, Arrays.asList(bjor, catlin, david));
        Person bjorWithRelations = createPersonWithRelations(bjor, Arrays.asList(anna, catlin, david));
        Person catlinWithRelations = createPersonWithRelations(catlin, Arrays.asList(anna, bjor, david));
        Person davidWithRelations = createPersonWithRelations(david, Arrays.asList(anna, bjor, catlin));

        return new Team(Arrays.asList(annaWithRelations, bjorWithRelations, catlinWithRelations, davidWithRelations));
    }


    public static Person createPersonWithRelations(Person person, List<Person> relations) {
        return new Person(person.getId(), relations);
    }


}

