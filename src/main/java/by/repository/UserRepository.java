package by.repository;

import by.entity.User;

public interface UserRepository {
  User findById(int id);
  User findByEmail(String email);
  void add(User user);
  void delete(String email);
  void updateFirstName(String newFirstName, String email);
  void updateLastName(String newLastName, String email);
  void updatePassword(String newPassword, String email);
  void updateStatus(boolean newStatus, int id);
}
