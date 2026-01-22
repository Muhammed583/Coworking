package service;

import repository.AuthRepository;

import java.io.Console;
import java.util.Scanner;

public class AuthConsole {
    private final AuthRepository repo = new AuthRepository();

    public String run(Scanner scanner) {
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
                String pass = readPassword("Password: ");

                if (repo.authenticate(login, pass)) {
                    System.out.println("Login success");
                    return login;
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

                String p1 = readPassword("Password: ");
                String p2 = readPassword("Confirm password: ");

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

    private String readPassword(String label) {
        Console console = System.console();
        if (console != null) {
            char[] pass = console.readPassword(label);
            return new String(pass);
        } else {
            System.out.print(label);
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    }

    private int readInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.print("Enter number: ");
            }
        }
    }
}
