package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(UUID id);
    UserDTO setBlockedStatus(UUID id, boolean status);
    void deleteUser(UUID id);
    boolean isUserBlocked(UUID id);
    List<UserDTO> getAllActiveUsers();
    int countAllUsers();
    int countUsersByRole(UserRole role);
}
