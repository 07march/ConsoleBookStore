package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.BookValidator;
import by.entity.Author;
import by.entity.Book;
import by.service.AuthorService;
import by.service.BookService;

import java.math.BigDecimal;

public class BookActionImpl implements BookAction {
    private static BookAction bookAction;
    private Reader reader;
    private Writer writer;
    private BookService bookService;
    private AuthorService authorService;

    private BookActionImpl() {
        throw new RuntimeException();
    }

    public BookActionImpl(Reader reader, Writer writer, BookService bookService, AuthorService authorService) {
        this.reader = reader;
        this.writer = writer;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static BookAction getInstance() {
        if (bookAction == null) {
            bookAction = new BookActionImpl();
        }
        return bookAction;
    }

    public void add() {
        writer.write("Введите название книги");
        String title = reader.read();
        Author[] all = authorService.findAll();
        if (all.length == 0) {
            writer.write("Автора нет в базе данных");
        } else {
            for (int i = 0; i < all.length && all[i] != null; ++i) {
                writer.write("Выберите автора");
                writer.write(i + " " + all[i].getName());
            }
            Integer ind = reader.readNumber();
            Author author = all[ind];
            writer.write("Введите цену");
            int price = reader.readNumber();
            writer.write("Введите описание");
            String description = reader.read();
            Book book = new Book(new BigDecimal(price), author, title, description);
            if (BookValidator.valid(book)) {
                bookService.add(book);
                writer.write("Добавлена книга: " + book.getTitle());
            } else {
                writer.write("Неверные данные");
            }
        }
    }

    public void delete() {
        writer.write("Введите id книги");
        Book[] all = bookService.findAll();
        int id;
        for (id = 0; id < all.length; ++id) {
            writer.write(id + " имя автора: " + all[id].getTitle() + " id автора: " + all[id].getId());
        }
        id = reader.readNumber();
        bookService.delete(id);
        writer.write("Книга была удалена из базы");
    }

    public void findAll() {
        writer.write("В базе следующие книги: ");
        Book[] all = bookService.getAllBooks();
        for (int i = 0; i < all.length; ++i) {
            Book allBook = all[i];
            if (allBook == null) {
                writer.write("Книг в базе нет ");
                break;
            }
            writer.write("Книга " + allBook.getTitle());
        }
    }

    public void findById() {
        writer.write("Введите id книги");
        int id = reader.readNumber();
        Book book = bookService.findById(id);
        addInBasket(book);
        writer.write("Книга " + book.getTitle() + " найдена");
    }

    public void findByTitle() {
        writer.write("Введите название книги");
        String title = reader.read();
        Book book = bookService.findByTitle(title);
        if (book == null) {
            writer.write("Книга не найдена");
        } else {
            addInBasket(book);
            writer.write("Книга " + book.getTitle() + " найдена");
        }
    }

    public void findByAuthorName() {
        writer.write("Введите имя автора, книги которого хотите увидеть ");
        String nameAuthor = reader.read();
        Book[] byAuthorName = bookService.findByAuthorName(nameAuthor);
        if (byAuthorName == null) {
            writer.write("Такого автора не сущетсвует");
        } else if (byAuthorName.length == 0) {
            writer.write("Книг данного автора нет");
        } else {
            writer.write("У данного автора следующие книги: ");
            int i;
            for (i = 0; i < byAuthorName.length; ++i) {
                if (byAuthorName[i] == null) {
                    writer.write("Больше книг у данного автора нет");
                    break;
                }
                writer.write("Книга " + i + " : " + byAuthorName[i].getTitle());
            }
            writer.write("Выберите номер книги");
            i = reader.readNumber();
            addInBasket(byAuthorName[i]);
        }
    }

    private void addInBasket(Book byTitle) {
        writer.write("Книга найдена. Вы выбрали " + byTitle.getTitle());
        writer.write("1 - Выбрать и показать информацию о ней ");
        writer.write("0 - Продолжить поиск");
        switch (reader.readNumber()) {
            case 1:
                writer.write("Название " + byTitle.getTitle());
                writer.write("Автор " + byTitle.getAuthor().getName());
                writer.write("Описание " + byTitle.getDescription());
                writer.write("Цена " + byTitle.getPrice());
                writer.write("1 - Положить в корзину");
                writer.write("0 - Выйти из описания и продолжить поиск");
                switch (reader.readNumber()) {
                    case 0:
                        writer.write("Выход из описания");
                        break;
                    case 1:
                        Book[] books = ConsoleApplicationImpl.session.getBasket().getBooks();
                        for (int i = 0; i < books.length; ++i) {
                            if (books[i] == null) {
                                books[i] = byTitle;
                                writer.write("Книга " + byTitle.getTitle() + " добавлена в корзину");
                                break;
                            }
                            if (books[i].getTitle().equals(byTitle.getTitle())) {
                                writer.write("Книга уже есть в вашей корзине. 1 - Добавить снова 2 - Выйти");
                                switch (this.reader.readNumber()) {
                                    case 1:
                                        for (int ii = 0; ii < books.length; ++ii) {
                                            if (books[ii] == null) {
                                                books[ii] = byTitle;
                                                writer.write("Книга " + byTitle.getTitle() + " снова добавлена в корзину");
                                                return;
                                            }
                                        }
                                    case 2:
                                        return;
                                    default:
                                        writer.write("Такого действия нет в списке выбора");
                                }
                            }
                        }

                        return;
                    default:
                        writer.write("Такой операции не существует!");
                }
            case 0:
                writer.write("Продолжение поиска");
                findByAuthorName();
            default:
                writer.write("Такой операции не существует!");
        }
    }
}
