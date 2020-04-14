package by.repository;

import by.entity.*;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

public class FileOrderRepository implements OrderRepository {
    private DB file = new FileDB();
    private List<Order> orders;

    public FileOrderRepository() {
        this.orders = file.getList(Order.class);
    }

//    public static void main(String[] args) {
//        OrderRepository orderRepository = new FileOrderRepository();
//        orderRepository.add(new Order(true, new BigDecimal(2.22), new Book[10], new User(), null, new Address("mal")));
//        orderRepository.add(new Order(true, new BigDecimal(2.22), new Book[10], new User(), null, new Address("mayak")));
//        System.out.println(Arrays.toString(orderRepository.findAll()));
//    }

    @Override
    public void add(Order order) {
        int lastOrderId = file.getId(Order.class);
        order.setId(++lastOrderId);
        orders.add(order);
        System.out.println("Заказ " + order.getUser() + " добавлен");
        file.change(orders, lastOrderId, Order.class);
    }

    @Override
    public void delete(int id) {
        for (Order order : orders) {
            if (order == null) break;
            if (order.getId() == id) {
                orders.remove(order);
                System.out.println("Заказ " + order.getId() + " удален");
                break;
            }
        }
        file.change(orders, id, Order.class);
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
        file.change(orders, id, Order.class);
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
        System.out.println("Заказ не найден");
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
        Order[] newMassive = new Order[10];
        int i = 0;
        for (Order order : orders) {
            if (store == null) continue;
            if (order.getStore().equals(store)) {
                newMassive[i] = order;
                i++;
            }
        }
        return newMassive;
    }
}
