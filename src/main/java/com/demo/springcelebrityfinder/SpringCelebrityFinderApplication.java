package com.demo.springcelebrityfinder;

import com.demo.springcelebrityfinder.creator.ITeamCreator;
import com.demo.springcelebrityfinder.creator.impl.TeamCreator;
import com.demo.springcelebrityfinder.exceptions.FileFormatException;
import com.demo.springcelebrityfinder.model.Person;
import com.demo.springcelebrityfinder.model.Team;
import com.demo.springcelebrityfinder.reader.IDataReader;
import com.demo.springcelebrityfinder.reader.impl.DataReader;
import com.demo.springcelebrityfinder.searcher.ICelebritySearcher;
import com.demo.springcelebrityfinder.searcher.impl.CelebritySearcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.util.Objects.nonNull;


@SpringBootApplication
public class SpringCelebrityFinderApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringCelebrityFinderApplication.class);

	private static final String FILENAME = "input.txt";

	@Autowired
	private ITeamCreator teamCreator;

	@Autowired
	private ICelebritySearcher celebritySearcher;

	public static void main(String[] args) {
		SpringApplication.run(SpringCelebrityFinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			Team team = teamCreator.createTeamFromFile(FILENAME);

			if (nonNull(team)) {
				Person celebrity = celebritySearcher.findCelebrity(team);

				if (nonNull(celebrity)) {
					LOGGER.info("-------------------------------------------------------------");
					LOGGER.info("The Celebrity in the team is: " + celebrity.getId());
					LOGGER.info("-------------------------------------------------------------");
				} else {
					LOGGER.info("-------------------------------------------------------------");
					LOGGER.info("Error: There is no celebrity in the Team");
					LOGGER.info("-------------------------------------------------------------");
				}
			}
		} catch (FileFormatException e) {
			LOGGER.info("-------------------------------------------------------------");
			LOGGER.info("Error: The team has a wrong configuration");
			LOGGER.info("-------------------------------------------------------------");
		} catch(IllegalArgumentException e) {
			LOGGER.info("-------------------------------------------------------------");
			LOGGER.info("Error: The file has a wrong configuration");
			LOGGER.info("-------------------------------------------------------------");
		}

	}
}
