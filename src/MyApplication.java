import repository.UserRepository;
import service.BookingService;
import service.AuthConsole;
import java.util.Scanner;

public class MyApplication {
    private final UserRepository userRepo = new UserRepository();
    private final BookingService bookingService = new BookingService();
    private final AuthConsole authConsole = new AuthConsole();
    private final Scanner scanner = new Scanner(System.in);

    private String currentLogin = null;

    public void start() {
        while (true) {
            printMenu();
            int choice = readChoice();

            if (choice == 0) {
                System.out.println("Exiting... Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    String login = authConsole.run(scanner);
                    if (login != null) {
                        currentLogin = login;
                    }
                    break;

                case 2:
                    if (currentLogin == null) {
                        System.out.println("\n[!] Error: You must authorize first (Choice 1)!");
                    } else {
                        makeBooking();
                    }
                    break;

                case 3:
                    userRepo.showAllBookings();
                    break;

                default:
                    System.out.println("Invalid input! Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===============================");
        System.out.println("   COWORKING BOOKING SYSTEM    ");
        System.out.println("===============================");
        if (currentLogin != null) {
            System.out.println(" STATUS: Logged in as [" + currentLogin + "]");
        } else {
            System.out.println(" STATUS: Not authorized");
        }
        System.out.println("-------------------------------");
        System.out.println("1. Authorization (Login/Register)");
        System.out.println("2. Book a workspace");
        System.out.println("3. View booking history");
        System.out.println("0. Exit");
        System.out.print("Select action: ");
    }

    private int readChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private void makeBooking() {
        userRepo.showAvailableWorkspaces();

        System.out.print("\nEnter workspace ID: ");
        int wsId = Integer.parseInt(scanner.nextLine());

        if (!userRepo.workspaceExists(wsId)) {
            System.out.println("Error: Workspace with this ID does not exist!");
            return;
        }

        System.out.print("Enter your status (Student/Resident): ");
        String status = scanner.nextLine();

        System.out.print("Number of hours: ");
        int hours = Integer.parseInt(scanner.nextLine());

        System.out.print("Category (1-Regular, 2-VIP +500): ");
        boolean isVip = (Integer.parseInt(scanner.nextLine()) == 2);

        double finalPrice = bookingService.calculatePrice(status, hours, isVip);

        userRepo.createBooking(currentLogin, wsId, finalPrice);

        System.out.println("\nSuccess!");
        System.out.println("Workspace #" + wsId + " booked for " + currentLogin);
        System.out.println("Total price: " + finalPrice + " tg.");
    }

    public static void main(String[] args) {
        new MyApplication().start();
    }
}