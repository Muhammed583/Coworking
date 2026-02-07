package service;

import model.Workspace;
import repository.RepositoryFactory;
import java.util.List;
import java.util.Scanner;

public class BookingService {

    public boolean showWorkspaces() {
        List<Workspace> list = RepositoryFactory.workspaceRepo().findAll();
        if (list.isEmpty()) {
            System.out.println("\n[!] No workspaces available.");
            return false;
        }
        System.out.println("\n--- Available Workspaces ---");
        System.out.printf("%-4s | %-20s | %-10s%n", "ID", "Name", "Price");
        System.out.println("--------------------------------------------");
        list.forEach(ws -> System.out.printf("%-4d | %-20s | %.0f tg/h%n",
                ws.getId(), ws.getName().trim(), ws.getHourlyRate()));
        System.out.println("--------------------------------------------");
        return true;
    }

    public boolean bookWorkspace(Scanner sc, int userId) {
        try {
            System.out.print("Enter workspace ID to book: ");
            int wsId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter duration (hours): ");
            int hrs = Integer.parseInt(sc.nextLine());

            return RepositoryFactory.bookingRepo().createBooking(userId, wsId, hrs);
        } catch (Exception e) {
            System.out.println("[!] Invalid input. Please enter numbers.");
            return false;
        }
    }

    public void myHistory(int userId) {
        RepositoryFactory.bookingRepo().showBookingHistory(userId);
    }
}
