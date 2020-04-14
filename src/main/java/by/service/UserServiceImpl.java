package by.service;

import by.entity.User;
import by.repository.UserRepository;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void changeStatus(boolean a, int id) {
        userRepository.updateStatus(a, id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void add(User user) {
        userRepository.add(user);
    }

    @Override
    public void delete(String email) {
        userRepository.delete(email);
    }

    @Override
    public void updateFirstName(String newFirstName, String email) {
        userRepository.updateFirstName(newFirstName, email);
    }

    @Override
    public void updateLastName(String newLastName, String email) {
        userRepository.updateLastName(newLastName, email);
    }

    @Override
    public void updatePassword(String newPassword, String email) {
        userRepository.updatePassword(newPassword, email);
    }


}
