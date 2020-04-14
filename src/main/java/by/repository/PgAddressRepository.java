package by.repository;

import by.entity.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgAddressRepository implements AddressRepository {
    private static Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_ADDRESS = "insert into address (address) values (?)";
    private static final String DELETE_ADDRESS = "delete from address where address=?";
    private static final String UPDATE_ADDRESS = "update address set address=? where id=?";
    private static final String FIND_ALL_ADDRESSES = "select * from address";
    private static final String FIND_BY_ID = "select * from address where id=?";
    private static final String FIND_BY_NAME = "select * from address where address=?";


    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Address address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_ADDRESS);
//            preparedStatement.setInt(1, address.getId());
            preparedStatement.setString(1, address.getAddress());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String address) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ADDRESS);
            preparedStatement.setString(1, address);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateAddressById(String newAddress, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS);
            preparedStatement.setString(1, newAddress);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Address[] findAll() {
        try {
            List<Address> addresses = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ADDRESSES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int anInt = resultSet.getInt(1);
                String string = resultSet.getString(2);
                addresses.add(new Address(anInt, string));
            }
            return addresses.toArray(new Address[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Address(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Address findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Address(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
