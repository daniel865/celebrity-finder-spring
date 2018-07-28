package com.demo.springcelebrityfinder.creator;

import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.reader.IDataReader;

/**
 * Interface that represents the behavior of the ITeamCreator. The ITeamCreator is in charge of creating a team
 * from different kind of sources.
 */
public interface ITeamCreator {

    /**
     * Create a Team using as source a File.
     * @return Team created with all its data
     */
    Team createTeamFromFile(String filename);


}
