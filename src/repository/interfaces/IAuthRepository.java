package repository.interfaces;

import model.AuthUser;

public interface IAuthRepository {
    boolean register(String login, String password);
    boolean existsByLogin(String login);
    AuthUser getByLogin(String login);
    AuthUser login(String login, String password);
    boolean setRole(String login, String role);
}
