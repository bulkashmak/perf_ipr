package ru.bulkashmak;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostgresQueries {

    private static final Logger LOGGER = LogManager.getLogger(PostgresQueries.class);

    @NotNull
    private final Connection connection;

    public PostgresQueries() {
        this.connection = PostgresUtil.connectToDb();
    }

    public void select(String selectTable, String fromTable) {

        String query = String.format("SELECT %s FROM %s", selectTable, fromTable);

        try (PreparedStatement statement = connection.prepareStatement(query)) {

//            statement.setInt(1, 1);
            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                Integer role = resultSet.getInt("role");

                System.out.println(id + " | " + login + " | " + password + " | " + role);
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка при отправке SELECT запроса\n" + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Ошибка при отключении от БД\n" + e.getMessage());
            }
        }
    }

    public void insert(String table, List<String> columns, List<String> values) {

        String query = String.format("INSERT INTO %s (%s) VALUES(%s)",
                table,
                String.join(", ", columns),
                String.join(", ", values));

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            final ResultSet resultSet = statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Ошибка при отправке INSERT запроса\n" + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Ошибка при отключении от БД\n" + e.getMessage());
            }
        }
    }
}