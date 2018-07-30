package com.demo.springcelebrityfinder.creator.impl;

import com.demo.springcelebrityfinder.creator.ITeamCreator;
import com.demo.springcelebrityfinder.exceptions.FileFormatException;
import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.reader.IDataReader;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Component
public class TeamCreator implements ITeamCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamCreator.class);

    @Autowired
    private IDataReader dataReader;

    @Override
    public Team createTeamFromFile(String filename) {
        StringBuilder data = dataReader.readTeamFromFile(filename);

        if (nonNull(data)) {
            // Obtain the information of the File in array of String representing the lines of the File
            String[] lines = data.toString().split("\\n");

            Team team = createTeam(lines);

            return team;
        }

        return null;
    }

    /**
     * Create a Team using the lines of the file as input
     * @param lines Lines of the file
     * @return Team with  all theirs relations
     */
    private Team createTeam(String[] lines) {

        // Get the first line that represent the size  of the Team
        Integer sizeOfTeam = Integer.parseInt(lines[0]);

        // Create array of people
        String[] linesMembers = Arrays.copyOfRange(lines, 1, sizeOfTeam + 1);

        // Create array of people's relations
        String[] linesRelations = Arrays.copyOfRange(lines, sizeOfTeam + 1, lines.length);

        // validate content of lines
        validateMembersAndRelations(linesMembers, linesRelations);

        // Create lasting members of the Team
        List<Person> members = createMembers(linesMembers);

        // Create team without relations
        Team team = new Team(members);

        // Create team with relations
        Team teamWithRelations = createRelations(team, linesRelations);

        return teamWithRelations;
    }

    private void validateMembersAndRelations(String[] linesMembers, String[] linesRelations) throws FileFormatException {
        if (linesMembers.length - 1 != linesRelations.length) {
            throw new FileFormatException("Error: There are no enough relations for the team members");
        }
    }

    /**
     * Create the members of the Team based on the lines of the file
     * @param linesMembers Lines of the file
     * @return Members of the Team
     */
    private List<Person> createMembers(String[] linesMembers) {
        Stream<String> linesWithoutSpaces = Stream.of(linesMembers).map(s -> s.trim());
        return linesWithoutSpaces.map(Person::new).collect(Collectors.toList());
    }

    /**
     * Create the relations between the team members
     * @param team Team to create the relations
     * @param relationsPerPerson Array with the relations for each member of the team
     * @return Team with relations between members
     */
    private Team createRelations(Team team, String[] relationsPerPerson) {
        List<Person> members = team.getPeople();

        Stream<String> streamRelations = Stream.of(relationsPerPerson).map(s -> s.trim());

        List<Person> people = streamRelations.map(relations -> {
            Optional<Person> optionalPerson = findPersonInMembers(relations, members);
            if (optionalPerson.isPresent()) {
                return createRelations(relations);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        Collection<Person> disjunction = CollectionUtils.disjunction(members, people);

        List<Person> mergedPeople = Stream.concat(disjunction.stream(), people.stream()).collect(Collectors.toList());

        return new Team(mergedPeople);
    }

    /**
     * Find a person in the members list
     * @param relations Relations of the person
     * @param members Member of the Team
     * @return optional with the person of the team
     */
    private Optional<Person> findPersonInMembers(String relations, List<Person> members) {
        String[] relationsOfPerson = relations.split(",");

        String idPerson = relationsOfPerson[0];

        return members.stream().filter(person -> person.getId().equals(idPerson)).findAny();
    }

    /**
     * Create a person with his relations
     * @param relations Relations of the person
     * @return Person with his relations created
     */
    public Person createRelations(String relations) {

        String[] relationsPerson = relations.split(",");

        // ID Owner
        String idPerson = relationsPerson[0];

        // Create Array to exclude owner of relations
        String[] relationsWithoutOwner = Arrays.copyOfRange(relationsPerson, 1, relationsPerson.length);

        return new Person(idPerson, createRelationsOfPerson(relationsWithoutOwner));
    }

    /**
     * Create the relations of a person
     * @param relationsOfPerson String containing the relations of the person
     * @return List of the relations of the person
     */
    public List<Person> createRelationsOfPerson(String[] relationsOfPerson) {
        return Stream.of(relationsOfPerson).map(Person::new).collect(Collectors.toList());
    }



}