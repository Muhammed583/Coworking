package service;

import model.User;
import repository.AuthRepository;
import java.util.Scanner;

public class AuthConsole {
    private final AuthRepository repo = new AuthRepository();

    public User run(Scanner scanner) {
        while (true) {
            System.out.println("\n--- AUTH MENU ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Back");
            System.out.print("Select action: ");

            int c = readInt(scanner);
            if (c == 0) return null;

            if (c == 1) {
                System.out.print("Login: ");
                String login = scanner.nextLine().trim();
                System.out.print("Password: ");
                String pass = scanner.nextLine();

                User user = repo.authenticate(login, pass);
                if (user != null) {
                    System.out.println("Login success");
                    return user;
                } else {
                    System.out.println("Wrong login or password");
                }
            }

            if (c == 2) {
                System.out.print("New login: ");
                String login = scanner.nextLine().trim();
                if (repo.loginExists(login)) {
                    System.out.println("Login already exists");
                    continue;
                }
                System.out.print("Password: ");
                String p1 = scanner.nextLine();
                System.out.print("Confirm password: ");
                String p2 = scanner.nextLine();

                if (!p1.equals(p2)) {
                    System.out.println("Passwords do not match");
                    continue;
                }
                if (repo.register(login, p1)) {
                    System.out.println("Registration successful");
                } else {
                    System.out.println("Registration failed");
                }
            }
        }
    }

    private int readInt(Scanner scanner) {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }
}