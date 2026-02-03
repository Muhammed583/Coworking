package repository;

import model.Workspace;
import util.DatabaseConnection;
import java.sql.*;

public class UserRepository {

    public boolean showAvailableWorkspaces() {
        String sql = "SELECT * FROM workspaces";
        boolean hasData = false;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Available workspaces ---");
            while (rs.next()) {
                hasData = true;
                Workspace ws = new Workspace(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("hourly_rate")
                );

                System.out.println("ID: " + ws.getId() +
                        " | " + ws.getName() +
                        " | " + ws.getHourlyRate() + " tg/h");
            }
        } catch (SQLException e) {
            System.out.println("Error showing workspaces: " + e.getMessage());
        }
        return hasData;
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

    public void createBooking(int userId, String userName, int wsId, double price) {
        String sql = "INSERT INTO bookings (user_id, user_name, workspace_id, total_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, userName);
            pstmt.setInt(3, wsId);
            pstmt.setDouble(4, price);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Booking error: " + e.getMessage());
        }
    }

    public void showMyBookings(int userId) {
        String sql = "SELECT b.user_name, w.name as ws_name, b.total_price, b.booking_date " +
                "FROM bookings b " +
                "JOIN workspaces w ON b.workspace_id = w.id " +
                "WHERE b.user_id = ? " +
                "ORDER BY b.booking_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("\n--- YOUR PERSONAL HISTORY ---");
                boolean hasHistory = false;
                while (rs.next()) {
                    hasHistory = true;
                    System.out.println("User: [" + rs.getString("user_name") +
                            "] | Space: " + rs.getString("ws_name") +
                            " | Price: " + rs.getDouble("total_price") +
                            " tg | Date: " + rs.getTimestamp("booking_date"));
                }
                if (!hasHistory) System.out.println("You have no bookings yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
    }
}