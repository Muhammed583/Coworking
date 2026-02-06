package repository;

import util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BookingRepository {

    public boolean createBooking(int userId, int workspaceId, int hours) {
        String sql = """
                INSERT INTO bookings(user_id, workspace_id, hours, total_price, created_at)
                VALUES (
                    ?, ?, ?,
                    (SELECT hourly_rate FROM workspaces WHERE id = ?) * ?,
                    NOW()
                )
                """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, userId);
            st.setInt(2, workspaceId);
            st.setInt(3, hours);
            st.setInt(4, workspaceId);
            st.setInt(5, hours);

            return st.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("CreateBooking error: " + e.getMessage());
            return false;
        }
    }

    public void showBookingHistory(int userId) {
        String sql = """
            SELECT b.id AS booking_id,
                   w.name AS workspace_name,
                   w.hourly_rate,
                   b.hours,
                   b.total_price,
                   b.created_at
            FROM bookings b
            JOIN workspaces w ON w.id = b.workspace_id
            WHERE b.user_id = ?
            ORDER BY b.id DESC
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, userId);

            try (ResultSet rs = st.executeQuery()) {
                System.out.println("\n--- 📜 MY BOOKING HISTORY ---");

                System.out.printf("%-4s | %-20s | %-6s | %-5s | %-10s | %-16s%n",
                        "ID", "Workspace", "Rate", "Hrs", "Total", "Date");
                System.out.println("---------------------------------------------------------------------------");

                boolean has = false;
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

                while (rs.next()) {
                    has = true;
                    Timestamp ts = rs.getTimestamp("created_at");
                    String dateStr = sdf.format(ts);

                    System.out.printf("#%-3d | %-20s | %-6.0f | %-5d | %-10.0f | %-16s%n",
                            rs.getInt("booking_id"),
                            rs.getString("workspace_name"),
                            rs.getDouble("hourly_rate"),
                            rs.getInt("hours"),
                            rs.getDouble("total_price"),
                            dateStr
                    );
                }

                if (!has) {
                    System.out.println("        You haven't booked anything yet.        ");
                }
                System.out.println("---------------------------------------------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("History error: " + e.getMessage());
        }
    }
}