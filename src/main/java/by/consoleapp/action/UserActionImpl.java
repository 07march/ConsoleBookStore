package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.UserValidator;
import by.entity.*;
import by.service.UserService;

public class UserActionImpl implements UserAction {
    private UserService userService;
    private Writer writer;
    private Reader reader;

    public UserActionImpl(UserService userService, Writer writer, Reader reader) {
        this.userService = userService;
        this.writer = writer;
        this.reader = reader;
    }

    public void findById() {
        writer.write("Введите id");
        int id = reader.readNumber();
        writer.write(userService.findById(id).getFirstName());
    }

    public void findByEmail() {
        writer.write("Введите email");
        String email = reader.read();
        writer.write(userService.findByEmail(email).getFirstName());
    }

    public void add() {
        writer.write("Введите имя");
        String firstName = reader.read();
        writer.write("Введите Фамилию");
        String lastName = reader.read();
        writer.write("Введите email");
        String email = reader.read();
        writer.write("Введите пароль");
        String password = reader.read();
        User user = new User(firstName, lastName, email, password);
        if (user.getEmail().equals("admin@a.by")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }
        if (UserValidator.valid(user)) {
            userService.add(user);
            writer.write(" Добавлен новый пользователь: " + user.getFirstName());
        } else {
            writer.write("Невернные данные");
        }
    }

    public void delete() {
        writer.write("Введите email");
        String email = reader.read();
        userService.delete(email);
        writer.write("Пользователь с email " + email + " удален");
    }

    public void auth() {
        writer.write("Введите email");
        String email = reader.read();
        writer.write("Введите пароль");
        String password = reader.read();
        User user = userService.findByEmail(email);
        if (user == null) {
            writer.write("Данный email не найден");
        } else {
            if (user.getPassword().equals(password)) {
                ConsoleApplicationImpl.session = new Session(new Basket(new Book[10]), user);
                writer.write("Вход выполнен");
            } else {
                writer.write("Неверный пароль");
            }
        }
    }

    public void updateFirstName() {
        writer.write("Введите новое имя");
        String name = reader.read();
        String email = ConsoleApplicationImpl.session.getUser().getEmail();
        userService.updateFirstName(name, email);
        writer.write("Новое имя: " + name);
    }

    public void updateLastName() {
        writer.write("Введите новую фамилию");
        String lastName = reader.read();
        String email = ConsoleApplicationImpl.session.getUser().getEmail();
        userService.updateLastName(lastName, email);
        writer.write("Новая фамилия: " + lastName);
    }

    public void updatePassword() {
        writer.write("Введите новый пароль");
        String password = reader.read();
        String email = ConsoleApplicationImpl.session.getUser().getEmail();
        userService.updatePassword(password, email);
        writer.write("Новый пароль: " + password);
    }
}
