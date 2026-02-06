package service;

import model.Workspace;
import repository.RepositoryFactory;

import java.util.List;
import java.util.Scanner;

public class BookingService {

    public void showWorkspaces() {
        List<Workspace> list = RepositoryFactory.workspaceRepo().findAll();

        System.out.println("\n--- Available workspaces ---");
        // ✅ lambda requirement
        list.forEach(ws -> System.out.printf(
                "ID: %d | %s | %.1f tg/h | %s%n",
                ws.getId(), ws.getName(), ws.getHourlyRate(), ws.getCategory()
        ));
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
