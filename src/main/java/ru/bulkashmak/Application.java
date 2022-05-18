package ru.bulkashmak;

import ru.bulkashmak.jdbc.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) throws SQLException {

        PostgresQueries queries = new PostgresQueries();

        queries.select("*", "users");
        queries.insert("users",
                Arrays.asList("login", "password", "role"),
                Arrays.asList("'alex'", "'1234'", "2"));
        queries.select("*", "users");

        queries.closeConnection();

        // DAO
        UserDAO daoQueries = new UserDAO();

        System.out.println(daoQueries.read("bulkashmak"));

        daoQueries.closeConnection();
    }
}
