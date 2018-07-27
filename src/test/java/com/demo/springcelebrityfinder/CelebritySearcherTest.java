package com.demo.springcelebrityfinder;

import com.demo.springcelebrityfinder.builders.TeamBuilder;
import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.searcher.impl.CelebritySearcher;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CelebritySearcherTest {

    @Test
    public void whenValidTeamIsGivenExpectedReturnIdOfCelebrity() {
        Team teamWithCelebrity = TeamBuilder.createTeamWithCelebrity();

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person celebrity = celebritySearcher.findCelebrity(teamWithCelebrity);

        assertThat(celebrity.getId()).isEqualToIgnoringCase("A");
    }

    @Test
    public void whenTeamWithoutCelebrityIsGivenExpectedReturnNull() {
        Team teamWithoutCelebrity = TeamBuilder.createTeamWithoutCelebrity();

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person celebrity = celebritySearcher.findCelebrity(teamWithoutCelebrity);

        assertThat(celebrity).isNull();
    }

    @Test
    public void whenTeamHasMoreThanOneCelebrityIsGivenExpectedReturnNull() {
        Team teamWithMoreThanOneCelebrity = TeamBuilder.createTeamWithMoreThanOneCelebrity();

        CelebritySearcher celebritySearcher = new CelebritySearcher();

        Person celebrity = celebritySearcher.findCelebrity(teamWithMoreThanOneCelebrity);

        assertThat(celebrity).isNull();
    }


}

