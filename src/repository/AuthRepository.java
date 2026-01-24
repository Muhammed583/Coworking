package repository;

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
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            return false;
        }
    }

    public boolean register(String login, String password) {
        String sql = "INSERT INTO auth_users (login, password_hash) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            ps.setString(2, PasswordUtil.sha256(password));
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Registration error: " + e.getMessage());
            return false;
        }
    }

    public boolean authenticate(String login, String password) {
        String sql = "SELECT password_hash FROM auth_users WHERE login = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String storedHash = rs.getString("password_hash");
                return storedHash.equals(PasswordUtil.sha256(password));
            }
        } catch (SQLException e) {
            System.out.println("Auth error: " + e.getMessage());
            return false;
        }
    }
}
