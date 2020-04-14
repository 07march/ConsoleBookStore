package by.repository;

import by.entity.City;

public interface CityRepository {
  void add(City city);
  void delete(int id);
  void delete(String name);
  boolean update(String name, int id);
  City[] findAll();
  City findByName(String name);
  City findById(int id);
}
