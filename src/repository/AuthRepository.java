package repository;

import model.User;
import util.DatabaseConnection;
import util.PasswordUtil;
import java.sql.*;

public class AuthRepository {

    public boolean loginExists(String login) {
        String sql = "SELECT 1 FROM auth_users WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { return false; }
    }

    public boolean register(String login, String password) {
        String sql = "INSERT INTO auth_users (login, password_hash) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, PasswordUtil.sha256(password));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { return false; }
    }

    public User authenticate(String login, String password) {
        String sql = "SELECT id FROM auth_users WHERE login = ? AND password_hash = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, PasswordUtil.sha256(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), login);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}