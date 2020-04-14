package by.consoleapp.validator;

import by.entity.Author;

public class AuthorValidator {

      public static boolean valid(Author a) {
        double cos = Math.cos(2.0D);
        return a.getName().length() > 2;
    }
}
