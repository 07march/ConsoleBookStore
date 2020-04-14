package by.repository;

import by.entity.Book;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.math.BigDecimal;
import java.util.List;

public class InMemoryBookRepository implements BookRepository {
    private List<Book> books;
    private DB inMemoryDB = new InMemoryDB();

    public InMemoryBookRepository() {
        this.books = inMemoryDB.getList(Book.class);
    }


    @Override
    public void add(Book book) {
        int id = inMemoryDB.getId(Book.class);
        book.setId(++id);
        books.add(book);
        System.out.println("Книга " + book.getTitle() + " добавлена");
        inMemoryDB.change(books, id, Book.class);
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
        inMemoryDB.change(books, id, Book.class);
    }

    @Override
    public void delete(String title) {
        int id = inMemoryDB.getId(Book.class);
        for (Book book : books) {
            if (book == null) break;
            if (book.getTitle().equals(title)) {
                books.remove(book);
                System.out.println("Книга " + book.getTitle() + " удалена");
                break;
            }
        }
        inMemoryDB.change(books, id, Book.class);
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
        inMemoryDB.change(books, id, Book.class);
        System.out.println("Данной книги нет в базе");
        return false;
    }

    @Override
    public boolean updatePrice(BigDecimal price, int id) {
        for (Book book : books) {
            if (book == null) break;
            if (book.getId() == id) {
                book.setPrice(price);
                System.out.println("Цена книги изменена на: " + price);
                return true;
            }
            break;
        }
        inMemoryDB.change(books, id, Book.class);
        System.out.println("Данной книги нет в базе");
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
        Book[] arr = new Book[10];
        int i = 0;
        for (Book book : books) {
            if (book == null) continue;
            if (book.getPrice().equals(price)) {
                arr[i] = book;
                i++;
            }
        }
        return arr;
    }

    @Override
    public Book[] findByAuthorName(String name) {
        int ii = 0;
        Book[] mif = new Book[10];
        for (Book book : books) {
            if (book == null) break;
            if (book.getAuthor().getName().equals(name)) {
                mif[ii] = book;
                ii++;
            }
        }
        return mif;
    }

    @Override
    public Book[] findAll() {
        return books.toArray(new Book[0]);
    }
}




