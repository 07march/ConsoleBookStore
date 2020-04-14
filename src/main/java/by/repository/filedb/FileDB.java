package by.repository.filedb;

import by.entity.*;

import java.io.*;
import java.util.List;

public class FileDB implements DB {
  private static Data data;
  private File file = new File("data.txt");

  public FileDB() {
    if (file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (file.length() == 0) {
      data = new Data();
    } else {
      try {
        data = (Data) new ObjectInputStream(new FileInputStream(file)).readObject();
      } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  private void writeData() {
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
      objectOutputStream.writeObject(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public <T> void change(List<T> list, int id, Class<T> clazz) {
    switch (clazz.getSimpleName()){
      case "User":
        data.setUsers((List<User>) list);
        data.setUserId(id);
        writeData();
        break;
      case "Address":
        data.setAddresses((List<Address>) list);
        data.setAddressesId(id);
        writeData();
        break;
      case "Author":
        data.setAuthors((List<Author>) list);
        data.setAuthorsId(id);
        writeData();
        break;
      case "Book":
        data.setBooks((List<Book>) list);
        data.setBooksId(id);
        writeData();
        break;
      case "City":
        data.setCities((List<City>) list);
        data.setCitiesId(id);
        writeData();
        break;
      case "Order":
        data.setOrders((List<Order>) list);
        data.setOrdersId(id);
        writeData();
        break;
      case "Store":
        data.setStores((List<Store>) list);
        data.setStoresId(id);
        writeData();
        break;
    }
  }

  @Override
  public <T> List<T> getList(Class<T> clazz) {
    switch (clazz.getSimpleName()){
      case "User":
        return (List<T>) data.getUsers();
      case "Address":
        return (List<T>) data.getAddresses();
      case "Author":
        return (List<T>) data.getAuthors();
      case "Book":
        return (List<T>) data.getBooks();
      case "City":
        return (List<T>) data.getCities();
      case "Order":
        return (List<T>) data.getOrders();
      case "Store":
        return (List<T>) data.getStores();
    }
    return null;
  }

  @Override
  public int getId(Class<?> clazz) {
    switch (clazz.getSimpleName()){
      case "User":
        return data.getUserId();
      case "Address":
        return data.getAddressesId();
      case "Author":
        return data.getAuthorsId();
      case "Book":
        return data.getBooksId();
      case "City":
        return data.getCitiesId();
      case "Order":
        return data.getOrdersId();
      case "Store":
        return data.getStoresId();
    }
    return 0;
  }
}
