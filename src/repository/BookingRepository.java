package repository;

import repository.interfaces.IBookingRepository;
import util.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

public class BookingRepository implements IBookingRepository {

    public boolean createBooking(int userId, int workspaceId, int hours) {
        String insertSql = """
                INSERT INTO bookings(user_id, workspace_id, hours, total_price, created_at)
                VALUES (?, ?, ?, (SELECT hourly_rate FROM workspaces WHERE id = ?) * ?, NOW())
                """;

        String updateWorkspaceSql = "UPDATE workspaces SET is_occupied = TRUE WHERE id = ?";
        String selectForUpdateSql = "SELECT is_occupied FROM workspaces WHERE id = ? FOR UPDATE";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try {
                conn.setAutoCommit(false);

                // Lock the workspace row to avoid race conditions
                try (PreparedStatement checkSt = conn.prepareStatement(selectForUpdateSql)) {
                    checkSt.setInt(1, workspaceId);
                    try (ResultSet rs = checkSt.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            System.out.println("[!] DB Error: workspace not found");
                            return false;
                        }
                        boolean occupied = rs.getBoolean("is_occupied");
                        if (occupied) {
                            conn.rollback();
                            System.out.println("Error: This place occupied");
                            return false;
                        }
                    }
                }

                try (PreparedStatement st = conn.prepareStatement(insertSql)) {
                    st.setInt(1, userId);
                    st.setInt(2, workspaceId);
                    st.setInt(3, hours);
                    st.setInt(4, workspaceId);
                    st.setInt(5, hours);

                    int inserted = st.executeUpdate();
                    if (inserted == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement st2 = conn.prepareStatement(updateWorkspaceSql)) {
                    st2.setInt(1, workspaceId);
                    int updated = st2.executeUpdate();
                    if (updated == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
                System.out.println("[!] DB Error: " + e.getMessage());
                return false;
            } finally {
                try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            }
        } catch (SQLException e) {
            System.out.println("[!] DB Connection error: " + e.getMessage());
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