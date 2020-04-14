package by.repository;

import by.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgOrderRepository implements OrderRepository {
    Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_ORDER = "insert into \"order\" (is_delivery, store_id, total_price, user_id, status, address_id) values (?, ?, ?, ?, ?, ?) returning id";
    private static final String UPDATE_BOOK_SET_ORDER_ID = "update book set order_id=? where id=?";
    private static final String DELETE_FROM_ORDER_BY_ID = "delete from \"order\" where id=?";
    private static final String UPDATE_BOOKS = "update book SET title=?, price=?, description=? where order_id=?";
    private static final String FIND_ORDER_BY_STORE_ID = "select * from \"order\" where store_id=?";
    private static final String FIND_ALL_BY_ORDER = "select * from \"order\" o join store s on store_id=s.id join \"user\" u on user_id=u.id join address a on o.address_id = a.id";
    private static final String FIND_STORE_BY_ID = "select * from store where id=?";
    private static final String FIND_CITY_by_ID = "select * from city where id=?";
    private static final String FIND_ADDRESS_BY_ID = "select * from address where id=?";
    private static final String FIND_USER_BY_ID = "select * from \"user\" where  id=?";
    private static final String FIND_ROLE_BY_ID = "select * from role where id=?";
    private static final String FIND_ORDER_BY_USER_ID = "select * from \"order\" where user_id=?";
    private static final String FIND_ALL_BY_STORE = "select * from \"order\" o join store s on store_id=s.id join \"user\" u on user_id=u.id join address a on o.address_id = a.id where store_id=?";
    private static final String INSERT_INTO_ADDRESS_VALUES = "insert into address (address) VALUES (?) RETURNING id";

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        PgOrderRepository pgOrderRepository = new PgOrderRepository();
//        Author author = new Author(2, "Rop");
//        BigDecimal bigDecimal = new BigDecimal(22);
////        Book book = new Book(1, bigDecimal, author, "War", "asdasd");
////        Book book2 = new Book(2, bigDecimal, author, "War", "asdasd");
////        User user = new User(6, "asd", "sada", "sdad", "asdasd");
////        Address address = new Address(1, "Mayakovka");
////        City city = new City(1, "Minsk");
////        Store by.store = new Store(1, "Mall", address, city);
////        pgOrderRepository.add(new Order(by.store, bigDecimal, new Book[]{book, book2}, user, Order.Status.ACTIVE));
////        System.out.println(pgOrderRepository.findByStore(new Store(1, "Mall", new Address(1, "Mayakovka"), new City(1, "Minsk"))));
//        pgOrderRepository.update(new Book[]{new Book(bigDecimal, author, "newTitle1", "newDescr1"), new Book(bigDecimal, author, "newTitle2", "newDescr2")}, 9);
//    }

    @Override
    public void add(Order order) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);
//            preparedStatement.setInt(1, order.getId());
            preparedStatement.setBoolean(1, order.isDelivery());
            if (order.getStore() == null) {
                preparedStatement.setInt(2, 1);
            } else {
                preparedStatement.setInt(2, order.getStore().getId());
            }
            preparedStatement.setInt(3, order.getTotalPrice().intValue());
            preparedStatement.setInt(4, order.getUser().getId());
            preparedStatement.setString(5, order.getStatus().name());
            if (order.getAddress() == null) {
                preparedStatement.setInt(6, 0);
            } else {
                if (order.getAddress().getId() == 0) {
                    Address address = order.getAddress();
                    PreparedStatement preparedStatement1 = connection.prepareStatement(INSERT_INTO_ADDRESS_VALUES);
                    preparedStatement1.setString(1, address.getAddress());
                    ResultSet resultSet = preparedStatement1.executeQuery();
                    resultSet.next();
                    int addId = resultSet.getInt(1);
                    preparedStatement.setInt(6, addId);
                }
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int anInt = resultSet.getInt(1);

            for (Book book : order.getBooks()) {
                if (book == null) break;
                PreparedStatement preparedStatement1 = connection.prepareStatement(UPDATE_BOOK_SET_ORDER_ID);
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

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Book[] books, int id) {
        for (Book book : books) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOKS);
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getPrice().intValue());
                preparedStatement.setString(3, book.getDescription());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Order findByStore(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_STORE_ID);
            preparedStatement.setInt(1, store.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isDelivery = resultSet.getBoolean("is_delivery");
                int storeId = resultSet.getInt("store_id");
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
                int usId = resultSet.getInt("user_id");
                User userById = getUserById(usId);
                String status = resultSet.getString("status");
                int addressId = resultSet.getInt("address_id");
                Address addressById = getAddressById(addressId);
                return new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] findAll() {
        try {
            List<Order> orders = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_ORDER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isDelivery = resultSet.getBoolean("is_delivery");
                int storeId = resultSet.getInt("store_id");
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
                int usId = resultSet.getInt("user_id");
                User userById = getUserById(usId);
                String status = resultSet.getString("status");
                int addressId = resultSet.getInt("address_id");
                Address addressById = getAddressById(addressId);
                orders.add(new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice));
            }
            return orders.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Store getStoreById(int storeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_STORE_BY_ID);
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
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CITY_by_ID);
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

    @Override
    public Order findByUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_USER_ID);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isDelivery = resultSet.getBoolean("is_delivery");
                int storeId = resultSet.getInt("store_id");
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
                int usId = resultSet.getInt("user_id");
                User userById = getUserById(usId);
                String status = resultSet.getString("status");
                int addressId = resultSet.getInt("address_id");
                Address addressById = getAddressById(addressId);
                return new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Order[] findAllByStore(Store store) {
        try {
            List<Order> ordersByStore = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_STORE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                boolean isDelivery = resultSet.getBoolean("is_delivery");
                int storeId = resultSet.getInt("store_id");
                Store storeById = getStoreById(storeId);
                BigDecimal totalPrice = resultSet.getBigDecimal("total_price");
                int usId = resultSet.getInt("user_id");
                User userById = getUserById(usId);
                String status = resultSet.getString("status");
                int addressId = resultSet.getInt("address_id");
                Address addressById = getAddressById(addressId);
                ordersByStore.add(new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice));
            }
            return ordersByStore.toArray(new Order[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Order[0];
    }
}
