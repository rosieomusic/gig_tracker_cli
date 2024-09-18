package org.example.connection;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataConnection {


   private final String dbUsername = "postgres";
    private final String dbPassword = "postgres1";

    // Example:
    // jdbc:postgresql://localhost:5432/PuppyDB
    private final String hostNameUrl = "localhost";
    private final int portNumber = 5432;
    private final String databaseName = "BandBillVenue";
    private final String connectionString = "jdbc:postgresql://" + hostNameUrl +
            ":" + portNumber + "/" + databaseName;

    private static DataSource dataSource;


    private DataConnection(){

        if(DataConnection.dataSource == null) {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(connectionString);
            basicDataSource.setUsername(dbUsername);
            basicDataSource.setPassword(dbPassword);

            DataConnection.dataSource = basicDataSource;
        }
    }

    public static DataSource get(){

        if(DataConnection.dataSource == null){
            new DataConnection();
        }
        return DataConnection.dataSource;
    }
}
