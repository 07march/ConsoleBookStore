package by.service;

import by.entity.Book;
import by.repository.BookRepository;

import java.math.BigDecimal;

public class BookServiceImpl implements BookService {
  private BookRepository bookRepository;

  public BookServiceImpl(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  private static BookService bookService;

  private BookServiceImpl() {
    throw new RuntimeException();
  }

  public static BookService getInstance(){
    if (bookService == null){
      bookService = new BookServiceImpl();
    }
    return bookService;
  }

  @Override
  public void add(Book book) {
//    String title = book.getTitle();
//    book.setTitle(title.toUpperCase());
    bookRepository.add(book);
  }

  @Override
  public Book[] getAllBooks() {
    return bookRepository.getAllBooks();
  }

  @Override
  public void delete(int id) {
    bookRepository.delete(id);
  }

  @Override
  public void delete(String title) {
    bookRepository.delete(title);
  }

  @Override
  public boolean updateTitle(String title, int id) {
    return bookRepository.updateTitle(title, id);
  }

  @Override
  public boolean updatePrice(BigDecimal price, int id) {
    return bookRepository.updatePrice(price, id);
  }

  @Override
  public Book findById(int id) {
    return bookRepository.findById(id);
  }

  @Override
  public Book findByTitle(String title) {
    return bookRepository.findByTitle(title);
  }

  @Override
  public Book[] findAllByPrice(BigDecimal price) {
    return bookRepository.findAllByPrice(price);
  }

  @Override
  public Book[] findByAuthorName(String name) {
    return bookRepository.findByAuthorName(name);
  }

  @Override
  public Book[] findAll() {
    return bookRepository.findAll();
  }
}

