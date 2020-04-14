package by.repository;

import by.entity.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgCityRepository implements CityRepository {
    Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_CITY = "insert into city (name) values (?)";
    private static final String DELETE_ID = "delete from city where id=?";
    private static final String DELETE_NAME = "delete from city where name=?";
    private static final String UPDATE_CITY_BY_ID = "update city set name=? where id=?";
    private static final String FIND_ALL_CITIES = "select * from city";
    private static final String FIND_BY_NAME = "select * from city where name=?";
    private static final String FIND_CITY_BY_ID = "select * from city where id=?";

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        PgCityRepository pgCityRepository = new PgCityRepository();
//        System.out.println(pgCityRepository.findByName("Grodno"));
////        System.out.println(Arrays.toString(pgCityRepository.findAll()));
//
//    }

    @Override
    public void add(City city) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CITY);
//            preparedStatement.setInt(1, city.getId());
            preparedStatement.setString(1, city.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CITY_BY_ID);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(1, name);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public City[] findAll() {
        try {
            List<City> cities = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CITIES);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int cityId = resultSet.getInt(1);
                String cityName = resultSet.getString(2);
                cities.add(new City(cityId, cityName));
            }
            return cities.toArray(new City[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new City(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CITY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new City(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
