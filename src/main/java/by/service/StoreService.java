package by.service;

import by.entity.Store;

public interface StoreService {
  void add(Store store);
  void delete(int id);
  boolean update(String name, int id);
  Store[] findAll();
  Store findByName(String name);
}
