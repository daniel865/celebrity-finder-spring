package com.demo.springcelebrityfinder.searcher.impl;

import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.searcher.ICelebritySearcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * This class is an implementation of the interface ICelebritySearcher that uses an Stack to store the members that
 * may be the celebrity.
 */
@Component
public class CelebritySearcher implements ICelebritySearcher {

    /**
     * Creates a stack of the people that is part of a team.
     * @param team Team with all its members
     * @return Stack with all of the persons that are part of the team
     */
    private Stack<Person> createStackOfPeople(Team team) {
        final Stack<Person> people = new Stack<>();
        team.getPeople().stream().forEach(person -> people.push(person));
        return people;
    }

    /**
     * Search a celebrity in a team. If the celebrity is found returns a Person, otherwise,
     * return a null value
     * @param team Team that contains all its members
     * @return Value that indicates if a celebrity was found
     */
    public Person findCelebrity(Team team) {
        Stack<Person> stackOfPeople = createStackOfPeople(team);

        while (stackOfPeople.size() > 1) {
            Person person1 = stackOfPeople.pop();
            Person person2 = stackOfPeople.pop();

            Person possibleCelebrity = knows(person1, person2);

            stackOfPeople.push(possibleCelebrity);
        }

        Person lastingPerson = stackOfPeople.pop();

        return isCelebrity(lastingPerson, team.getPeople()) ? lastingPerson : null;
    }

    /**
     * Check if a person knows another. Return the person may be the celebrity
     * @param personA 
     * @param personB
     * @return Person that may be the celebrity
     */
    public Person knows(Person personA, Person personB) {
        List<Person> people = personA.getPersonsAcquainted();

        Optional<Person> optionalPerson = people
                .stream()
                .filter(person -> this.compareNames(person.getId(), personB.getId()))
                .findAny();

        if (optionalPerson.isPresent()) {
            return personB;
        }
        return personA;
    }

    /**
     * Check if a person is a celebrity. If a person doesn't have any relations with anybody return true, false otherwise
     * @param possibleCelebrity Person to check if is a celebrity
     * @return Boolean value that indicates that a person is a celebrity
     */
    private Boolean isCelebrity(Person possibleCelebrity, List<Person> members) {
        for (Person member : members) {
            if (member.getPersonsAcquainted() != null && !contains(member, possibleCelebrity.getId())) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }

    /**
     * Compare if the names of the persons are equal
     * @param personA First person
     * @param personB Second person
     * @return Boolean value that indicates if the names are equal
     */
    private Boolean compareNames(String personA, String personB) {
        return personA.trim().equals(personB.trim());
    }

    /**
     * Check if a person is a acquainted with other
     * @param person Person with its relations
     * @param personName Name of the person to search
     * @return Boolean values that indicates if the persons are acquainted
     */
    private Boolean contains(Person person, String personName) {
        List<Person> personsAcquainted = person.getPersonsAcquainted();
        Optional<Person> optionalPerson = personsAcquainted
                .stream()
                .filter(person1 -> compareNames(person1.getId(), personName))
                .findAny();

        if (optionalPerson.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
