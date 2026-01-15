package service;
import model.User;

public class BookingService {
    public double calculatePrice(String type, int hours, boolean isVip) {
        double rate = 1000.0; // Норм цена
        double totalPrice;

        // Логика скидки
        if ("STUDENT".equalsIgnoreCase(type)) {
            totalPrice = rate * hours * 0.8;
        } else {
            totalPrice = rate * hours;
        }

        // Кидаем Наценку
        if (isVip) {
            totalPrice += 500.0;
        }

        return totalPrice;
    }
}