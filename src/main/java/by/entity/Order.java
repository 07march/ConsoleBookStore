package by.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

public class Order implements Serializable {
   private int id;

  public enum Status {
    ACTIVE,
    PROCESSED,
    CLOSE
  }

  private boolean isDelivery;
  private Store store;
//  private BigDecimal totalPrice;
  private Book[] books;
  private User user;
  private Status status;
  private Address address;
  private BigDecimal totalPrice;

  public Order(boolean isDelivery, BigDecimal totalPrice, Book[] books, User user, Status status, Address address) {
    this.isDelivery = isDelivery;
    this.totalPrice = totalPrice;
    this.books = books;
    this.user = user;
    this.status = status;
    this.address = address;
  }

  public Order(int id, boolean isDelivery, Store store, User user, Status status, Address address, BigDecimal totalPrice) {
    this.id = id;
    this.isDelivery = isDelivery;
    this.store = store;
//    this.totalPrice = totalPrice;
    this.user = user;
    this.status = status;
    this.address = address;
    this.totalPrice = totalPrice;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Order(Store store, BigDecimal totalPrice, Book[] books, User user, Status status) {
    this.store = store;
    this.totalPrice = totalPrice;
    this.books = books;
    this.user = user;
    this.status = status;
  }

  public Order() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isDelivery() {
    return isDelivery;
  }

  public void setDelivery(boolean delivery) {
    isDelivery = delivery;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  public Book[] getBooks() {
    return books;
  }

  public void setBooks(Book[] books) {
    this.books = books;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Order{" +
            "id=" + id +
            ", isDelivery=" + isDelivery +
            ", by.store=" + store +
            ", totalPrice=" + totalPrice +
            ", books=" + Arrays.toString(books) +
            ", user=" + user +
            '}';
  }
}
