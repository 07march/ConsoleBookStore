package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.AuthorValidator;
import by.entity.Author;
import by.service.AuthorService;

public class AuthorActionImpl implements AuthorAction {
    private Writer writer;
    private Reader reader;
    private AuthorService authorService;

    public AuthorActionImpl(Writer writer, Reader reader, AuthorService authorService) {
        this.writer = writer;
        this.reader = reader;
        this.authorService = authorService;
    }

    public void add() {
        writer.write("Введите автора");
        String name = reader.read();
        Author author = new Author(name);
        if (AuthorValidator.valid(author)) {
            authorService.add(author);
            writer.write("Добавлен автор: " + author.getName());
        } else {
            writer.write("Невернные данные");
        }
    }

    public void delete() {
        Author[] all = authorService.findAll();
        int id;
        for(id = 0; id < all.length; ++id) {
            writer.write(id + " имя автора: " + all[id].getName() + " id автора: " + all[id].getId());
        }
        writer.write("Введите id автора");
        id = reader.readNumber();
        authorService.delete(id);
        writer.write("Автор был удален из базы");
    }

    public void findAll() {
        writer.write("В базе следующие авторы:");
        Author[] all = authorService.findAll();
        for(int i = 0; i < all.length; ++i) {
            if (all[i] == null) {
                writer.write("Авторов в базе нет ");
                break;
            }
            writer.write("Имя: " + all[i].getName());
        }
    }

    public void findById() {
        writer.write("Ввведите id автора");
        int i = reader.readNumber();
        Author byId = authorService.findById(i);
        writer.write(byId.getName());
    }

    public void findByName() {
        writer.write("Ввведите имя автора");
        String name = reader.read();
        Author byName = authorService.findByName(name);
        writer.write(byName.toString());
    }
}
