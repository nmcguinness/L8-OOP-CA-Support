package daoexample.dao;

import daoexample.db.DatabaseConnection;
import daoexample.domain.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/// <summary>
/// JDBC implementation of TaskDao.
/// </summary>
public class JdbcTaskDao implements TaskDao {

    public JdbcTaskDao() {
    }

    @Override
    public int insert(String title, String status) throws Exception {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("title is required");

        if (status == null || status.isBlank())
            throw new IllegalArgumentException("status is required");

        String sql = "INSERT INTO tasks(title, status) VALUES (?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, title.trim());
            ps.setString(2, status.trim().toUpperCase());

            int rows = ps.executeUpdate();

            if (rows != 1)
                throw new IllegalStateException("Insert failed. rows=" + rows);

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next())
                    throw new IllegalStateException("No generated key returned");

                return keys.getInt(1);
            }
        }
    }

    @Override
    public Optional<Task> findById(int id) throws Exception {
        if (id <= 0)
            return Optional.empty();

        String sql = "SELECT id, title, status FROM tasks WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return Optional.empty();

                return Optional.of(mapRow(rs));
            }
        }
    }

    @Override
    public List<Task> findAll() throws Exception {
        String sql = "SELECT id, title, status FROM tasks ORDER BY id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ArrayList<Task> tasks = new ArrayList<>();

            while (rs.next())
                tasks.add(mapRow(rs));

            return tasks;
        }
    }

    @Override
    public boolean updateStatus(int id, String newStatus) throws Exception {
        if (id <= 0)
            return false;

        if (newStatus == null || newStatus.isBlank())
            throw new IllegalArgumentException("newStatus is required");

        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, newStatus.trim().toUpperCase());
            ps.setInt(2, id);

            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean deleteById(int id) throws Exception {
        if (id <= 0)
            return false;

        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        }
    }

    private static Task mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String status = rs.getString("status");

        return new Task(id, title, status);
    }

}