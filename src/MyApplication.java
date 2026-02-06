import model.AuthUser;
import repository.RepositoryFactory;
import service.AuthConsole;
import service.BookingService;

import java.util.Scanner;

public class MyApplication {

    private final Scanner sc = new Scanner(System.in);
    private final AuthConsole authConsole = new AuthConsole();
    private final BookingService bookingService = new BookingService();

    private AuthUser currentUser = null;

    public static void main(String[] args) {
        new MyApplication().start();
    }

    public void start() {
        while (true) {
            clear();

            if (currentUser == null) {
                printGuestMenu();
                int choice = readInt();

                if (choice == 0) break;

                if (choice == 1) currentUser = authConsole.register(sc);
                else if (choice == 2) currentUser = authConsole.login(sc);
                else System.out.println("[!] Invalid option");

                pause();
            } else {
                printUserMenu(currentUser.getLogin());
                int choice = readInt();

                if (choice == 0) break;

                if (choice == 1) bookingService.showWorkspaces();
                else if (choice == 2) bookingService.bookWorkspace(sc, currentUser.getId());
                else if (choice == 3) bookingService.myHistory(currentUser.getId());
                else if (choice == 4) {
                    currentUser = null;
                    System.out.println("[+] Logged out");
                } else System.out.println("[!] Invalid option");

                pause();
            }
        }

        System.out.println("Bye!");
    }

    private void printGuestMenu() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║      COWORKING BOOKING SYSTEM      ║");
        System.out.println("║          Status: Guest             ║");
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1) Register");
        System.out.println("2) Login");
        System.out.println("0) Exit");
        System.out.print("> ");
    }

    private void printUserMenu(String login) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║      COWORKING BOOKING SYSTEM      ║");
        System.out.printf ("║   Status: Logged in as %-10s   ║%n", login);
        System.out.println("╚════════════════════════════════════╝");
        System.out.println("1) View workspaces");
        System.out.println("2) Book workspace");
        System.out.println("3) My booking history");
        System.out.println("4) Logout");
        System.out.println("0) Exit");
        System.out.print("> ");
    }

    private int readInt() {
        while (true) {
            String s = sc.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (Exception e) {
                System.out.print("[!] Enter a number\n> ");
            }
        }
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        sc.nextLine();
    }

    private void clear() {
        for (int i = 0; i < 25; i++) System.out.println();
    }
}
