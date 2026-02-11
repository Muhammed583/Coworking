package service.interfaces;

import model.AuthUser;

import java.util.Scanner;

public interface IAuthConsole {
    AuthUser register(Scanner sc);
    AuthUser login(Scanner sc);
}
