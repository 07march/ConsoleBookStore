package by.consoleapp.action;

import by.consoleapp.util.ConsoleReaderImpl;
import by.consoleapp.util.ConsoleWriterImpl;
import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.entity.Book;
import by.entity.Role;
import by.entity.Session;
import by.repository.*;
import by.service.*;

public class ConsoleApplicationImpl implements ConsoleApplication {
    public static Session session;
    private Writer writer = new ConsoleWriterImpl();
    private Reader reader = ConsoleReaderImpl.getInstance();
    private AddressRepository addressRepository = new PgAddressRepository();
    private AuthorRepository authorRepository = new PgAuthorRepository();
    private BookRepository bookRepository = new PgBookRepository();
    private CityRepository cityRepository = new PgCityRepository();
    private OrderRepository orderRepository = new PgOrderRepository();
    private StoreRepository storeRepository = new PgStoreRepository();
    private UserRepository userRepository = new PgUserRepository();
    private AddressService addressService;
    private AuthorService authorService;
    private BookService bookService;
    private CityService cityService;
    private OrderService orderService;
    private StoreService storeService;
    private UserService userService;
    private AddressAction addressAction;
    private AdminAction adminAction;
    private AuthorAction authorAction;
    private BasketAction basketAction;
    private BookAction bookAction;
    private CityAction cityAction;
    private ModeratorAction moderatorAction;
    private OrderAction orderAction;
    private StoreAction storeAction;
    private UserAction userAction;

    public ConsoleApplicationImpl() {
        addressService = new AddressServiceImpl(addressRepository);
        authorService = new AuthorServiceImpl(authorRepository);
        bookService = new BookServiceImpl(bookRepository);
        cityService = new CityServiceImpl(cityRepository);
        orderService = new OrderServiceImpl(orderRepository);
        storeService = new StoreServiceImpl(storeRepository);
        userService = new UserServiceImpl(userRepository);
        addressAction = new AddressActionImpl(writer, addressService);
        adminAction = new AdminActionImpl(reader, writer, userService, storeService);
        authorAction = new AuthorActionImpl(writer, reader, authorService);
        basketAction = new BasketActionImpl(writer, reader);
        bookAction = BookActionImpl.getInstance();
        cityAction = new CityActionImpl(writer, reader, cityService);
        moderatorAction = new ModeratorActionImpl(writer, reader, orderService);
        orderAction = new OrderActionImpl(writer, reader, orderService, storeService, bookService, userService);
        storeAction = new StoreActionImpl(storeService, writer, reader, addressService, cityService);
        userAction = new UserActionImpl(userService, writer, reader);
    }

    public static double getTotalPrice() {
        double price = 0.0;
        Book[] booksPrice = session.getBasket().getBooks();
        for (Book book : booksPrice) {
            if (book == null) {
                break;
            }
            price += book.getPrice().doubleValue();
        }
        return price;
    }

