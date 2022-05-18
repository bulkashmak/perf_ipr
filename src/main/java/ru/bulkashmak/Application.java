package ru.bulkashmak;

import ru.bulkashmak.jdbc.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) throws SQLException {
        final String dbUser = "postgres";
        final String dbPassword = "1234";
        final String dbUrl = "jdbc:postgresql://localhost:5432/ipr_db";

        Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        PostgresQueries queries = new PostgresQueries();

        queries.select("*", "users");
        queries.insert("users",
                Arrays.asList("login", "password", "role"),
                Arrays.asList("'alex'", "'1234'", "2"));
        queries.select("*", "users");

        // DAO
        UserDAO daoQueries = new UserDAO();

        System.out.println(daoQueries.read("bulkashmak"));

        connection.close();
    }
}
