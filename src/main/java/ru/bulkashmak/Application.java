package ru.bulkashmak;

import ru.bulkashmak.jdbc.UserDAO;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        PostgresQueries queries = new PostgresQueries();

        queries.select("*", "users");
        queries.insert("users",
                Arrays.asList("login", "password", "role"),
                Arrays.asList("'alex'", "'1234'", "2"));
        queries.select("*", "users");

        // DAO
        UserDAO daoQueries = new UserDAO();

        System.out.println(daoQueries.read("bulkashmak"));
    }
}
