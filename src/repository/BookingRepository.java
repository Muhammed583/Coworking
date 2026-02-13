package repository;

import repository.interfaces.IBookingRepository;
import util.DatabaseConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

public class BookingRepository implements IBookingRepository {

    public boolean createBooking(int userId, int workspaceId, Timestamp startTime, Timestamp endTime) {
        String selectWorkspaceSql = "SELECT hourly_rate FROM workspaces WHERE id = ? FOR UPDATE";
        String overlapSql = "SELECT COUNT(*) AS cnt FROM bookings WHERE workspace_id = ? AND NOT (end_time <= ? OR start_time >= ?)";
        String insertSql = "INSERT INTO bookings(user_id, workspace_id, start_time, end_time, hours, total_price, created_at) VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            try {
                conn.setAutoCommit(false);

                double hourlyRate;

                try (PreparedStatement st = conn.prepareStatement(selectWorkspaceSql)) {
                    st.setInt(1, workspaceId);
                    try (ResultSet rs = st.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            System.out.println("[!] DB Error: workspace not found");
                            return false;
                        }
                        hourlyRate = rs.getDouble("hourly_rate");
                    }
                }


                try (PreparedStatement st = conn.prepareStatement(overlapSql)) {
                    st.setInt(1, workspaceId);
                    st.setTimestamp(2, startTime);
                    st.setTimestamp(3, endTime);
                    try (ResultSet rs = st.executeQuery()) {
                        if (rs.next() && rs.getInt("cnt") > 0) {
                            conn.rollback();
                            System.out.println("Error: Workspace already booked in this period");
                            return false;
                        }
                    }
                }

                long diffMs = endTime.getTime() - startTime.getTime();
                long hours = diffMs / (1000 * 60 * 60);
                if (hours <= 0) {
                    conn.rollback();
                    System.out.println("[!] Invalid time range: end must be after start");
                    return false;
                }

                double totalPrice = hourlyRate * hours;

                try (PreparedStatement st = conn.prepareStatement(insertSql)) {
                    st.setInt(1, userId);
                    st.setInt(2, workspaceId);
                    st.setTimestamp(3, startTime);
                    st.setTimestamp(4, endTime);
                    st.setInt(5, (int) hours);
                    st.setDouble(6, totalPrice);

                    int inserted = st.executeUpdate();
                    if (inserted == 0) {
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
            SELECT b.id, w.name, w.hourly_rate, b.start_time, b.end_time, b.total_price, b.created_at
            FROM bookings b JOIN workspaces w ON w.id = b.workspace_id
            WHERE b.user_id = ? ORDER BY b.id DESC
            """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, userId);
            try (ResultSet rs = st.executeQuery()) {
                System.out.println("\n---  MY BOOKING HISTORY ---");
                System.out.printf("%-4s | %-20s | %-7s | %-22s | %-10s | %-16s%n",
                        "ID", "Workspace", "Rate", "Period", "Total", "Booked At");
                System.out.println("----------------------------------------------------------------------------------------------");

                boolean hasData = false;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                while (rs.next()) {
                    hasData = true;
                    String period = sdf.format(rs.getTimestamp("start_time")) + " - " + sdf.format(rs.getTimestamp("end_time"));
                    System.out.printf("#%-3d | %-20s | %-7.0f | %-22s | %-10.0f | %-16s%n",
                            rs.getInt("id"), rs.getString("name").trim(), rs.getDouble("hourly_rate"),
                            period, rs.getDouble("total_price"), sdf.format(rs.getTimestamp("created_at")));
                }
                if (!hasData) System.out.println("        You haven't booked anything yet.        ");
                System.out.println("----------------------------------------------------------------------------------------------");
                return hasData;
            }
        } catch (SQLException e) {
            System.out.println("[!] History error: " + e.getMessage());
            return false;
        }
    }
}