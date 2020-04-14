package by.repository;

import by.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PgAuthorRepository implements AuthorRepository {
    Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "root";
    private static final String ADD_AUTHOR = "insert into author (name) values (?)";
    private static final String DELETE_AUTHOR = "delete from author where id=?";
    private static final String UPDATE_AUTHOR = "update author set name=? where id=?";
    private static final String FIND_AUTHOR_BY_ID = "select * from author where id=?";
    private static final String FIND_AUTHOR_BY_NAME = "select * from author where name=?";
    private static final String FIND_ALL_AUTHORS = "select * from author";

    {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(Author a) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_AUTHOR);
//            preparedStatement.setInt(1, a.getId());
            preparedStatement.setString(1, a.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean updateAuthorById(String name, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Author findById(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Author(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author findByName(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_NAME);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Author(resultSet.getInt(1), resultSet.getString(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Author[] findAll() {
        try {
            List<Author> authors = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_AUTHORS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idAuthor = resultSet.getInt(1);
                String nameAuthor = resultSet.getString(2);
                authors.add(new Author(idAuthor, nameAuthor));
            }

            return authors.toArray(new Author[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
