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
            System.out.println("User saved!");
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
    }

    public void showAvailableWorkspaces() {
        String sql = "SELECT * FROM workspaces";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Available seats ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getDouble("hourly_rate") + " tg/h");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean workspaceExists(int id) {
        String sql = "SELECT id FROM workspaces WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) { return rs.next(); }
        } catch (SQLException e) { return false; }
    }

    public void createBooking(String userName, int wsId, double price) {
        String sql = "INSERT INTO bookings (user_name, workspace_id, total_price) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setInt(2, wsId);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Booking Error: " + e.getMessage());
        }
    }
    public void showAllBookings() {
        String sql = "SELECT user_name, total_price, booking_date FROM bookings ORDER BY booking_date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- BOOKING HISTORY ---");
            while (rs.next()) {
                String name = rs.getString("user_name");
                double price = rs.getDouble("total_price");

                System.out.println("User: " + name + " | Sum: " + price + " tg");
            }
        } catch (SQLException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
    }
}