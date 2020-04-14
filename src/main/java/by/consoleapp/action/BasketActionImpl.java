package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.entity.Book;

public class BasketActionImpl implements BasketAction {
    private Writer writer;
    private Reader reader;

    public BasketActionImpl(Writer writer, Reader reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void deleteBookInBasket() {
        Book[] books = ConsoleApplicationImpl.session.getBasket().getBooks();
        int i;
        for (i = 0; i < books.length && books[i] != null; ++i) {
            writer.write(i + " " + books[i]);
        }
        writer.write("Выберите номер книги");
        i = reader.readNumber();
        if (books.length - 1 - i >= 0) {
            System.arraycopy(books, i + 1, books, i, books.length - 1 - i);
        }
        writer.write("Книга удалена из корзины");
    }

    public void deleteAllInBasket() {
        Book[] books = ConsoleApplicationImpl.session.getBasket().getBooks();
        for (int i = 0; i < books.length; ++i) {
            books[i] = null;
        }
        writer.write("Корзина пуста");
    }

    public void showAllBooksInBasket() {
        Book[] books = ConsoleApplicationImpl.session.getBasket().getBooks();
        if (books.length == 0) {
            writer.write("Корзина пуста");
        } else {
            for (int i = 0; i < books.length; ++i) {
                Book book = books[i];
                if (book == null) {
                    break;
                }
                writer.write("Книга " + book.getTitle());
            }
        }
    }
}
