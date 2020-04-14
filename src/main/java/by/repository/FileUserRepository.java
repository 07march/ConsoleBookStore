package by.repository;

import by.entity.User;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

public class FileUserRepository implements UserRepository {
    private DB file = new FileDB();
    private List<User> users;

    public FileUserRepository() {
        this.users = file.getList(User.class);
    }

    @Override
    public String toString() {
        return "FileUserRepository{" +
                "file=" + file +
                ", users=" + users +
                '}';
    }

    @Override
    public User findById(int id) {
        for (User user : users) {
            if (user == null) continue;
            if (user.getId() == id) {
                System.out.println("Пользователь найден: " + user.getFirstName());
                return user;
            }
            break;
        }
        System.out.println("Пользователь не найден");
        return null;
    }

    @Override
    public User findByEmail(String email) {
        for (User user : users) {
            if (user == null) continue;
            if (user.getEmail().equals(email)) {
                System.out.println("Пользователь найден: " + user.getFirstName());
                return user;
            }
            break;
        }
        System.out.println("Пользователь не найден");
        return null;
    }

    @Override
    public void add(User user) {
        int id = file.getId(User.class);
        user.setId(++id);
        users.add(user);
        System.out.println("Пользователь " + user.getFirstName() + " добавлен");
        file.change(users, id, User.class);
    }


    @Override
    public void delete(String email) {
        int id = file.getId(User.class);
        for (User user : users) {
            if (user == null) break;
            if (user.getEmail().equals(email)) {
                users.remove(user);
                System.out.println("Пользователь " + user.getEmail() + " удален");
                break;
            }
        }
        file.change(users, id, User.class);
    }

    @Override
    public void updateFirstName(String newFirstName, String email) {
        int id = file.getId(User.class);
        for (User user : users) {
            if (user == null) break;
            if (user.getEmail().equals(email)) {
                user.setFirstName(newFirstName);
                System.out.println("Имя пользователя изменено на " + newFirstName);
            }
            break;
        }
        file.change(users, id, User.class);
    }

    @Override
    public void updateLastName(String newLastName, String email) {
        int id = file.getId(User.class);
        for (User user : users) {
            if (user == null) break;
            if (user.getEmail().equals(email)) {
                user.setLastName(newLastName);
                System.out.println("Фамилия пользователя изменена на " + newLastName);
            }
            break;
        }
        file.change(users, id, User.class);
    }

    @Override
    public void updatePassword(String newPassword, String email) {
        int id = file.getId(User.class);
        for (User user : users) {
            if (user == null) break;
            if (user.getEmail().equals(email)) {
                user.setPassword(newPassword);
                System.out.println("Пароль пользователя изменен");
            }
            break;
        }
        file.change(users, id, User.class);
    }

    @Override
    public void updateStatus(boolean newStatus, int id) {
        for (User user : users) {
            if (user == null) break;
            if (user.getId() == id) {
                user.setOrdered(newStatus);
                System.out.println("Статус пользователя изменен");
            }
            break;
        }
        file.change(users, id, User.class);
    }
}
