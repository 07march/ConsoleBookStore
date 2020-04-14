package by.repository;

import by.entity.Address;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

//  25.12.2019 Все репозитории перевести на работу с файлом
public class FileAddressRepository implements AddressRepository {
  private List<Address> addresses;
  private DB file = new FileDB();

  public FileAddressRepository() {
    this.addresses = file.getList(Address.class);
  }

  @Override
  public void add(Address address) {
    int lastAddressId = file.getId(Address.class);
    address.setId(++lastAddressId);
    addresses.add(address);
    System.out.println("Адрес " + address.getAddress() + " добавлен");
    file.change(addresses, lastAddressId, Address.class);
  }

  @Override
  public void delete(String address) {
    int lastAddressId = file.getId(Address.class);
    for (Address address1 : addresses) {
      if (address == null) break;
      if (address1.getAddress().equals(address)) {
        addresses.remove(address1);
        System.out.println("Адрес " + address1.getAddress() + " удален");
        break;
      }
    }
    file.change(addresses, lastAddressId, Address.class);
  }

  @Override
  public boolean updateAddressById(String newAddress, int id) {
    for (Address address : addresses) {
      if (address == null) break;
      if (address.getId() == id){
        address.setAddress(newAddress);
        System.out.println("Адрес изменен на: " + newAddress);
        return true;
      }
    }
    file.change(addresses, id, Address.class);
        return false;
  }

  @Override
  public Address[] findAll() {
    return addresses.toArray(new Address[0]);
  }

  @Override
  public Address findById(int id) {
    for (Address address : addresses) {
      if (address == null) break;
      if (address.getId() == id) {
        System.out.println("Адрес найден: " + address.getAddress());
        return address;
      }
      break;
    }
    System.out.println("Данного адреса нет в базе");
    return null;
  }

  @Override
  public Address findByName(String name) {
    for (Address address : addresses) {
      if (address == null) break;
      if (address.getAddress().equals(name)) {
        System.out.println("Адрес найден: " + address.getAddress());
        return address;
      }
      break;
    }
    System.out.println("Данного адреса нет в базе");
    return null;
  }
}
