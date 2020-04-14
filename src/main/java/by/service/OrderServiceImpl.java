package by.service;

import by.entity.Book;
import by.entity.Order;
import by.entity.Store;
import by.entity.User;
import by.repository.OrderRepository;

public class OrderServiceImpl implements OrderService {
  private OrderRepository orderRepository;

  public OrderServiceImpl(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @Override
  public void add(Order order) {
    orderRepository.add(order);
  }

  @Override
  public void delete(int id) {
orderRepository.delete(id);
  }

  @Override
  public boolean update(Book[] books, int id) {
    return orderRepository.update(books, id);
  }

  @Override
  public Order findByStore(Store store) {
    return orderRepository.findByStore(store);
  }

  @Override
  public Order[] findAll() {
    return orderRepository.findAll();
  }

  @Override
  public Order findByUser(User user) {
    return orderRepository.findByUser(user);
  }

  @Override
  public Order[] findAllByStore(Store store) {
    return orderRepository.findAllByStore(store);
  }
}
