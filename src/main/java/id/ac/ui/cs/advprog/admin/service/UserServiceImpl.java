package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User blockUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setBlocked(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUserBlocked(Long id) {
        return userRepository.findById(id)
                .map(User::isBlocked)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findByIsBlocked(false);
    }

    @Override
    public int countAllUsers() {
        return (int) userRepository.count();
    }

    @Override
    public int countUsersByRole(UserRole role) {
        return (int) userRepository.countByRole(role);
    }
}