    public void run() {
        while (true) {
            if (session == null) {
                writer.write("Добро пожаловать. Выберите одно из действий: ");
            } else {
                writer.write("Привет " + session.getUser().getRole().name() + " Session ID " + session.getId());
            }
            showMenu();
            if (session == null) {
                switch (reader.readNumber()) {
                    case 0:
                        return;
                    case 1:
                        userAction.add();
                        continue;
                    case 2:
                        userAction.auth();
                        continue;
                    case 3:
                        bookAction.findByTitle();
                        continue;
                    case 4:
                        bookAction.findByAuthorName();
                        continue;
                    default:
                        writer.write("Такой операции не существует!");
                }
            }

            if (session.getUser().getRole().equals(Role.USER)) {
                switch (reader.readNumber()) {
                    case -1:
                        logout();
                        continue;
                    case 0:
                        return;
                    case 1:
                        bookAction.findByTitle();
                        continue;
                    case 2:
                        bookAction.findByAuthorName();
                        continue;
                    case 3:
                        storeAction.findAll();
                        continue;
                    case 4:
                        bookAction.findAll();
                        continue;
                    case 5:
                        accKab();
                        switch (reader.readNumber()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateFirstName();
                                continue;
                            case 2:
                                userAction.updateLastName();
                                continue;
                            case 3:
                                userAction.updatePasswordl();
                                continue;
                            case 4:
                                orderAction.showPersonalOrder();
                                continue;
                            case 5:
                                basketAction.showAllBooksInBasket();
                                basketMenu();
                                switch (reader.readNumber()) {
                                    case 0:
                                        continue;
                                    case 1:
                                        basketAction.deleteBookInBasket();
                                        continue;
                                    case 2:
                                        basketAction.deleteAllInBasket();
                                    default:
                                        writer.write("Такой операции не существует!");
                                        continue;
                                }
                            case 6:
                                orderAction.createOrder();
                                continue;
                            default:
                                writer.write("Такой операции не существует!");
                                continue;
                        }
                    default:
                        writer.write("Такой операции не существует!");
                }
            }

            if (session.getUser().getRole().equals(Role.MODERATOR)) {
                switch (reader.readNumber()) {
                    case 0:
                        continue;
                    case 1:
                        addressAction.add();
                        continue;
                    case 2:
                        cityAction.add();
                        continue;
                    case 3:
                        storeAction.add();
                        continue;
                    case 4:
                        authorAction.add();
                        continue;
                    case 5:
                        bookAction.add();
                        continue;
                    case 6:
                        addressAction.delete();
                        continue;
                    case 7:
                        cityAction.delete();
                        continue;
                    case 8:
                        storeAction.delete();
                        continue;
                    case 9:
                        authorAction.delete();
                        continue;
                    case 10:
                        bookAction.delete();
                        continue;
                    case 11:
                        addressAction.findAll();
                        continue;
                    case 12:
                        cityAction.findAll();
                        continue;
                    case 13:
                        storeAction.findAll();
                        continue;
                    case 14:
                        authorAction.findAll();
                        continue;
                    case 15:
                        bookAction.findAll();
                        continue;
                    case 16:
                        accKab();
                        switch (reader.readNumber()) {
                            case 0:
                                continue;
                            case 1:
                                userAction.updateFirstName();
                                continue;
                            case 2:
                                userAction.updateLastName();
                                continue;
                            case 3:
                                userAction.updatePasswordl();
                                continue;
                            default:
                                writer.write("Такой операции не существует!");
                                continue;
                        }
                    case 17:
                        orderMenu();
                        switch (reader.readNumber()) {
                            case 0:
                                continue;
                            case 1:
                                orderAction.showAllByStore();
                                continue;
                            case 2:
                                moderatorAction.changeOrderStatus();
                                continue;
                            case 3:
                                orderAction.deleteOrder();
                                continue;
                            default:
                                writer.write("Такой операции не существует!");
                        }
                    case -1:
                        logout();
                        continue;
                    default:
                        writer.write("Такой операции не существует!");
                }
            }

            if (session.getUser().getRole().equals(Role.ADMIN)) {
                switch (reader.readNumber()) {
                    case -1:
                        logout();
                        break;
                    case 0:
                        return;
                    case 1:
                        addressAction.add();
                        break;
                    case 2:
                        cityAction.add();
                        break;
                    case 3:
                        storeAction.add();
                        break;
                    case 4:
                        authorAction.add();
                        break;
                    case 5:
                        bookAction.add();
                        break;
                    case 6:
                        addressAction.delete();
                        break;
                    case 7:
                        cityAction.delete();
                        break;
                    case 8:
                        storeAction.delete();
                        break;
                    case 9:
                        authorAction.delete();
                        break;
                    case 10:
                        bookAction.delete();
                        break;
                    case 11:
                        addressAction.findAll();
                        break;
                    case 12:
                        cityAction.findAll();
                        break;
                    case 13:
                        storeAction.findAll();
                        break;
                    case 14:
                        authorAction.findAll();
                        break;
                    case 15:
                        bookAction.findAll();
                        break;
                    case 16:
                        adminAction.addModerator();
                        break;
                    case 17:
                        adminAction.deleteModerator();
                        break;
                    case 18:
                        adminAction.setRoleModerator();
                        break;
                    case 19:
                        accKab();
                        switch (reader.readNumber()) {
                            case 0:
                                break;
                            case 1:
                                userAction.updateFirstName();
                                continue;
                            case 2:
                                userAction.updateLastName();
                                continue;
                            case 3:
                                userAction.updatePasswordl();
                                continue;
                            default:
                                writer.write("Такой операции не существует!");
                        }
                    case 20:
                        orderAction.showAll();
                        break;
                    case 21:
                        orderAction.showOrderByStore();
                        break;
                    case 22:
                        orderAction.deleteOrder();
                        break;
                    default:
                        writer.write("Такой операции не существует!");
                }
            }
        }
    }

