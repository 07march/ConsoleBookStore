package by.consoleapp.validator;

import by.entity.Moderator;

public class ModeratorValidator extends UserValidator {

    public static boolean valid(Moderator moderator) {
        return moderator.getStore() != null;
    }
}
