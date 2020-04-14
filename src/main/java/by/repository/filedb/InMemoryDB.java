package by.repository.filedb;

import by.entity.*;

import java.util.List;

public class InMemoryDB implements DB {
  private static Data data = new Data();

  @Override
  public <T> void change(List<T> list, int id, Class<T> clazz) {
    switch (clazz.getSimpleName()) {
      case "User":
        data.setUsers((List<User>) list);
        data.setUserId(id);
        break;
      case "Address":
        data.setAddresses((List<Address>) list);
        data.setAddressesId(id);
        break;
      case "Author":
        data.setAuthors((List<Author>) list);
        data.setAuthorsId(id);
        break;
      case "Book":
        data.setBooks((List<Book>) list);
        data.setBooksId(id);
        break;
      case "City":
        data.setCities((List<City>) list);
        data.setCitiesId(id);
        break;
      case "Order":
        data.setOrders((List<Order>) list);
        data.setOrdersId(id);
        break;
      case "Store":
        data.setStores((List<Store>) list);
        data.setStoresId(id);
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
