package by.repository.filedb;

import by.entity.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Data implements Serializable {
  private List<User> users = new ArrayList<>();
  private List<Address> addresses = new ArrayList<>();
  private List<Author> authors = new ArrayList<>();
  private List<Book> books = new ArrayList<>();
  private List<City> cities = new ArrayList<>();
  private List<Order> orders = new ArrayList<>();
  private List<Store> stores = new ArrayList<>();

  private int userId;
  private int addressesId;
  private int authorsId;
  private int booksId;
  private int citiesId;
  private int ordersId;
  private int storesId;

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void setAuthors(List<Author> authors) {
    this.authors = authors;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public List<City> getCities() {
    return cities;
  }

  public void setCities(List<City> cities) {
    this.cities = cities;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public List<Store> getStores() {
    return stores;
  }

  public void setStores(List<Store> stores) {
    this.stores = stores;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getAddressesId() {
    return addressesId;
  }

  public void setAddressesId(int addressesId) {
    this.addressesId = addressesId;
  }

  public int getAuthorsId() {
    return authorsId;
  }

  public void setAuthorsId(int authorsId) {
    this.authorsId = authorsId;
  }

  public int getBooksId() {
    return booksId;
  }

  public void setBooksId(int booksId) {
    this.booksId = booksId;
  }

  public int getCitiesId() {
    return citiesId;
  }

  public void setCitiesId(int citiesId) {
    this.citiesId = citiesId;
  }

  public int getOrdersId() {
    return ordersId;
  }

  public void setOrdersId(int ordersId) {
    this.ordersId = ordersId;
  }

  public int getStoresId() {
    return storesId;
  }

  public void setStoresId(int storesId) {
    this.storesId = storesId;
  }
}
