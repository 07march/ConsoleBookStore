package by.repository;

import by.entity.Book;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.math.BigDecimal;
import java.util.List;

public class FileBookRepository implements BookRepository {
    private DB file = new FileDB();
    private List<Book> books;


    public FileBookRepository() {
        this.books = file.getList(Book.class);
    }

//    public static void main(String[] args) {
//        BookRepository bookRepository = new FileBookRepository();
//        bookRepository.add(new Book(new BigDecimal(2.22), new Author("bulg"), "Goods", "Great"));
//        System.out.println(Arrays.toString(bookRepository.findAll()));
//    }

    @Override
    public void add(Book book) {
        int lastBookId = file.getId(Book.class);
        book.setId(++lastBookId);
        books.add(book);
        System.out.println("Книга " + book.getTitle() + " добавлена");
        file.change(books, lastBookId, Book.class);
    }

    @Override
    public Book[] getAllBooks() {
        return books.toArray(new Book[0]);
    }

    @Override
    public void delete(int id) {
        for (Book book : books) {
            if (book == null) break;
            if (book.getId() == id) {
                books.remove(book);
                System.out.println("Книга " + book.getId() + " удалена");
                break;
            }
        }
        file.change(books, id, Book.class);
    }

    @Override
    public void delete(String title) {
        int lastBookId = file.getId(Book.class);
        for (Book book : books) {
            if (book == null) break;
            if (book.getTitle().equals(title)) {
                books.remove(book);
                System.out.println("Книга " + book.getTitle() + " удалена");
                break;
            }
        }
        file.change(books, lastBookId, Book.class);
    }

    @Override
    public boolean updateTitle(String title, int id) {
        for (Book book : books) {
            if (book == null) break;
            if (book.getId() == id) {
                book.setTitle(title);
                System.out.println("Книга изменена на " + title);
              return true;
            }
            break;
        }
        file.change(books, id, Book.class);
        return false;
    }

    @Override
    public boolean updatePrice(BigDecimal price, int id) {
        for (Book book : books) {
            if (book == null) break;
            if (book.getId() == id) {
                book.setPrice(price);
                System.out.println("Цена книги изменена на: " + price);
                file.change(books, id, Book.class);
                return true;
            }
            break;
        }
        return false;
    }

    @Override
    public Book findById(int id) {
        for (Book book : books) {
          if (book == null) continue;
            if (book.getId() == id) {
                System.out.println("Книга найдена: " + book.getTitle());
                return book;
            }
            break;
        }
        System.out.println("Данной книги нет в базе");
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        for (Book book : books) {
          if (book == null) continue;
            if (book.getTitle().equals(title)) {
                System.out.println("Книга найдена: " + book.getTitle());
                return book;
            }
            break;
        }
        System.out.println("Данной книги нет в базе");
        return null;
    }

    @Override
    public Book[] findAllByPrice(BigDecimal price) {
        Book[] newMassive = new Book[10];
        int i = 0;
        for (Book book : books) {
            if (book == null) continue;
            if (book.getPrice().equals(price)) {
                newMassive[i] = book;
                i++;
            }
        }
        return newMassive;
    }

    @Override
    public Book[] findByAuthorName(String name) {
        Book[] newMassive = new Book[10];
        int i = 0;
        for (Book book : books) {
            if (book == null) continue;
            if (book.getAuthor().getName().equals(name)) {
                newMassive[i] = book;
                i++;
            }
        }
        return newMassive;
    }

    @Override
    public Book[] findAll() {
        return books.toArray(new Book[0]);
    }
}
