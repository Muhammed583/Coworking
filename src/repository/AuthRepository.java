package repository;

import model.AuthUser;
import util.DatabaseConnection;
import util.PasswordUtil;
import java.sql.*;

public class AuthRepository {

    public boolean register(String login, String password) {
        String sql = "INSERT INTO auth_users(login, password_hash, role) VALUES (?, ?, 'CLIENT')";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, login);
            st.setString(2, PasswordUtil.hash(password));

            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[!] Register error: " + e.getMessage());
            return false;
        }
    }

    public boolean existsByLogin(String login) {
        String sql = "SELECT 1 FROM auth_users WHERE login = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, login);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public AuthUser getByLogin(String login) {
        String sql = "SELECT id, login, password_hash, role FROM auth_users WHERE login = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, login);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new AuthUser(
                            rs.getInt("id"),
                            rs.getString("login"),
                            rs.getString("password_hash"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("[!] GetByLogin error: " + e.getMessage());
        }
        return null;
    }

    public AuthUser login(String login, String password) {
        AuthUser user = getByLogin(login);
        if (user == null) return null;

        String inputHash = PasswordUtil.hash(password);
        return inputHash.equals(user.getPasswordHash()) ? user : null;
    }

    public boolean setRole(String login, String role) {
        String sql = "UPDATE auth_users SET role = ? WHERE login = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setString(1, role);
            st.setString(2, login);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[!] SetRole error: " + e.getMessage());
            return false;
        }
    }
}