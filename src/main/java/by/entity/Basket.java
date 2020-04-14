package by.entity;

import java.util.Arrays;

public class Basket {
    private int id;
  private Book[] books;

  public Basket(Book[] books) {
    this.books = books;
  }

  public Basket() {
  }

  public int getId() {
    return id;
  }

  public Book[] getBooks() {
    return books;
  }

  public void setBooks(Book[] books) {
    this.books = books;
  }

  @Override
  public String toString() {
    return "Basket{" +
            "id=" + id +
            ", books=" + Arrays.toString(books) +
            '}';
  }
}
