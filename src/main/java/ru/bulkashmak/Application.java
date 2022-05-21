package ru.bulkashmak;

import ru.bulkashmak.jdbc.UserDAO;

import java.util.Arrays;
import java.util.Map;

import static ru.bulkashmak.Util.Util.generateRandomString;

public class Application {

    public static void main(String[] args) {

        PostgresQueries queries = new PostgresQueries();

        System.out.println(queries.select("*"));
        queries.insert(Map.of(
                "login", String.format("%s", generateRandomString(10)),
                "password", "1234",
                "role", "2"));
        System.out.println(queries.select("*"));

        queries.closeConnection();

        // DAO
        UserDAO daoQueries = new UserDAO();

        System.out.println(daoQueries.read("bulkashmak"));

        daoQueries.closeConnection();
    }
}
