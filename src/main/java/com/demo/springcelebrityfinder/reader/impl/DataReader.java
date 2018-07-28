package com.demo.springcelebrityfinder.reader.impl;

import com.demo.springcelebrityfinder.exceptions.FileFormatException;
import com.demo.springcelebrityfinder.reader.IDataReader;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class DataReader implements IDataReader {

    private final static Logger LOGGER = Logger.getLogger(DataReader.class);

    @Override
    public StringBuilder readTeamFromFile(String filename) {
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());
            StringBuilder data = new StringBuilder();
            Stream<String> lines = Files.lines(path);
            lines.forEach(line -> data.append(line).append("\n"));
            lines.close();

            if (data.toString().isEmpty()) {
                throw new FileFormatException("File can't be empty");
            }

            return data;
        } catch (URISyntaxException e) {
            LOGGER.error("Error: URI to resource is incorrect");
        } catch (IOException e) {
            LOGGER.error("Error reading data from file");
        }  catch (FileFormatException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

    /**
     * Transform an InputStream to String
     * @param inputStream InputStream to transform
     * @return String with the transformation of the InputStream
     * @throws IOException
     */
    private String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
