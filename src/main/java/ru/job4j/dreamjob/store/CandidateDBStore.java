package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CandidateDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(CandidateDBStore.class.getName());
    private static final String FIND_ALL_SELECT = "SELECT * FROM candidate ORDER BY id";
    private static final String FIND_BY_ID_SELECT = "SELECT * FROM candidate WHERE id = ?";
    private static final String INSERT_INTO = "INSERT INTO candidate(name, description, created, "
            + "photo) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE candidate SET name = ?, description = ?, "
            + "photo = ? WHERE id = ?";

    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL_SELECT)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidateFromResultSet(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Исключение в методе findAllPosts() класс CandidateDBStore", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(INSERT_INTO,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Исключение в методе add() класс CandidateDBStore", e);
        }
        return candidate;
    }

    public void update(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(UPDATE)
        ) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setBytes(3, candidate.getPhoto());
            ps.setInt(4, candidate.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Исключение в методе update() класс CandidateDBStore", e);
        }
    }

    public Optional<Candidate> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID_SELECT)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(getCandidateFromResultSet(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Исключение в методе findById() класс CandidateDBStore", e);
        }
        return Optional.empty();
    }

    private static Candidate getCandidateFromResultSet(ResultSet it) throws SQLException {
        return new Candidate(it.getInt("id"), it.getString("name"),
                it.getString("description"), it.getBytes("photo"));
    }
}
