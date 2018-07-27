package com.demo.springcelebrityfinder.creator.impl;

import com.demo.springcelebrityfinder.creator.ITeamCreator;
import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.reader.IDataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

@Component
public class TeamCreator implements ITeamCreator {

    @Autowired
    private IDataReader dataReader;

    @Override
    public Team createTeamFromFile(String filename) {
        StringBuilder data = dataReader.readTeamFromFile(filename);

        if (nonNull(data)) {
            // Obtain the information of the File in array of String representing the lines of the File
            String[] lines = data.toString().split("\\n");

            // Get the first line that represent the size  of the Team
            Integer sizeOfTeam = Integer.parseInt(lines[0]);

            // Create lasting members of the Team
            List<Person> members = createMembers(lines, sizeOfTeam);

            return new Team(members);
        }

        return null;
    }

    /**
     * Create the members of the Team based on the lines of the file
     * @param lines Lines of the file
     * @param size Number of members of the team
     * @return Members of the Team
     */
    private List<Person> createMembers(String[] lines, Integer size) {
        String[] strings = Arrays.copyOfRange(lines, 1, size + 1);

        Stream<String> stringStream = Stream.of(strings);
        return stringStream.map(this::createTeamMember).collect(Collectors.toList());
    }

    /**
     * Create a Team member based on a string that represents a line from the file
     * @param line
     * @return
     */
    private Person createTeamMember(String line) {
        String[] data = line.split(",");
        if (data.length == 2) {
            return new Person(data[0], new Person(data[1]));
        }
        return new Person(data[0], null);
    }


}