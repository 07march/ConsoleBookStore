package by.repository;

import by.entity.Address;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.util.List;

public class InMemoryAddressRepository implements AddressRepository {
    //  private Address[] addresses = new Address[10];
    private List<Address> addresses;
    private DB inMemoryDB = new InMemoryDB();

    public InMemoryAddressRepository() {
        this.addresses = inMemoryDB.getList(Address.class);
    }

//    public static void main(String[] args) {
//        AddressRepository addressRepository = new InMemoryAddressRepository();
//        addressRepository.add(new Address("das"));
//        addressRepository.add(new Address("xzv"));
//        addressRepository.add(new Address("cvx"));
//        addressRepository.add(new Address("aaa"));
//        addressRepository.findAll();
//        System.out.println(Arrays.toString(addressRepository.findAll()));
//    }

    @Override
    public void add(Address address) {
        int id = inMemoryDB.getId(Address.class);
        address.setId(++id);
        addresses.add(address);
        System.out.println("Адрес " + address.getAddress() + " добавлен");
        inMemoryDB.change(addresses, id, Address.class);
//    for (int i = 0; i < addresses.length; i++) {
//      if (addresses[i] == null) {
//        addresses[i] = address;
//        return;
//      }
//    }
    }

    @Override
    public void delete(String address) {
        int id = inMemoryDB.getId(Address.class);
        for (Address address1 : addresses) {
            if (address1 == null) break;
            if (address1.getAddress().equals(address)) {
                addresses.remove(address1);
                System.out.println("Адрес " + address1.getAddress() + " удален");
                break;
            }
        }
        inMemoryDB.change(addresses, id, Address.class);
    }

    @Override
    public boolean updateAddressById(String address, int id) {
        for (Address address1 : addresses) {
            if (address1 == null) continue;
            if (address1.getId() == id) {
                address1.setAddress(address);
                System.out.println("Адрес изменен на: " + address);
                inMemoryDB.change(addresses, id, Address.class);
                return true;
            }
        }
        return false;
    }

    @Override
    public Address[] findAll() {
        return addresses.toArray(new Address[0]);
//    int count = 0;
//    for (int i = 0; i < addresses.length; i++) {
//      if (addresses[i] == null) {
//        count = i;
//        break;
//      }
//    }
//    Address[] addresses1 = new Address[count];
//    System.arraycopy(addresses, 0, addresses1, 0, addresses1.length);
//    return addresses1;
    }

    @Override
    public Address findById(int id) {
        for (Address address : addresses) {
            if (address == null) continue;
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
            if (address == null) continue;
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
