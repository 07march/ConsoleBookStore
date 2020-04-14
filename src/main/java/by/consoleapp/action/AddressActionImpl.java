package by.consoleapp.action;

import by.consoleapp.util.ConsoleReaderImpl;
import by.consoleapp.util.Reader;
import by.consoleapp.util.Writer;
import by.consoleapp.validator.AddressValidator;
import by.entity.Address;
import by.service.AddressService;

public class AddressActionImpl implements AddressAction {
    private Writer writer;
    private Reader reader = ConsoleReaderImpl.getInstance();
    private AddressService addressService;

    public AddressActionImpl(Writer writer, AddressService addressService) {
        this.writer = writer;
        this.addressService = addressService;
    }

    public void add() {
        writer.write("Введите адрес");
        String address = reader.read();
        Address address1 = new Address(address);
        if (AddressValidator.valid(address1)) {
            addressService.add(address1);
            writer.write("Адрес: " + address);
        } else {
            writer.write("Неверные параметры ввода адреса");
        }
    }

    public void delete() {
        Address[] all = addressService.findAll();
        for (int i = 0; i < all.length; ++i) {
            writer.write(all[i].getAddress());
        }
        writer.write("Введите адрес для удаления");
        String address = reader.read();
        addressService.delete(address);
        writer.write("Адрес " + address + " удален");
    }

    public void update() {
        writer.write("Введите старое адрес");
        String address = reader.read();
        Address byName = addressService.findByName(address);
        writer.write("Введите новый адрес");
        String newAddress = reader.read();
        addressService.updateAddressById(newAddress, byName.getId());
        writer.write("Новый адрес: " + newAddress);
    }

    public void findAll() {
        writer.write("В базе следующие адреса: ");
        Address[] all = addressService.findAll();
        for (int i = 0; i < all.length; ++i) {
            Address address = all[i];
            if (address == null) {
                writer.write("Адресов в базе данных нет");
                break;
            }
            writer.write("Адрес: " + address.getAddress());
        }
    }

    public void findById() {
        writer.write("Ввведите id");
        int id = reader.readNumber();
        Address byId = addressService.findById(id);
        writer.write(byId.getAddress());
    }
}
