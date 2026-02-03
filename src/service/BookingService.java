package service;

public class BookingService {
    public double calculatePrice(String type, int hours, boolean isVip) {
        double rate = 1000.0;
        double price = ("STUDENT".equalsIgnoreCase(type)) ? (rate * hours * 0.8) : (rate * hours);
        if (isVip) price += 500;
        return price;
    }
}