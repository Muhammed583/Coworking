import model.User;
import repository.UserRepository;
import service.BookingService;
import service.AuthConsole;
import java.util.Scanner;

public class MyApplication {
    private final UserRepository userRepo = new UserRepository();
    private final BookingService bookingService = new BookingService();
    private final AuthConsole authConsole = new AuthConsole();
    private final Scanner scanner = new Scanner(System.in);

    private User currentUser = null;

    public void start() {
        while (true) {
            printMenu();

            int choice = readChoice();

            if (choice == 0) {
                System.out.println("Exiting... Goodbye!");
                break;
            }

            if (currentUser == null) {

                if (choice == 1) {
                    currentUser = authConsole.run(scanner);
                } else {
                    System.out.println("Invalid input!");
                }
            } else {

                switch (choice) {
                    case 1:
                        makeBooking();
                        break;
                    case 2:
                        userRepo.showMyBookings(currentUser.getId());
                        break;
                    default:
                        System.out.println("Invalid input!");
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===============================");
        System.out.println("   COWORKING BOOKING SYSTEM    ");
        System.out.println("===============================");

        if (currentUser != null) {
            System.out.println(" STATUS: Logged in as [" + currentUser.getLogin() + "]");
            System.out.println("-------------------------------");
            System.out.println("1. Book a workspace");
            System.out.println("2. View my booking history");
            System.out.println("0. Exit");
        } else {
            System.out.println(" STATUS: Not authorized");
            System.out.println("-------------------------------");
            System.out.println("1. Authorization (Login/Register)");
            System.out.println("0. Exit");
        }

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
        if (!userRepo.showAvailableWorkspaces()) {
            System.out.println("No workspaces found in database.");
            return;
        }

        System.out.print("\nEnter workspace ID: ");
        int wsId = readChoice();

        if (!userRepo.workspaceExists(wsId)) {
            System.out.println("Error: Workspace with this ID does not exist!");
            return;
        }

        System.out.print("Enter your status (Student/Resident): ");
        String status = scanner.nextLine();

        System.out.print("Number of hours: ");
        int hours = readChoice();

        System.out.print("Category (1-Regular, 2-VIP +500): ");
        boolean isVip = (readChoice() == 2);

        double finalPrice = bookingService.calculatePrice(status, hours, isVip);

        userRepo.createBooking(currentUser.getId(), currentUser.getLogin(), wsId, finalPrice);

        System.out.println("\nSuccess!");
        System.out.println("Workspace #" + wsId + " booked for " + currentUser.getLogin());
        System.out.println("Total price: " + finalPrice + " tg.");
    }

    public static void main(String[] args) {
        new MyApplication().start();
    }
}