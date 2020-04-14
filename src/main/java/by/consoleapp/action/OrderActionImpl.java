package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.entity.*;
import by.service.BookService;
import by.service.OrderService;
import by.service.StoreService;
import by.service.UserService;

import java.math.BigDecimal;
import java.util.Arrays;

public class OrderActionImpl implements OrderAction {
    private Writer writer;
    private Reader reader;
    private OrderService orderService;
    private StoreService storeService;
    private BookService bookService;
    private UserService userService;

    public OrderActionImpl(Writer writer, Reader reader, OrderService orderService, StoreService storeService, BookService bookService, UserService userService) {
        this.writer = writer;
        this.reader = reader;
        this.orderService = orderService;
        this.storeService = storeService;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void createOrder() {
        writer.write("Выберите тип покупки: 1 - Доставка 2 - Самовывоз 0 - Выход");
        switch (reader.readNumber()) {
            case 0:
                return;
            case 1:
                writer.write("Введите адрес");
                String address = reader.read();
                Address address1 = new Address(address);
                orderService.add(new Order(true, BigDecimal.valueOf(ConsoleApplicationImpl.getTotalPrice()), ConsoleApplicationImpl.session.getBasket().getBooks(), ConsoleApplicationImpl.session.getUser(), Order.Status.ACTIVE, address1));
                int id = ConsoleApplicationImpl.session.getUser().getId();
                userService.changeStatus(true, id);
                ConsoleApplicationImpl.session.getUser().setOrdered(true);
                writer.write("Ваш заказ: 1) сумма: " + ConsoleApplicationImpl.getTotalPrice() + " 2) книги: " + Arrays.toString(ConsoleApplicationImpl.session.getBasket().getBooks()) + " 3) статус заказа: " + Order.Status.ACTIVE.name());
                ConsoleApplicationImpl.session.setBasket(new Basket(new Book[10]));
                return;
            case 2:
                writer.write("Выберите пунк самовывоза");
                Store[] all = storeService.findAll();
                int number;
                for (number = 0; number < all.length && all[number] != null; ++number) {
                    writer.write(number + " " + all[number].getName());
                }
                number = reader.readNumber();
                Store store = all[number];
                orderService.add(new Order(store, BigDecimal.valueOf(ConsoleApplicationImpl.getTotalPrice()), ConsoleApplicationImpl.session.getBasket().getBooks(), ConsoleApplicationImpl.session.getUser(), Order.Status.ACTIVE));
                int id2 = ConsoleApplicationImpl.session.getUser().getId();
                userService.changeStatus(true, id2);
                ConsoleApplicationImpl.session.getUser().setOrdered(true);
                writer.write("Ваш заказ: 1) сумма: " + ConsoleApplicationImpl.getTotalPrice() + " 2) книги: " + Arrays.toString(ConsoleApplicationImpl.session.getBasket().getBooks()) + " 3) статус заказа: " + Order.Status.ACTIVE.name());
                ConsoleApplicationImpl.session.setBasket(new Basket(new Book[10]));
                return;
            default:
                writer.write("Такой операции нет");
        }
    }

    public void deleteOrder() {
        Order[] all = orderService.findAll();
        if (all.length == 0) {
            writer.write("База данных пуста.");
        } else {
            writer.write("Выберите заказ, который хотите удалить");
            int i;
            for (i = 0; i < all.length; ++i) {
                writer.write(i + " Заказ " + all[i].getUser().getEmail() + " " + all[i].getStatus());
            }
            i = reader.readNumber();
            orderService.delete(all[i].getId());
            writer.write("Заказ удален");
        }
    }

    public void showOrderByStore() {
        Store[] all = storeService.findAll();
        if (all.length == 0) {
            writer.write("База данных пуста.");
        } else {
            writer.write("Выберите магазин из списка");
            int i;
            for (i = 0; i < all.length; ++i) {
                writer.write(i + " " + all[i].getName());
                if (all[i] == null) {
                    break;
                }
                writer.write(i + " " + all[i].getName());
            }
            i = reader.readNumber();
            Store store = all[i];
            Order[] allByStore = orderService.findAllByStore(store);
            for (int ii = 0; ii < allByStore.length; ++ii) {
                writer.write(ii + " Заказ " + allByStore[ii].getUser().getEmail() + " " + allByStore[ii].getStatus());
            }
        }
    }

    public void showAll() {
        Order[] all = orderService.findAll();
        if (all.length == 0) {
            writer.write("База данных пуста.");
        } else {
            for (Order order : all) {
                if (order == null) {
                    break;
                }
                writer.write("Заказ номер " + order.getId() + " Пользователь " + order.getUser().getFirstName() + " Магазин " + order.getStore().getName() + " Доставка? " + order.isDelivery());
            }
        }
    }

    public void showAllByStore() {
        Moderator moderator = (Moderator) ConsoleApplicationImpl.session.getUser();
        Order[] allByStore = orderService.findAllByStore(moderator.getStore());
        for (Order order : allByStore) {
            writer.write("Заказ " + order.getId());
            writer.write("Содержание заказа: " + Arrays.toString(order.getBooks()));
            writer.write("Имя: " + order.getUser().getFirstName());
            writer.write("Фамилия: " + order.getUser().getLastName());
            writer.write("Email " + order.getUser().getEmail());
            writer.write("Сумма заказа: " + order.getTotalPrice().doubleValue());
            writer.write("--------------------------------------------");
        }
    }

    public void showPersonalOrder() {
        User user = ConsoleApplicationImpl.session.getUser();
        Order byUser = orderService.findByUser(user);
        if (byUser == null) {
            writer.write("У вас нет активных заказов");
        } else {
            writer.write("Заказ " + byUser.getId());
            writer.write("Содержание заказа: " + Arrays.toString(byUser.getBooks()));
            writer.write("Ваше имя: " + byUser.getUser().getFirstName());
            writer.write("Сумма заказа: " + byUser.getTotalPrice().doubleValue());
            writer.write("Статус заказа : " + byUser.getStatus());
            writer.write("Спасибо за заказ!");
        }
    }
}
