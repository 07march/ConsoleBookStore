package by.repository;

import by.entity.Book;
import by.entity.Order;
import by.entity.Store;
import by.entity.User;

public interface OrderRepository {
  void add(Order order);
  void delete(int id);
  boolean update(Book[] books, int id);
  Order findByStore(Store store);
  Order[] findAll();
  Order findByUser(User user);
  Order[] findAllByStore(Store store);
}
