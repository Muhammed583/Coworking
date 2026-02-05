package service;

import model.AuthUser;
import repository.AuthRepository;

import java.util.Scanner;

public class AuthConsole {
    private final AuthRepository authRepo = new AuthRepository();

    public AuthUser register(Scanner sc) {
        System.out.print("New login: ");
        String login = sc.nextLine().trim();

        System.out.print("New password: ");
        String password = sc.nextLine().trim();

        boolean ok = authRepo.register(login, password);
        if (!ok) {
            System.out.println("[!] Registration failed");
            return null;
        }

        System.out.println("[+] Registered successfully");
        // Auto-login after register
        AuthUser user = authRepo.login(login, password);
        if (user != null) {
            System.out.println("[+] Logged in as: " + user.getLogin());
        }
        return user;
    }

    public AuthUser login(Scanner sc) {
        System.out.print("Login: ");
        String login = sc.nextLine().trim();

        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        AuthUser user = authRepo.login(login, password);
        if (user == null) {
            System.out.println("[!] Invalid login or password");
            return null;
        }

        System.out.println("[+] Logged in as: " + user.getLogin());
        return user;
    }
}
