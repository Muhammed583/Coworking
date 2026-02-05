package repository;

import model.Workspace;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserRepository {

    public void showAvailableWorkspaces() {
        String sql = "SELECT id, name, hourly_rate FROM workspaces ORDER BY id";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Available workspaces ---");
            boolean has = false;

            while (rs.next()) {
                has = true;
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                " | " + rs.getString("name") +
                                " | " + rs.getDouble("hourly_rate") + " tg/h"
                );
            }

            if (!has) System.out.println("[!] No workspaces found.");

        } catch (Exception e) {
            System.out.println("ShowWorkspaces error: " + e.getMessage());
        }
    }
}
