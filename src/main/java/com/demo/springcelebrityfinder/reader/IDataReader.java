package com.demo.springcelebrityfinder.reader;

/**
 * Describe the behaviour of a DataReader. A DataReader can read data from different sources (Files, Databases, URL, etc).
 */
public interface IDataReader {

    /**
     * Read a file in the classpath and store all its information in a StringBuilder.
     * @return Information of the Team that contains the File
     */
    StringBuilder readTeamFromFile(String filename);

}
