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
            System.out.println("\n--- Система брони коворкинга ---");
            System.out.println("1. Регистрация нового клиента");
            System.out.println("2. Забронировать рабочее место");
            System.out.println("3. Просмотр всех бронирований");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) break;

            switch (choice) {
                case 1:
                    System.out.print("Введите ФИО: "); String n = scanner.nextLine();
                    System.out.print("Тип (Student/Resident): "); String t = scanner.nextLine();
                    userRepo.save(new User(0, n, t));
                    break;
                case 2:
                    makeBooking();
                    break;
                case 3:
                    userRepo.showAllBookings();
                    break;
                default:
                    System.out.println("Неверный ввод!");
            }
        }
    }

    private void makeBooking() {
        userRepo.showAvailableWorkspaces();
        System.out.print("\nВведите ID места: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (!userRepo.workspaceExists(id)) {
            System.out.println("Ошибка: Такого ID не существует!");
            return;
        }

        System.out.print("Введите ваше имя для записи: ");
        String userName = scanner.nextLine();

        System.out.print("Ваш статус: ");
        String ut = scanner.next();

        System.out.print("Количество часов: ");
        int h = scanner.nextInt();

        System.out.print("Категория (1-Обычная, 2-VIP +500тг): ");
        boolean v = (scanner.nextInt() == 2);

        double p = bookingService.calculatePrice(ut, h, v);

        // Сохраняем итоговую запись в историю
        userRepo.createBooking(userName, id, p);

        System.out.println("\nЗабронировано! Итого к оплате: " + p + " тенге.");
    }

    public static void main(String[] args) {
        new MyApplication().start();
    }
}