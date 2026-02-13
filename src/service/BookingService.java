package service;

import model.Workspace;
import repository.RepositoryFactory;
import service.interfaces.IBookingService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BookingService implements IBookingService {

    public boolean showWorkspaces() {
        List<Workspace> list = RepositoryFactory.workspaceRepo().findAll();
        if (list.isEmpty()) {
            System.out.println("\n[!] No workspaces available.");
            return false;
        }
        System.out.println("\n--- Available Workspaces ---");
        System.out.printf("%-4s | %-20s | %-15s%n", "ID", "Name", "Price");
        System.out.println("--------------------------------------------");
        list.forEach(ws -> System.out.printf("%-4d | %-20s | %-15s%n",
            ws.getId(), ws.getName().trim(), String.format("%.0f tg/h", ws.getHourlyRate())));
        System.out.println("--------------------------------------------");
        return true;
    }

    public boolean bookWorkspace(Scanner sc, int userId) {
        try {
            System.out.print("Enter workspace ID to book: ");
            int wsId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter date (dd/MM/yyyy): ");
            String date = sc.nextLine().trim();

            System.out.print("Enter start hour (0-23): ");
            int startHour = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Enter end hour (0-23): ");
            int endHour = Integer.parseInt(sc.nextLine().trim());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH");
            Date startD = sdf.parse(date + " " + startHour);
            Date endD = sdf.parse(date + " " + endHour);

            Timestamp startTs = new Timestamp(startD.getTime());
            Timestamp endTs = new Timestamp(endD.getTime());

            if (!endTs.after(startTs)) {
                System.out.println("[!] End time must be after start time.");
                return false;
            }
            if (startTs.before(new Timestamp(System.currentTimeMillis()))) {
                System.out.println("[!] Start time must be in the future.");
                return false;
            }

            return RepositoryFactory.bookingRepo().createBooking(userId, wsId, startTs, endTs);
        } catch (NumberFormatException ne) {
            System.out.println("[!] Invalid number input.");
            return false;
        } catch (Exception e) {
            System.out.println("[!] Invalid input. Please follow the requested formats.");
            return false;
        }
    }

    public void myHistory(int userId) {
        RepositoryFactory.bookingRepo().showBookingHistory(userId);
    }
}
