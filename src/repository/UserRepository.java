package repository;

import util.DatabaseConnection;
import model.User;
import java.sql.*;

public class UserRepository {
    public void save(User user) {
        String sql = "INSERT INTO users (full_name, membership_type) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getMembershipType());
            pstmt.executeUpdate();
            System.out.println("Пользователь успешно сохранен!");
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении: " + e.getMessage());
        }
    }

    public void showAvailableWorkspaces() {
        String sql = "SELECT * FROM workspaces";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Список доступных мест ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getDouble("hourly_rate") + " тг/час");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка загрузки мест: " + e.getMessage());
        }
    }

    public boolean workspaceExists(int id) {
        String sql = "SELECT id FROM workspaces WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }
}