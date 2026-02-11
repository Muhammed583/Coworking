package service.interfaces;

import java.util.Scanner;

public interface IBookingService {
    boolean showWorkspaces();
    boolean bookWorkspace(Scanner sc, int userId);
    void myHistory(int userId);
}
