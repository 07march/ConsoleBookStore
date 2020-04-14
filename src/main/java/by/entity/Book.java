package by.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Book implements Serializable {
  private int id;
  private BigDecimal price;
  private Author author;
  private String title;
  private String description;
  private Order order;

  public Book(BigDecimal price, Author author, String title, String description) {
    this.price = price;
    this.author = author;
    this.title = title;
    this.description = description;
  }

  public Book(int id, BigDecimal price, Author author, String title, String description, Order order) {
    this.id = id;
    this.price = price;
    this.author = author;
    this.title = title;
    this.description = description;
    this.order = order;
  }

  public Book(int id, BigDecimal price, Author author, String title, String description) {
    this.id = id;
    this.price = price;
    this.author = author;
    this.title = title;
    this.description = description;
  }


  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  //x.eq(x) - true
  //x.eq(y) == y.eq(x) - true
  //x == y == z x.eq(y) == z.eq(x) - true
  //x.eq(y) always eq - true
  //not null
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return price.equals(book.price) &&
            Objects.equals(author, book.author) &&
            Objects.equals(title, book.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, author, title);
  }

  @Override
  public String toString() {
    return "Book{" +
            "id=" + id +
            ", price=" + price +
            ", author='" + author + '\'' +
            ", title='" + title + '\'' +
            '}';
  }
}
