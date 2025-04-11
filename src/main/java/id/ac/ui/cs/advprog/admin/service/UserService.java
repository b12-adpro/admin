package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // Mendapatkan semua pengguna
    List<User> getAllUsers();

    // Mendapatkan pengguna berdasarkan ID
    Optional<User> getUserById(Long id);

    // Memblokir pengguna
    User blockUser(Long id);

    // Menghapus pengguna
    void deleteUser(Long id);

    boolean isUserBlocked(Long id);

    // Untuk mendapatkan semua pengguna yang tidak diblokir
    List<User> getAllActiveUsers();
    int countAllUsers();
    int countUsersByRole(UserRole role);
}