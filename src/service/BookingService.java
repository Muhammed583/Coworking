package service;

public class BookingService {
    public double calculatePrice(String type, int hours, boolean isVip) {
        double rate = 1000.0;
        double totalPrice;

        if ("STUDENT".equalsIgnoreCase(type)) {
            totalPrice = rate * hours * 0.8; // Скидка 20
        } else {
            totalPrice = rate * hours;
        }

        if (isVip) {
            totalPrice += 500.0; // наценка
        }

        return totalPrice;
    }
}