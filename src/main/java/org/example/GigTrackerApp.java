package org.example;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.dao.BandDao;
import org.example.dao.JdbcBandDao;
import org.example.dao.JdbcBillDao;
import org.example.dao.JdbcVenueDao;
import org.example.util.SystemInOutConsole;

import java.util.Scanner;


public class GigTrackerApp {
    public static BasicDataSource dataSource;
    public static JdbcBandDao jdbcBandDao;
    public static JdbcBillDao jdbcBillDao;
    public static JdbcVenueDao jdbcVenueDao;

    public static void main(String[] args) {
        SystemInOutConsole systemInOutConsole = new SystemInOutConsole();

        GigTrackerController controller =
                new GigTrackerController(systemInOutConsole);
        controller.run();
    }


}

