package by.consoleapp.action;

import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.StoreValidator;
import by.entity.Address;
import by.entity.City;
import by.entity.Store;
import by.service.AddressService;
import by.service.CityService;
import by.service.StoreService;

public class StoreActionImpl implements StoreAction {
    private StoreService storeService;
    private Writer writer;
    private Reader reader;
    private AddressService addressService;
    private CityService cityService;

    public StoreActionImpl(StoreService storeService, Writer writer, Reader reader, AddressService addressService, CityService cityService) {
        this.storeService = storeService;
        this.writer = writer;
        this.reader = reader;
        this.addressService = addressService;
        this.cityService = cityService;
    }

    public void add() {
        Address[] all = addressService.findAll();
        if (all.length == 0) {
            writer.write("В базе нет адресов. Создайте и повторите операцию");
        } else {
            for (int i = 0; i < all.length; ++i) {
                if (all[i] == null) {
                    writer.write("Иных нет ");
                    break;
                }
                writer.write("Выберите адрес магазина");
                writer.write(i + " " + all[i].getAddress());
            }
            int in = reader.readNumber();
            Address address = all[in];
            City[] cit = cityService.findAll();
            if (cit.length == 0) {
                writer.write("В базе нет городов. Создайте и повторите операцию");
            } else {
                for (int i = 0; i < cit.length && cit[i] != null; ++i) {
                    writer.write("Выберите город в котором магазин");
                    writer.write(i + " " + cit[i].getName());
                }
                int ineteg = reader.readNumber();
                City city = cit[ineteg];
                writer.write("Введите имя магазина");
                String name = reader.read();
                Store store = new Store(name, address, city);
                if (StoreValidator.valid(store)) {
                    storeService.add(store);
                    writer.write(" Создан новый магазин: " + name);
                } else {
                    writer.write("Неверные параметры ввода названия магазина");
                }
            }
        }
    }

    public void delete() {
        Store[] all = storeService.findAll();
        int i;
        for (i = 0; i < all.length; ++i) {
            writer.write(i + " название магазина: " + all[i].getName() + " id магазина: " + all[i].getId());
        }
        writer.write("Введите id");
        i = reader.readNumber();
        storeService.delete(i);
        writer.write("Магазин удален");
    }

    public void findAll() {
        writer.write("В базе следующие магазины: ");
        Store[] all = storeService.findAll();
        if (all.length == 0) {
            writer.write("Магазинов в базе нет");
        } else {
            Store[] all1 = storeService.findAll();
            for (Store store : all1) {
                if (store == null) {
                    writer.write(" Магазинов нет в базе ");
                    break;
                }
                writer.write("Магазин " + store.getName());
            }
        }
    }

    public void findByName() {
        writer.write("Введите имя магазина");
        String read = reader.read();
        Store byName = storeService.findByName(read);
        if (byName == null) {
            writer.write("Нет такого магазина");
        } else {
            writer.write("Магазин " + byName);
        }
    }

    public void update() {
        writer.write("Введите старое название магазина");
        String oldName = reader.read();
        Store store = storeService.findByName(oldName);
        writer.write("Введите новое название магазина");
        String newName = reader.read();
        storeService.update(newName, store.getId());
        writer.write("Старое название магазина " + oldName + " изменено на " + newName);
    }
}