    private void showMenu() {
        if (session == null) {
            writer.write("1 - Регистрация");
            writer.write("2 - Авторизация");
            writer.write("3 - Поиск книг по названию");
            writer.write("4 - Поиск книг по автору");
            writer.write("0 - Выход");
        } else if (session.getUser().getRole().equals(Role.USER)) {
            writer.write("1 - Поиск книг по названию");
            writer.write("2 - Поиск книг по автору");
            writer.write("3 - Вывести все магазины");
            writer.write("4 - Вывести все книги");
            writer.write("5 - Перейти в личный кабинет");
            writer.write("0 - Выход");
            writer.write("-1 - Выход из сессии");
        } else if (session.getUser().getRole().equals(Role.MODERATOR)) {
            writer.write("1 - Добавить адрес");
            writer.write("2 - Добавить город");
            writer.write("3 - Добавить магазин");
            writer.write("4 - Добавить автора");
            writer.write("5 - Добавить книгу");
            writer.write("6 - Удалить адрес");
            writer.write("7 - Удалить город");
            writer.write("8 - Удалить магазин");
            writer.write("9 - Удалить автора");
            writer.write("10 - Удалить книгу");
            writer.write("11 - Вывести все адреса");
            writer.write("12 - Вывести все города");
            writer.write("13 - Вывести все магазины");
            writer.write("14 - Вывести всех авторов");
            writer.write("15 - Вывести все книги");
            writer.write("16 - Перейти в личный кабинет");
            writer.write("17 - Перейти в меню заказов");
            writer.write("0 - Выход");
            writer.write("-1 - Выход из сессии");
        } else if (session.getUser().getRole().equals(Role.ADMIN)) {
            writer.write("1 - Добавить адрес");
            writer.write("2 - Добавить город");
            writer.write("3 - Добавить магазин");
            writer.write("4 - Добавить автора");
            writer.write("5 - Добавить книгу");
            writer.write("6 - Удалить адрес");
            writer.write("7 - Удалить город");
            writer.write("8 - Удалить магазин");
            writer.write("9 - Удалить автора");
            writer.write("10 - Удалить книгу");
            writer.write("11 - Вывести все адреса");
            writer.write("12 - Вывести все города");
            writer.write("13 - Вывести все магазины");
            writer.write("14 - Вывести всех авторов");
            writer.write("15 - Вывести все книги");
            writer.write("16 - Добавить модератора");
            writer.write("17 - Удалить юзера");
            writer.write("18 - Повысить до модератора");
            writer.write("19 - Перейти в личный кабинет");
            writer.write("20 - Вывести все заказы");
            writer.write("21 - Вывести все заказы определенного магазина");
            writer.write("22 - Удалить заказ");
            writer.write("0 - Выход");
            writer.write("-1 - Выход из сессии");
        }

    }

    private void accKab() {
        writer.write("Ваше имя: " + session.getUser().getFirstName());
        writer.write("Ваша фамилия: " + session.getUser().getLastName());
        writer.write("Ваш email: " + session.getUser().getEmail());
        if (session.getUser().getRole().equals(Role.USER)) {
            writer.write("Всего в корзине " + getRealBookCountToBasket());
        }

        writer.write("1 - Изменить имя");
        writer.write("2 - Изменить фамилию");
        writer.write("3 - Изменить пароль");
        if (session.getUser().isOrdered()) {
            writer.write("4 - Показать мой заказ");
        }

        if (session.getUser().getRole().equals(Role.USER) && !session.getUser().isOrdered()) {
            writer.write("5 - Перейти в корзину");
            writer.write("6 - Оформить заказ");
        }

        writer.write("0 - Выход из кабинета");
    }

    private int getRealBookCountToBasket() {
        int count = 0;
        Book[] books = session.getBasket().getBooks();
        for (Book book : books) {
            if (book == null) {
                break;
            }
            ++count;
        }
        return count;
    }

    private void orderMenu() {
        writer.write("Всего заказов ");
        writer.write("1 - Показать все заказы");
        writer.write("2 - Изменить статус заказа");
        writer.write("3 - Удалить заказ");
    }

    private void basketMenu() {
        writer.write("Стоимость  " + getTotalPrice());
        writer.write("1 - Удалить книгу из корзины");
        writer.write("2 - Очистить корзину полностью");
        writer.write("0 - Выход из корзины");
    }

    private void logout() {
        session = null;
    }
}
