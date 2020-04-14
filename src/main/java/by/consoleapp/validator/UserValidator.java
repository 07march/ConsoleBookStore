package by.consoleapp.validator;

import by.entity.User;

public class UserValidator {
    public static boolean valid(User user) {
        return user.getFirstName() != null && user.getLastName() != null && user.getEmail().length() > 4 && user.getPassword().length() > 4;
    }
}
