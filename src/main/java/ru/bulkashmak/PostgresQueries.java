package ru.bulkashmak;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostgresQueries {

    private static final Logger LOGGER = LogManager.getLogger(PostgresQueries.class);

    @NotNull
    private final Connection connection;

    public PostgresQueries() {
        this.connection = PostgresUtil.connectToDb();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Ошибка при отключении от БД", e);
        }
    }

    public List<Map<String, String>> select(String toSelect) {

        List<Map<String, String>> result = new ArrayList<>();

        String query = String.format("SELECT %s FROM users", toSelect);

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            final ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("id", resultSet.getString("id"));
                user.put("login", resultSet.getString("login"));
                user.put("password", resultSet.getString("password"));
                user.put("role", resultSet.getString("role"));
                result.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка при отправке SELECT запроса", e);
        }

        if (result.isEmpty()) {
            result.add(Map.of("empty", "Запрос не вернул значений"));
        }
        return result;
    }

    public Boolean insert(List<String> columns, List<String> values) {

        try {
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO users (%s) VALUES(%s)",
                    String.join(", ", columns),
                    String.join(", ", values));
            statement.executeUpdate(query);
        } catch (SQLException e) {
            LOGGER.error("Ошибка при отправке INSERT запроса", e);
            return false;
        }

        return true;
    }
}