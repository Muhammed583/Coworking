import model.User;
import repository.UserRepository;
import service.BookingService;
import service.AuthConsole;   
import java.util.Scanner;

public class MyApplication {
    private final UserRepository userRepo = new UserRepository();
    private final BookingService bookingService = new BookingService();
    private final Scanner scanner = new Scanner(System.in);

    private final AuthConsole authConsole = new AuthConsole(); 
    private String currentLogin = null; 

    public void start() {
        while (true) {
            System.out.println("\n--- Coworking space booking system ---");
            System.out.println("1. New client registration");
            System.out.println("2. Book a workspace");
            System.out.println("3.View all bookings");
            System.out.println("4. Authorization"); 
            System.out.println("0. Exit");
            System.out.print("Select action: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.print("Enter your full name: "); String n = scanner.nextLine();
                    System.out.print("Type (Student/Resident): "); String t = scanner.nextLine();
                    userRepo.save(new User(0, n, t));
                    break;
                case 2:
                    makeBooking();
                    break;
                case 3:
                    userRepo.showAllBookings();
                    break;

                case 4: 
                    String login = authConsole.run(scanner);
                    if (login != null) currentLogin = login;
                    break;

                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private void makeBooking() {
        userRepo.showAvailableWorkspaces();
        System.out.print("\nEnter location ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (!userRepo.workspaceExists(id)) {
            System.out.println("Error: This ID does not exist!");
            return;
        }

        System.out.print("Please enter your name to record: ");
        String userName = scanner.nextLine();

        System.out.print("Your status: ");
        String ut = scanner.nextLine();

        System.out.print("Number of hours: ");
        int h = scanner.nextInt();

        System.out.print("Category (1-Regular, 2-VIP +500 tenge): ");
        boolean v = (scanner.nextInt() == 2);

        double p = bookingService.calculatePrice(ut, h, v);

        userRepo.createBooking(userName, id, p);

        System.out.println("\nBooked! Total to pay: " + p + " tenge.");

        if (currentLogin != null) {
            System.out.println("Authorized as: " + currentLogin);
        } else {
            System.out.println("Not authorized.");
        }
    }

    public static void main(String[] args) {
        new MyApplication().start();
    }
}
