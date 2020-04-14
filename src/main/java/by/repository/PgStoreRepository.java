package by.repository;

import by.entity.Address;
import by.entity.City;
import by.entity.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgStoreRepository implements StoreRepository {
    Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_STORE = "insert into store (name, address_id, city_id) values (?, ?, ?) returning id";
    private static final String UPDATE_STORE_BY_ID = "update store set name=? where id=?";
    private static final String FIND_ALL = "select * from store join address a on store.address_id = a.id join city c on store.city_id = c.id";
    private static final String FIND_BY_NAME = "select * from store where name=?";
    private static final String DELETE_STORE_ID = "delete from store where id=?";
    private static final String FIND_ALL_FROM_CITY_BY_ID = "select * from city where id=?";
    private static final String FIND_ALL_FROM_ADDRESS_BY_ID = "select * from address where id=?";

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Store store) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_STORE);
//            preparedStatement.setInt(1,store.getId());
            preparedStatement.setString(1, store.getName());
            preparedStatement.setInt(2, store.getAddress().getId());
            preparedStatement.setInt(3, store.getCity().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STORE_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STORE_BY_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Store[] findAll() {
        try {
            List<Store> stores = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
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
            return stores.toArray(new Store[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private City getCityById(int cityId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FROM_CITY_BY_ID);
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
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_FROM_ADDRESS_BY_ID);
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

    @Override
    public Store findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
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
