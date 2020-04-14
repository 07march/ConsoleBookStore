package by.consoleapp.validator;

import by.entity.Book;

public class BookValidator {

    public static boolean valid(Book b) {
        return b.getAuthor() != null && b.getPrice().intValue() > 0 && b.getTitle().length() > 2 && b.getDescription() != null;
    }
}
