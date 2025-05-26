package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDTO> getAllUsers();
    Optional<UserDTO> getUserById(UUID id);
    boolean isUserBlocked(UUID id);
    List<UserDTO> getAllActiveUsers();
    int countAllUsers();
    String getDonaturName(UUID donaturId);
}
