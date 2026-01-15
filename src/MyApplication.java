import model.User;
import repository.UserRepository;
import service.BookingService;
import java.util.Scanner;

public class MyApplication {
    private final UserRepository userRepo = new UserRepository();
    private final BookingService bookingService = new BookingService();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- Система Коворкинга ---");
            System.out.println("1. Регистрация нового пользователя");
            System.out.println("2. Посмотреть места и Забронировать");
            System.out.println("0. Выход");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            if (choice == 0) {
                System.out.println("Завершение работы...");
                break;
            }

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    makeBooking();
                    break;
                default:
                    System.out.println("Ошибка: Неверный пункт меню!");
            }
        }
    }

    private void registerUser() {
        System.out.print("Введите ФИО пользователя: ");
        String name = scanner.nextLine();
        System.out.print("Введите тип (STUDENT/RESIDENT): ");
        String type = scanner.nextLine();

        userRepo.save(new User(0, name, type));
    }

    private void makeBooking() {
        userRepo.showAvailableWorkspaces();

        System.out.print("\nВведите ID выбранного места: ");
        int workspaceId = scanner.nextInt();

        if (!userRepo.workspaceExists(workspaceId)) {
            System.out.println("!!! ОШИБКА: Места с ID " + workspaceId + " не существует в базе данных!");
            return;
        }

        System.out.print("Введите тип пользователя для расчета скидки: ");
        String type = scanner.next();

        System.out.print("Введите количество часов: ");
        int hours = scanner.nextInt();

        System.out.println("Выберите категорию брони: 1. Стандарт 2. VIP (+500 тенге)");
        int vipChoice = scanner.nextInt();
        boolean isVip = (vipChoice == 2);

        double finalPrice = bookingService.calculatePrice(type, hours, isVip);

        System.out.println("\n--- Бронирование подтверждено ---");
        System.out.println("Место ID: " + workspaceId + " забронировано.");
        System.out.println("Итоговая стоимость: " + finalPrice + " тенге.");
    }

    public static void main(String[] args) {
        new MyApplication().start();
    }
}