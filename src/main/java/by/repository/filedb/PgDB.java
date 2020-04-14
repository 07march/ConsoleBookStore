package by.repository.filedb;

import by.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgDB implements DBPg {
    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_ADDRESS = "insert into address (id, address) values (?, ?)";
    private static final String ADD_AUTHOR = "insert into author (id, name) values (?, ?)";
    private static final String ADD_BOOK = "insert into book (id, price, author_id, title, description, order_id) values (?, ?, ?, ?, ?, ?)";
    private static final String ADD_CITY = "insert into city (id, name) values (?, ?)";
    private static final String ADD_ORDER = "insert into \"order\" (id, is_delivery, store_id, total_price, user_id, status, address_id) values (?, ?, ?, ?, ?, ?, ?) returning id";
    private static final String ADD_STORE = "insert into store (id, name, address_id, city_id) values (?, ?, ?, ?) returning id";
    private static final String ADD_USER = "insert into \"user\" (id, first_name, last_name, email, password, role_id, is_ordered) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ADDRESS = "delete from address where address=?";
    private static final String DELETE_AUTHOR_BY_ID = "delete from author where id=?";
    private static final String DELETE_BOOK_BY_ID = "delete from book where id=?";
    private static final String DELETE_BOOK_BY_TITLE = "delete from book where title=?";
    private static final String DELETE_CITY_BY_NAME = "delete from city where name=?";
    private static final String DELETE_CITY_BY_ID = "delete from city where id=?";
    private static final String DELETE_ORDER_BY_ID = "delete from \"order\" where id=?";
    private static final String DELETE_STORE_BY_ID = "delete from store where id=?";
    private static final String DELETE_USER_BY_EMAIL = "delete from \"user\" where email=?";
    private static final String UPDATE_ADDRESS = "update address set address=? where id=?";
    private static final String UPDATE_AUTHOR = "update author set name=? where id=?";
    private static final String UPDATE_BOOK_SET_ORDER = "update book set order_id=? where id=?";
    private static final String UPDATE_TITLE = "update book set title=? where id=?";
    private static final String UPDATE_PRICE = "update book set price=? where id=?";
    private static final String UPDATE_CITY_BY_ID = "update city set name=? where id=?";
    private static final String UPDATE_BOOKS = "update book SET title=?, price=?, description=? where order_id=?";
    private static final String UPDATE_STORE_BY_ID = "update store set name=? where id=?";
    private static final String UPDATE_STATUS = "update \"user\" set is_ordered=? where id=?";
    private static final String UPDATE_LAST_NAME = "update \"user\" set last_name=? where email=?";
    private static final String UPDATE_PASSWORD = "update \"user\" set password=? where email=?";
    private static final String UPDATE_USER_FIRST_NAME = "update \"user\" set first_name=? where email=?";
    private static final String FIND_ALL_ADRESSES = "select * from address";
    private static final String FIND_ALL_FROM_ADDRESS = "select * from address where address=?";
    private static final String FIND_ADDRESS_BY_ID = "select * from address where id=?";
    private static final String FIND_ALL_AUTHORS = "select * from author";
    private static final String FIND_BY_NAME_AUTHOR = "select * from author where author=?";
    private static final String FIND_BY_ID_AUTHOR = "select * from author where id=?";
    private static final String FIND_ALL_BOOKS = "select * from book";
    private static final String FIND_BOOK_BY_TITLE = "select * from book where title=?";
    private static final String FIND_ALL_CITIES = "select * from city";
    private static final String FIND_BY_NAME_CITY = "select * from city where name=?";
    private static final String FIND_BY_ID_CITY = "select * from city where id=?";
    private static final String FIND_BY_ORDERS = "select * from \"order\" o join store s on store_id=s.id join \"user\" u on user_id=u.id join address a on o.address_id = a.id";
    private static final String FIND_ORDER_BY_ID = "select * from \"order\" where user_id=?";
    private static final String FIND_ALL_STORES = "select * from store join address a on store.address_id = a.id join city c on store.city_id = c.id";
    private static final String FIND_BY_NAME_STORE = "select * from store where name=?";
    private static final String FIND_BY_ID_STORE = "select * from store where id=?";
    private static final String FIND_ALL_FROM_ROLE = "select * from role where role=?";
    private static final String FIND_ROLE_BY_ID = "select * from role where id=?";
    private static final String FIND_ALL_FROM_USER = "select * from \"user\"";
    private static final String FIND_USER_BY_ID = "select * from \"user\" where  id=?";
    private static final String FIND_USER_BY_EMAIL = "select * from \"user\" where email=?";

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void add(T t, Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "User":
                addUser((User) t);
                break;
            case "Address":
                addAddress((Address) t);
                break;
            case "Author":
                addAuthor((Author) t);
                break;
            case "Book":
                addBook((Book) t);
                break;
            case "City":
                addCity((City) t);
                break;
            case "Order":
                addOrder((Order) t);
                break;
            case "Store":
                addStore((Store) t);
                break;
        }
    }

    private void addUser(User user) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FROM_ROLE);
            preparedStatement.setString(1, user.getRole().name());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int roleId = resultSet.getInt(1);

            PreparedStatement preparedStatement2 = connection.prepareStatement(ADD_USER);
            preparedStatement2.setInt(1, user.getId());
            preparedStatement2.setString(2, user.getFirstName());
            preparedStatement2.setString(3, user.getLastName());
            preparedStatement2.setString(4, user.getEmail());
            preparedStatement2.setString(5, user.getPassword());
            preparedStatement2.setInt(6, roleId);
            preparedStatement2.setBoolean(7, user.isOrdered());
            preparedStatement2.execute();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void addAddress(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ADDRESS);
            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(2, address.getAddress());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAuthor(Author author) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR);
            preparedStatement.setInt(1, author.getId());
            preparedStatement.setString(2, author.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBook(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK);
            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(2, book.getPrice().intValue());
            preparedStatement.setInt(3, book.getAuthor().getId());
            preparedStatement.setString(4, book.getTitle());
            preparedStatement.setString(5, book.getDescription());
            preparedStatement.setInt(6, book.getOrder().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCity(City city) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CITY);
            preparedStatement.setInt(1, city.getId());
            preparedStatement.setString(2, city.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addOrder(Order order) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setBoolean(2, order.isDelivery());
            if (order.getStore() == null) {
                preparedStatement.setInt(3, 1);
            } else {
                preparedStatement.setInt(3, order.getStore().getId());
            }
            preparedStatement.setInt(4, order.getTotalPrice().intValue());
            preparedStatement.setInt(5, order.getUser().getId());
            preparedStatement.setString(6, order.getStatus().name());
            if (order.getAddress() == null) {
                preparedStatement.setInt(7, 0);
            } else {
                preparedStatement.setInt(7, order.getAddress().getId());
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int anInt = resultSet.getInt(1);

            for (Book book : order.getBooks()) {
                if (book == null) break;
                PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_BOOK_SET_ORDER);
                preparedStatement1.setInt(1, anInt);
                preparedStatement1.setInt(2, book.getId());
                preparedStatement1.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    private void addStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_STORE);
            preparedStatement.setInt(1, store.getId());
            preparedStatement.setString(2, store.getName());
            preparedStatement.setInt(3, store.getAddress().getId());
            preparedStatement.setInt(4, store.getCity().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T, TT> void delete(TT tt, Class<TT> ttClass, Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "User":
                deleteUser((String) ttClass.cast(tt));
                break;
            case "Address":
                deleteAddress((String) ttClass.cast(tt));
                break;
            case "Author":
                deleteAuthor((Integer) ttClass.cast(tt));
                break;
            case "Book":
                if (ttClass.equals(Integer.class)) {
                    deleteBook((Integer) ttClass.cast(tt));
                } else {
                    deleteBook((String) ttClass.cast(tt));
                }
                break;
            case "City":
                if (ttClass.equals(Integer.class)) {
                    deleteCity((Integer) ttClass.cast(tt));
                } else {
                    deleteCity((String) ttClass.cast(tt));
                }
                break;
            case "Order":
                deleteOrder((Integer) ttClass.cast(tt));
                break;
            case "Store":
                deleteStore((Integer) ttClass.cast(tt));
                break;
        }
    }

    private void deleteUser(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAddress(String address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS);
            preparedStatement.setString(1, address);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteAuthor(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCity(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CITY_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCity(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CITY_BY_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteOrder(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStore(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T, TT> void change(Object identification, String field, TT tt, Class<TT> ttClass, Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "User":
                switch (field) {
                    case "firstName":
                        updateUserFirstName((String) tt, (String) identification);
                        break;
                    case "lastName":
                        updateLastName((String) tt, (String) identification);
                        break;
                    case "password":
                        updatePassword((String) tt, (String) identification);
                        break;
                    case "status":
                        if ((boolean) tt) {
                            updateStatus(true, (Integer) identification);
                        } else {
                            updateStatus(false, (Integer) identification);
                        }
                        break;
                }
            case "Address":
                updateAddressById((String) tt, (Integer) identification);
            case "Author":
                updateAuthorById((String) tt, (Integer) identification);
            case "Book":
                switch (field) {
                    case "Title":
                        updateTitle((String) tt, (Integer) identification);
                        break;
                    case "Price":
                        updatePrice((BigDecimal) tt, (Integer) identification);
                        break;
                }
            case "City":
                updateCity((String) tt, (Integer) identification);
            case "Order":
                updateBooks((Book[]) tt, (Integer) identification);
            case "Store":
                updateStore((String) tt, (Integer) identification);
        }
    }

    private void updateUserFirstName(String newFirstName, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_FIRST_NAME);
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, email);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateLastName(String newLastName, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LAST_NAME);
            preparedStatement.setString(1, newLastName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePassword(String newPassword, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(boolean newStatus, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS);
            preparedStatement.setBoolean(1, newStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAddressById(String newAddress, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS);
            preparedStatement.setString(1, newAddress);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAuthorById(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean updateTitle(String title, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_TITLE);
            preparedStatement.setString(1, title);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean updatePrice(BigDecimal price, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRICE);
            preparedStatement.setBigDecimal(1, price);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateCity(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY_BY_ID);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBooks(Book[] books, int id) {
        for (Book book : books) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOKS);
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getPrice().intValue());
                preparedStatement.setString(3, book.getDescription());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateStore(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STORE_BY_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "User":
                return (List<T>) findAllUsers();
            case "Address":
                return (List<T>) findAllAdresses();
            case "Author":
                return (List<T>) findAllAuthors();
            case "Book":
                return (List<T>) findAllBooks();
            case "City":
                return (List<T>) findAllCities();
            case "Order":
                return (List<T>) findByOrders();
            case "Store":
                return (List<T>) findByStores();
        }
        return null;
    }

    private List<User> findAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FROM_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idUser = resultSet.getInt(1);
                String fname = resultSet.getString(2);
                String lname = resultSet.getString(3);
                String userEmail = resultSet.getString(4);
                String password = resultSet.getString(5);
                int userRoleId = resultSet.getInt(6);
                PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ROLE_BY_ID);
                preparedStatement1.setInt(1, userRoleId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                resultSet1.next();
                String role = resultSet1.getString(2);
                userList.add(new User(idUser, fname, lname, userEmail, password, Role.valueOf(role)));
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Address> findAllAdresses() {
        try {
            List<Address> addresses = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ADRESSES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                String string = resultSet.getString(2);
                addresses.add(new Address(anInt, string));
            }
            return addresses;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Author> findAllAuthors() {
        try {
            List<Author> authors = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_AUTHORS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAuthor = resultSet.getInt(1);
                String nameAuthor = resultSet.getString(2);
                authors.add(new Author(idAuthor, nameAuthor));
            }
            return authors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Book> findAllBooks() {
        try {
            List<Book> books = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BOOKS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                books.add(new Book(idAut, bookPrice, authorById, bookTitle, bookDescription));
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Author getAuthorById(int authorId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_AUTHOR);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int autId = resultSet.getInt(1);
                String autName = resultSet.getString(2);
                return new Author(autId, autName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Store getStoreById(int storeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_STORE);
            preparedStatement.setInt(1, storeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int addId = resultSet.getInt(3);
            Address adr = getAddressById(addId);
            int citId = resultSet.getInt(4);
            City cy = getCityById(citId);
            return new Store(id, name, adr, cy);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private City getCityById(int cityId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_CITY);
            preparedStatement.setInt(1, cityId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int citId = resultSet.getInt(1);
            String citName = resultSet.getString(2);
            return new City(citId, citName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Address getAddressById(int addressId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ADDRESS_BY_ID);
            preparedStatement.setInt(1, addressId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int addId = resultSet.getInt(1);
            String addName = resultSet.getString(2);
            return new Address(addId, addName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUserById(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);
            int roleId = resultSet.getInt(6);

            PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ROLE_BY_ID);
            preparedStatement1.setInt(1, roleId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            String role = resultSet1.getString(2);
            boolean isOrdered = resultSet.getBoolean(7);
            return new User(id, fname, lname, email, password, Role.valueOf(role));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<City> findAllCities() {
        try {
            List<City> cities = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CITIES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int cityId = resultSet.getInt(1);
                String cityName = resultSet.getString(2);
                cities.add(new City(cityId, cityName));
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Order> findByOrders() {
        try {
            List<Order> orders = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                int storeId = resultSet.getInt(3);
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal(4);
                int usId = resultSet.getInt(5);
                User userById = getUserById(usId);
                String status = resultSet.getString(6);
                int addressId = resultSet.getInt(7);
                Address addressById = getAddressById(addressId);
                orders.add(new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice));
            }
            return orders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Store> findByStores() {
        try {
            List<Store> stores = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_STORES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idStore = resultSet.getInt(1);
                String nameStore = resultSet.getString(2);
                int addressId = resultSet.getInt(3);
                Address adr = getAddressById(addressId);
                int cityId = resultSet.getInt(4);
                City cy = getCityById(cityId);
                stores.add(new Store(idStore, nameStore, adr, cy));
            }
            return stores;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T findBy(T t, Class<T> tClass) {
        switch (tClass.getSimpleName()) {
            case "User":
                return (T) findUserByEmail((String) tClass.cast(t));
            case "Address":
                return (T) findAddressByName((String) tClass.cast(t));
            case "Author":
                return (T) findAuthorByName((String) tClass.cast(t));
            case "Book":
                return (T) findBookByTitle((String) tClass.cast(t));
            case "City":
                return (T) findByCityName((String) tClass.cast(t));
            case "Order":
                return (T) findOrderById((Integer) tClass.cast(t));
            case "Store":
                return (T) findStoreByName((String) tClass.cast(t));
        }
        return null;
    }

    private User findUserByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idUser = resultSet.getInt(1);
                String fname = resultSet.getString(2);
                String lname = resultSet.getString(3);
                String userEmail = resultSet.getString(4);
                String password = resultSet.getString(5);
                int userRoleId = resultSet.getInt(6);

                PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ROLE_BY_ID);
                preparedStatement1.setInt(1, userRoleId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                resultSet1.next();
                String role = resultSet1.getString(2);
                return new User(idUser, fname, lname, userEmail, password, Role.valueOf(role));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Address findAddressByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FROM_ADDRESS);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Address(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Author findAuthorByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_AUTHOR);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Author(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Book findBookByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                return new Book(idAut, bookPrice, authorById, bookTitle, bookDescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private City findByCityName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_CITY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new City(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Order findOrderById(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                int storeId = resultSet.getInt(3);
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal(4);
                int usId = resultSet.getInt(5);
                User userById = getUserById(usId);
                String status = resultSet.getString(6);
                int addressId = resultSet.getInt(7);
                Address addressById = getAddressById(addressId);
                return new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Store findStoreByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME_STORE);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int storeId = resultSet.getInt(1);
                String storeName = resultSet.getString(2);
                int addId = resultSet.getInt(3);
                Address address = getAddressById(addId);
                int citId = resultSet.getInt(4);
                City city = getCityById(citId);
                return new Store(storeId, storeName, address, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
