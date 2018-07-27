package com.demo.springcelebrityfinder.searcher;

import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;

/**
 * Interface that represent the searcher for a celebrity
 */
public interface ICelebritySearcher {

    /**
     * Search a celebrity in a team. If the celebrity is found returns a Person, otherwise,
     * return a null value
     * @param team Team that contains all its members
     * @return Value that indicates if a celebrity was found
     */
    Person findCelebrity(Team team);

}
