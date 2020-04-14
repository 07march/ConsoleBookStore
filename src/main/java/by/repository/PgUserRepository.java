package by.repository;

import by.entity.Role;
import by.entity.User;

import java.sql.*;

public class PgUserRepository implements UserRepository {
    private static final String FIND_USER_BY_ID = "select * from \"user\" where id=?";
    private static final String FIND_USER_BY_EMAIL = "select * from \"user\" where email=?";
    private static final String FIND_FROM_ROLE = "select * from \"role\" where \"role\"=?";
    private static final String FIND_ROLE_BY_ID = "select * from \"role\" where id=?";
    private static final String ADD_USER = "insert into \"user\" (id, first_name, last_name, email, password, role_id, is_ordered) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_USER_BY_EMAIL = "delete from \"user\" where email=?";
    private static final String UPDATE_FIRST_NAME = "update \"user\" set first_name=? where email=?";
    private static final String UPDATE_LAST_NAME = "update \"user\" set last_name=? where email=?";
    private static final String UPDATE_PASSWORD = "update \"user\" set password=? where email=?";
    private static final String UPDATE_STATUS = "update \"user\" set is_ordered=? where id=?";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String fname = resultSet.getString(2);
                String lname = resultSet.getString(3);
                String email = resultSet.getString(4);
                String password = resultSet.getString(5);
                String userRole = resultSet.getString(6);
                boolean orderedUser = resultSet.getBoolean(7);
                return new User(userId, fname, lname, email, password, Role.valueOf(userRole), orderedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
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
                boolean orderedUser = resultSet.getBoolean(7);
                PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_ROLE_BY_ID);
                preparedStatement1.setInt(1, userRoleId);
                ResultSet resultSet1 = preparedStatement1.executeQuery();
                resultSet1.next();
                String role = resultSet1.getString(2);
                return new User(idUser, fname, lname, userEmail, password, Role.valueOf(role), orderedUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(User user) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_FROM_ROLE);
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

    @Override
    public void delete(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_EMAIL);
            preparedStatement.setString(1, email);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFirstName(String newFirstName, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIRST_NAME);
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateLastName(String newLastName, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_LAST_NAME);
            preparedStatement.setString(1, newLastName);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePassword(String newPassword, String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PASSWORD);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(boolean newStatus, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS);
            preparedStatement.setBoolean(1, newStatus);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
