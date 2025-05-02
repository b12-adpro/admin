package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(Long id);
    UserDTO blockUser(Long id);
    void deleteUser(Long id);
    boolean isUserBlocked(Long id);
    List<UserDTO> getAllActiveUsers();
    int countAllUsers();
    int countUsersByRole(UserRole role);
}
