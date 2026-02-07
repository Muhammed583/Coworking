package repository;

import model.Workspace;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceRepository {

    public List<Workspace> findAll() {
        String sql = "SELECT id, name, hourly_rate, COALESCE(category, 'GENERAL') AS category FROM workspaces ORDER BY id";
        List<Workspace> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                list.add(new Workspace(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("hourly_rate"),
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            System.out.println("[!] FindAll workspaces error: " + e.getMessage());
        }
        return list;
    }

    public boolean existsById(int id) {
        String sql = "SELECT 1 FROM workspaces WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("[!] existsById error: " + e.getMessage());
            return false;
        }
    }
}
