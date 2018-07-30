package com.demo.springcelebrityfinder;

import com.demo.springcelebrityfinder.builders.TeamBuilder;
import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.searcher.impl.CelebritySearcher;
import org.junit.Test;

import java.util.Arrays;

import static com.demo.springcelebrityfinder.builders.TeamBuilder.createPersonWithRelations;
import static org.assertj.core.api.Assertions.assertThat;

public class CelebritySearcherTest {

    @Test
    public void whenValidTeamIsGivenExpectedReturnIdOfCelebrity() {
        Team teamWithCelebrity = TeamBuilder.createTeamWithCelebrity();

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person celebrity = celebritySearcher.findCelebrity(teamWithCelebrity);

        assertThat(celebrity.getId()).isEqualToIgnoringCase("Bjor");
    }

    @Test
    public void whenTeamWithoutCelebrityIsGivenExpectedReturnNull() {
        Team teamWithoutCelebrity = TeamBuilder.createTeamWithoutCelebrity();

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person celebrity = celebritySearcher.findCelebrity(teamWithoutCelebrity);

        assertThat(celebrity).isNull();
    }

    @Test
    public void knowsTest() {
        Person anna = new Person("Anna");
        Person bjor = new Person("Bjor");
        Person catlin = new Person("Catlin");
        Person david = new Person("David");

        Person annaWithRelations = createPersonWithRelations(anna, Arrays.asList(bjor, catlin, david));
        Person catlinWithRelations = createPersonWithRelations(catlin, Arrays.asList(anna, bjor, david));

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person person = celebritySearcher.knows(catlinWithRelations, annaWithRelations);

        assertThat(person.getId()).isEqualToIgnoringCase("Anna");
    }



}

