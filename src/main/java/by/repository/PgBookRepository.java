package by.repository;

import by.entity.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgBookRepository implements BookRepository {
    Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_BOOK = "insert into book (price, author_id, title, description, order_id) values (?, ?, ?, ?, ?)";
    private static final String FIND_ALL = "select * from book";
    private static final String DELETE_BOOK = "delete from book where id=?";
    private static final String DELETE_TITLE = "delete from book where title=?";
    private static final String UPDATE_TITLE = "update book set title=? where id=?";
    private static final String UPDATE_PRICE = "update book set price=? where id=?";
    private static final String FIND_BY_ID = "select * from book where id=?";
    private static final String FIND_BY_TITLE = "select * from book where title=?";
    private static final String FIND_BY_PRICE = "select * from book where price=?";
    private static final String FIND_BOOK_BY_AUTHOR_NAME = "select * from book where author_id=?";
    private static final String GET_AUTHOR_BY_ID = "select * from author where id=?";
    private static final String FIND_ADDRESS_BY_ID = "select * from address where id=?";
    private static final String FIND_Author_BY_NAME = "select * from author where name=?";
    private static final String FIND_CITY_ID = "select * from city where id=?";
    private static final String FIND_ROLE_ID = "select * from role where id=?";
    private static final String FIND_STORE_ID = "select * from store where id=?";
    private static final String FIND_USER_ID = "select * from \"user\" where  id=?";
    private static final String FIND_ORDER_ID = "select * from \"order\" where id=?";

    {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Book book) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_BOOK);
//            preparedStatement.setInt(1, book.getId());
            preparedStatement.setInt(1, book.getPrice().intValue());
            preparedStatement.setInt(2, book.getAuthor().getId());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setString(4, book.getDescription());
            preparedStatement.setInt(5, 0);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book[] getAllBooks() {
        try {
            List<Book> books = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idBook = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                books.add(new Book(idBook, bookPrice, authorById, bookTitle, bookDescription, orderById));
            }
            return books.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TITLE);
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateTitle(String title, int id) {
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

    @Override
    public boolean updatePrice(BigDecimal price, int id) {
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

    @Override
    public Book findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                return new Book(idAut, bookPrice, authorById, bookTitle, bookDescription, orderById);
            }
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

    private Author getAuthorById(int authorId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID);
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

    private City getCityById(int cityId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CITY_ID);
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

    private Order getOrderById(int orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_ID);
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                boolean isDelivery = resultSet.getBoolean(2);
                int storeId = resultSet.getInt(3);
                Store storeById = getStoreById(storeId);
                int userId = resultSet.getInt(4);
                User userById = getUserById(userId);
                String status = resultSet.getString(5);
                int addressId = resultSet.getInt(6);
                Address addressById = getAddressById(addressId);
                BigDecimal totalPrice = resultSet.getBigDecimal(7);
                return new Order(id, isDelivery, storeById, userById, Order.Status.valueOf(status), addressById, totalPrice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Store getStoreById(int storeId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_STORE_ID);
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

    private User getUserById(int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt(1);
            String fname = resultSet.getString(2);
            String lname = resultSet.getString(3);
            String email = resultSet.getString(4);
            String password = resultSet.getString(5);

            int roleId = resultSet.getInt(6);
            PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ROLE_ID);
            preparedStatement1.setInt(1, roleId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            resultSet1.next();
            int idRole = resultSet1.getInt(1);
            String role = resultSet1.getString(2);

            boolean isOrdered = resultSet.getBoolean(7);
            return new User(id, fname, lname, email, password, Role.valueOf(role), isOrdered);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                return new Book(idAut, bookPrice, authorById, bookTitle, bookDescription, orderById);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findAllByPrice(BigDecimal price) {
        try {
            List<Book> bookList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PRICE);
            preparedStatement.setBigDecimal(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                bookList.add(new Book(idAut, bookPrice, authorById, bookTitle, bookDescription, orderById));
            }
            return bookList.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findByAuthorName(String name) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_Author_BY_NAME);
            preparedStatement1.setString(1, name);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (!resultSet1.next()) {
                return null;
            }
            int authorId = resultSet1.getInt(1);

            List<Book> bookList = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_AUTHOR_NAME);
            preparedStatement.setInt(1, authorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId2 = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId2);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                bookList.add(new Book(idAut, bookPrice, authorById, bookTitle, bookDescription, orderById));
            }
            return bookList.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findAll() {
        try {
            List<Book> books = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAut = resultSet.getInt(1);
                BigDecimal bookPrice = resultSet.getBigDecimal(2);
                int authorId = resultSet.getInt(3);
                Author authorById = getAuthorById(authorId);
                String bookTitle = resultSet.getString(4);
                String bookDescription = resultSet.getString(5);
                int orderId = resultSet.getInt(6);
                Order orderById = getOrderById(orderId);
                books.add(new Book(idAut, bookPrice, authorById, bookTitle, bookDescription, orderById));
            }
            return books.toArray(new Book[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
