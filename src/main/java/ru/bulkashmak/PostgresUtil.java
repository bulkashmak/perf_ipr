package ru.bulkashmak;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresUtil {

    static Logger LOGGER = LogManager.getLogger(PostgresUtil.class);

    private final static String dbUser = "postgres";
    private final static String dbPassword = "1234";
    private final static String dbUrl = "jdbc:postgresql://localhost:5432/ipr_db";

    public static Connection connection;

    public static Connection connectToDb() {

        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            LOGGER.error("Не удалось подключиться к базе данных. Убедитесь в том, что сервер " +
                    "базы данных активен и проверьте корректность переданных в метод параметров\n" +
                    e.getMessage());
        }
        return connection;
    }
}
