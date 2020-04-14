package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.ModeratorValidator;
import by.entity.Moderator;
import by.entity.Role;
import by.entity.Store;
import by.entity.User;
import by.service.StoreService;
import by.service.UserService;

public class AdminActionImpl implements AdminAction {
    private Reader reader;
    private Writer writer;
    private UserService userService;
    private StoreService storeService;

    public AdminActionImpl(Reader reader, Writer writer, UserService userService, StoreService storeService) {
        this.reader = reader;
        this.writer = writer;
        this.userService = userService;
        this.storeService = storeService;
    }

    public void addModerator() {
        writer.write("Введите имя");
        String firstName = reader.read();
        writer.write("Введите фамилию");
        String lastName = reader.read();
        writer.write("Введите email");
        String email = reader.read();
        writer.write("Введите пароль");
        String password = reader.read();
        writer.write("Выберите магазин, в котором работает медератор");
        Store[] magaz = storeService.findAll();
        Store[] all = storeService.findAll();
        if (all.length == 0) {
            writer.write("Зарегистрированных магазинов нет. Операция отменена.");
        } else {
            int i;
            for(i = 0; i < magaz.length && magaz[i] != null; ++i) {
                writer.write(i + " Название магазина: " + magaz[i].getName());
            }
            writer.write("Выберите номер магазина");
            i = reader.readNumber();
            Store store = magaz[i];
            User user = new Moderator(firstName, lastName, email, password, store);
            user.setRole(Role.MODERATOR);
            if (ModeratorValidator.valid(user)) {
                userService.add(user);
                writer.write("Модератор: " + user.getFirstName());
            } else {
                writer.write("Невернные данные");
            }
        }
    }

    public void deleteModerator() {
        writer.write("Введите email");
        String em = reader.read();
        userService.delete(em);
        writer.write("Пользователь " + em + " удален");
    }

    public void setRoleModerator() {
        writer.write("Введите email");
        String polz = reader.read();
        Moderator use = (Moderator) userService.findByEmail(polz);
        writer.write("Выберите магазин, в котором работает модератор");
        Store[] magazine = storeService.findAll();
        if (magazine.length == 0) {
            writer.write("Зарегистрированных магазинов нет. Операция отменена.");
        } else {
            int num;
            for(num = 0; num < magazine.length; ++num) {
                writer.write(num + " " + magazine[num].getName());
            }
            num = reader.readNumber();
            Store store = magazine[num];
            use.setRole(Role.MODERATOR);
            use.setStore(store);
            writer.write("Пользователь " + use.getFirstName() + " назначен модератором");
        }
    }
}
