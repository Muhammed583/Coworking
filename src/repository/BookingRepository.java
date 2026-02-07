package repository;

import util.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

public class BookingRepository {

    public boolean createBooking(int userId, int workspaceId, int hours) {
        String sql = """
                INSERT INTO bookings(user_id, workspace_id, hours, total_price, created_at)
                VALUES (?, ?, ?, (SELECT hourly_rate FROM workspaces WHERE id = ?) * ?, NOW())
                """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, workspaceId);
            st.setInt(3, hours);
            st.setInt(4, workspaceId);
            st.setInt(5, hours);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[!] DB Error: " + e.getMessage());
            return false;
        }
    }

    public boolean showBookingHistory(int userId) {
        String sql = """
            SELECT b.id, w.name, w.hourly_rate, b.hours, b.total_price, b.created_at
            FROM bookings b JOIN workspaces w ON w.id = b.workspace_id
            WHERE b.user_id = ? ORDER BY b.id DESC
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                System.out.println("\n---  MY BOOKING HISTORY ---");
                System.out.printf("%-4s | %-20s | %-7s | %-4s | %-10s | %-16s%n",
                        "ID", "Workspace", "Rate", "Hrs", "Total", "Date");
                System.out.println("---------------------------------------------------------------------------");

                boolean hasData = false;
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                while (rs.next()) {
                    hasData = true;
                    System.out.printf("#%-3d | %-20s | %-7.0f | %-4d | %-10.0f | %-16s%n",
                            rs.getInt("id"), rs.getString("name").trim(), rs.getDouble("hourly_rate"),
                            rs.getInt("hours"), rs.getDouble("total_price"), sdf.format(rs.getTimestamp("created_at")));
                }
                if (!hasData) System.out.println("        You haven't booked anything yet.        ");
                System.out.println("---------------------------------------------------------------------------");
                return hasData;
            }
        } catch (SQLException e) {
            System.out.println("[!] History error: " + e.getMessage());
            return false;
        }
    }
}