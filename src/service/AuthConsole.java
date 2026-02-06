package service;

import model.AuthUser;
import repository.RepositoryFactory; // Теперь используем фабрику
import java.util.Scanner;

public class AuthConsole {
    private final repository.AuthRepository authRepo = RepositoryFactory.authRepo();

    public AuthUser register(Scanner sc) {
        System.out.print("New login: ");
        String login = sc.nextLine().trim();

        System.out.print("New password: ");
        String password = sc.nextLine().trim();

        boolean ok = authRepo.register(login, password);
        if (!ok) {
            System.out.println("[!] Registration failed (login might be taken)");
            return null;
        }

        System.out.println("[+] Registered successfully");
        return authRepo.login(login, password);
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

        System.out.println("[+] Welcome back, " + user.getLogin());
        return user;
    }
}