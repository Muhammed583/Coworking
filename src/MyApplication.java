import model.AuthUser;
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
            if (currentUser == null) {
                printGuestMenu();
                int choice = readInt();
                if (choice == 0) break;
                if (choice == 1) currentUser = authConsole.register(sc);
                else if (choice == 2) currentUser = authConsole.login(sc);
                pause();
            } else {
                printUserMenu(currentUser.getLogin());
                int choice = readInt();
                if (choice == 0) break;

                if (choice == 1) {
                    if (bookingService.showWorkspaces()) {
                        System.out.print("Do you want to book? (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            if (bookingService.bookWorkspace(sc, currentUser.getId())) {
                                System.out.println("[+] Booking successful!");
                            } else {
                                System.out.println("[!] Booking failed.");
                            }
                        }
                    }
                } else if (choice == 2) {
                    bookingService.myHistory(currentUser.getId());
                } else if (choice == 3) {
                    currentUser = null;
                    System.out.println("[+] Logged out");
                }
                pause();
            }
        }
        System.out.println("Bye!");
    }

    private void printGuestMenu() {
        System.out.println("\n=== COWORKING SYSTEM (Guest) ===");
        System.out.println("1) Register\n2) Login\n0) Exit");
        System.out.print("> ");
    }

    private void printUserMenu(String login) {
        System.out.println("\n=== Welcome, " + login + " ===");
        System.out.println("1) View & Book Workspaces\n2) My History\n3) Logout\n0) Exit");
        System.out.print("> ");
    }

    private int readInt() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.print("[!] Enter a number: "); }
        }
    }

    private void pause() {
        System.out.println("\nPress ENTER to continue...");
        sc.nextLine();
    }
}