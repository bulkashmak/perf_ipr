package ru.bulkashmak.jdbc;

import com.sun.istack.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.bulkashmak.PostgresUtil;
import ru.bulkashmak.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO<User, String> {

    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

    @NotNull
    private final Connection connection;

    public UserDAO() {
        this.connection = PostgresUtil.connectToDb();
    }

    /**
     * Создание пользователя в БД
     *
     * @return False если пользователь уже существует
     */
    @Override
    public boolean create(@NotNull final User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.INSERT.QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setInt(3,user.getRole().getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            LOGGER.error("Ошибка при создании пользователя\n" + e.getMessage());
        }
        return result;
    }

    /**
     * Найти пользователя по логину
     */
    @Override
    public User read(@NotNull final String login) {
        final User result = new User();
        result.setLogin("Пользователь не найден");

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.GET.QUERY)) {
            statement.setString(1, login);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(rs.getInt("id"));
                result.setLogin(login);
                result.setPassword(rs.getString("password"));
                result.setRole(new User.Role(rs.getInt("rol_id"), rs.getString("role")));
            }
        } catch (SQLException e) {
            LOGGER.error("Ошибка при получении пользователя\n" + e.getMessage());
        }
        return result;
    }

    /**
     * Изменить пароль пользователя по Id
     *
     * @return True при успехе. False при неудаче
     */
    @Override
    public boolean update(@NotNull final User user) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.UPDATE.QUERY)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            LOGGER.error("Ошибка при обновлении пользователя\n" + e.getMessage());
        }
        return result;
    }

    /**
     * Удаление пользователя по Id & Login & Password
     *
     * @return True если пользователь был удален. False если пользователя не существует
     */
    @Override
    public boolean delete(@NotNull Integer userId) {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(SQLUser.DELETE.QUERY)) {
            statement.setInt(1, userId);
            result = statement.executeQuery().next();
        } catch (SQLException e) {
            LOGGER.error("Ошибка при удалении пользователя\n" + e.getMessage());
        }
        return result;
    }

    /**
     * SQL запросы
     */
    enum SQLUser {
        GET("SELECT u.id, u.login, u.password, r.id AS rol_id, r.role FROM users AS u LEFT JOIN roles AS r ON u.role = r.id WHERE u.login = ?"),
        INSERT("INSERT INTO users (login, password, role) VALUES (?, ?, ?) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = ? RETURNING id"),
        UPDATE("UPDATE users SET password = ? WHERE id = ? RETURNING id");

        String QUERY;

        SQLUser(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}