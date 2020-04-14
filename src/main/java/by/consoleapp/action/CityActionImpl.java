package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.CityValidator;
import by.entity.City;
import by.service.CityService;

public class CityActionImpl implements CityAction {
    private Writer writer;
    private Reader reader;
    private CityService cityService;

    public CityActionImpl(Writer writer, Reader reader, CityService cityService) {
        this.writer = writer;
        this.reader = reader;
        this.cityService = cityService;
    }

    public void add() {
        writer.write("Введите название города");
        String name = reader.read();
        City city = new City(name);
        if (CityValidator.valid(city)) {
            cityService.add(city);
            writer.write("Добавлен город: " + city.getName());
        } else {
            writer.write("Неверные параметры ввода названия города");
        }
    }

    public void delete() {
        City[] all = cityService.findAll();
        for (City city : all) {
            writer.write(city.getName());
        }
        writer.write("Введите название города");
        String name = reader.read();
        cityService.delete(name);
        writer.write("Город " + name + " удален");
    }

    public void update() {
        writer.write("Введите название города, который нужно переименовать");
        String name = reader.read();
        City byName = cityService.findByName(name);
        writer.write("Введите новое название города");
        String newName = reader.read();
        cityService.update(newName, byName.getId());
        writer.write("Город  " + name + " переименован в: " + newName);
    }

    public void findAll() {
        writer.write("В базе следующие города: ");
        City[] all = cityService.findAll();
        for (City city : all) {
            if (city == null) {
                writer.write("Городов нет");
                break;
            }
            writer.write("Город: " + city.getName());
        }
    }

    public void findByName() {
        writer.write("Введите название");
        String name = reader.read();
        City byName = cityService.findByName(name);
        writer.write("Город: " + byName.getName());
    }
}
