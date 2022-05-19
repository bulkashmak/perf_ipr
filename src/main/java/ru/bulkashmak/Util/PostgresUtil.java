package ru.bulkashmak.Util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresUtil {

    static Logger LOGGER = LogManager.getLogger(PostgresUtil.class);

    public static Connection connectToDb() {

        Properties credentials = new Properties();

        try {
            Class.forName("org.postgresql.Driver");

            credentials.load(PostgresUtil.class.getResourceAsStream("db_credentials.properties"));

            return DriverManager.getConnection(
                    credentials.getProperty("user"),
                    credentials.getProperty("password"),
                    credentials.getProperty("url"));
        } catch (IOException | SQLException | ClassNotFoundException e) {
            LOGGER.error("Не удалось подключиться к базе данных. Убедитесь в том, что сервер " +
                    "базы данных активен и проверьте корректность переданных в метод параметров", e);
        }
        return null;
    }
}
