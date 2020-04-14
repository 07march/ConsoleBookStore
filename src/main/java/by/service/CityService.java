package by.service;

import by.entity.City;

public interface CityService {
  void add(City city);
  void delete(int id);
  void delete(String name);
  boolean update(String name, int id);
  City[] findAll();
  City findByName(String name);
  City findById(int id);
}
