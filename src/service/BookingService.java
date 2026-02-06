package service;
import repository.RepositoryFactory;

import java.util.List;
import java.util.Scanner;

public class BookingService {

    public boolean showWorkspaces() {
        List<model.Workspace> list = repository.RepositoryFactory.workspaceRepo().findAll();

        if (list.isEmpty()) {
            System.out.println("\n[!] No workspaces available at the moment.");
            return false;
        }

        System.out.println("\n--- Available Workspaces ---");
        System.out.printf("%-4s | %-20s | %-10s%n", "ID", "Name", "Price");
        System.out.println("--------------------------------------------");

        for (model.Workspace ws : list) {
            System.out.printf(
                    "%-4d | %-20s | %.0f tg/h%n",
                    ws.getId(),
                    ws.getName().trim(),
                    ws.getHourlyRate()
            );
        }

        System.out.println("--------------------------------------------");
        return true;
    }

    public void bookWorkspace(Scanner sc, int userId) {
        System.out.print("Enter workspace ID: ");
        int workspaceId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter hours: ");
        int hours = Integer.parseInt(sc.nextLine());

        boolean ok = RepositoryFactory.bookingRepo().createBooking(userId, workspaceId, hours);
        System.out.println(ok ? "[+] Booking created" : "[!] Booking failed");
    }

    public void myHistory(int userId) {
        RepositoryFactory.bookingRepo().showBookingHistory(userId);
    }
}
