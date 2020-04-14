package by.repository;

import by.entity.*;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {
    private List<Order> orders;
    private DB inMemoryDB = new InMemoryDB();

    private InMemoryOrderRepository() {
        this.orders = inMemoryDB.getList(Order.class);
    }

    @Override
    public void add(Order order) {
        int id = inMemoryDB.getId(Order.class);
        order.setId(++id);
        orders.add(order);
        System.out.println("Заказ " + order.getUser() + " добавлен");
        inMemoryDB.change(orders, id, Order.class);
    }

    @Override
    public void delete(int id) {
        for (Order order : orders) {
            if (order == null) break;
            if (order.getId() == id) {
                orders.remove(order);
                System.out.println("Заказ " + order.getId() + " удален");
            }
            break;
        }
        inMemoryDB.change(orders, id, Order.class);
    }

    @Override
    public boolean update(Book[] books, int id) {
        for (Order order : orders) {
            if (order == null) break;
            if (order.getId() == id) {
                order.setBooks(books);
                System.out.println("Заказ " + order.getId() + " изменен");
                return true;
            }
            break;
        }
        inMemoryDB.change(orders, id, Order.class);
        return false;
    }

    @Override
    public Order findByStore(Store store) {
        for (Order order : orders) {
            if (order == null) continue;
            if (order.getStore().equals(store)) {
                System.out.println("Заказ найден: " + order.getUser());
                return order;
            }
            break;
        }
        return null;
    }

    @Override
    public Order[] findAll() {
        return orders.toArray(new Order[0]);
    }

    @Override
    public Order findByUser(User user) {
        for (Order order : orders) {
            if (order == null) continue;
            if (order.getUser().equals(user)) {
                System.out.println("Заказ найден: " + order.getUser());
                return order;
            }
            break;
        }
        System.out.println("Заказ не найден");
        return null;
    }

    @Override
    public Order[] findAllByStore(Store store) {
        Order[] orderByStores = new Order[10];
        int i = 0;
        for (Order order : orderByStores) {
            if (store == null) continue;
            if (order.getStore().equals(store)) {
                orderByStores[i] = order;
                i++;
            }
        }
        return orderByStores;
    }
}
