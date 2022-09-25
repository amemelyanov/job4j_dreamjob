package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(PostDBStore.class.getName());
    private static final String INSERT_INTO = "INSERT INTO users(email, password) VALUES (?, ?)";
    private static final String BY_EMAIL_AND_PWD_SELECT = "SELECT * FROM users"
            + " WHERE email = ? AND password = ?";

    private final BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public Optional<User> add(User user) {
        Optional<User> resultUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_INTO,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    user.setId(id.getInt(1));
                    resultUser = Optional.of(user);
                }
            }
        } catch (Exception e) {
            LOG.error("Исключение в методе add() класс UserDBStore", e);
        }
        return resultUser;
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> resultUser = Optional.empty();
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps = cn.prepareStatement(BY_EMAIL_AND_PWD_SELECT)
            ) {
                ps.setString(1, email);
                ps.setString(2, password);
                try (ResultSet it = ps.executeQuery()) {
                    if (it.next()) {
                        resultUser = Optional.of(getUserFromResultSet(it));
                    }
                }
            } catch (Exception e) {
                LOG.error("Исключение в методе findUserByEmailAndPwd() класс UserDBStore", e);
            }
            return resultUser;
    }

    private static User getUserFromResultSet(ResultSet it) throws SQLException {
        return new User(it.getInt("id"), it.getString("email"),
                it.getString("password"));
    }
}
