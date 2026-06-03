package controller;

import dao.loginDao;
import dao.signupDao;
import model.loginModel;
import model.signupModel;

/**
 * Concrete implementation of the AuthService interface backing operations with database SQL DAOs.
 */
public class DatabaseAuthService implements AuthService {
    private final loginDao loginDao = new loginDao();
    private final signupDao signupDao = new signupDao();

    @Override
    public boolean login(loginModel credentials) {
        return loginDao.validateUser(credentials);
    }

    @Override
    public boolean register(signupModel userDetails) {
        return signupDao.createUser(userDetails);
    }

    @Override
    public boolean usernameExists(String username) {
        return signupDao.usernameExists(username);
    }

    @Override
    public boolean emailExists(String email) {
        return signupDao.emailExists(email);
    }
}
