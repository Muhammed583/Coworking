package service;

import model.AuthUser;
import repository.AuthRepository;
import java.util.Scanner;

public class AuthConsole {
    private final AuthRepository authRepo = new AuthRepository();

    public AuthUser register(Scanner sc) {
        String login;
        while (true) {
            System.out.print("New login: ");
            login = sc.nextLine().trim();

            if (login.isEmpty()) {
                System.out.println("[!] Login cannot be empty");
                continue;
            }

            if (authRepo.existsByLogin(login)) {
                System.out.println("[!] Login already exists, please choose another");
                continue;
            }
            break;
        }

        System.out.print("New password: ");
        String password = sc.nextLine().trim();

        if (authRepo.register(login, password)) {
            System.out.println("[+] Registered successfully");
            return authRepo.login(login, password); // Авто-логин
        } else {
            System.out.println("[!] Registration failed (DB error)");
            return null;
        }
    }

    public AuthUser login(Scanner sc) {
        System.out.print("Login: ");
        String login = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        AuthUser user = authRepo.login(login, password);

        if (user != null) {
            System.out.println("[+] Welcome, " + user.getLogin() + "!");
            return user;
        } else {
            System.out.println("[!] Invalid login or password");
            return null;
        }
    }
}