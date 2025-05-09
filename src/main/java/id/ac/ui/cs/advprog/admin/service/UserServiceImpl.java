package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Map<String, UserDTO> dummyUsers = new HashMap<>();

    public UserServiceImpl() {

        dummyUsers.put("7e8725e7-c9d8-4176-a392-4c3897042990", UserDTO.builder().id(UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042990")).name("Siti").role(UserRole.DONATUR).isBlocked(false).build());
        dummyUsers.put("7e8725e7-c9d8-4176-a392-4c3897042991", UserDTO.builder().id(UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042991")).name("Budi").role(UserRole.FUNDRAISER).isBlocked(true).build());
        dummyUsers.put("7e8725e7-c9d8-4176-a392-4c3897042989", UserDTO.builder().id(UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989")).name("Andi").role(UserRole.FUNDRAISER).isBlocked(false).build());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(dummyUsers.values());
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        return Optional.ofNullable(dummyUsers.get(id));
    }

    @Override
    public UserDTO setBlockedStatus(UUID id, boolean status) {
        UserDTO user = dummyUsers.get(id.toString());
        if (user != null) {
            user.setBlocked(status);
        }
        return user;
    }

    @Override
    public void deleteUser(UUID id) {
        dummyUsers.remove(id.toString());
    }

    @Override
    public boolean isUserBlocked(UUID id) {
        UserDTO user = dummyUsers.get(id);
        return user != null && user.isBlocked();
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        return dummyUsers.values().stream()
                .filter(user -> !user.isBlocked())
                .collect(Collectors.toList());
    }

    @Override
    public int countAllUsers() {
        return dummyUsers.size();
    }

    @Override
    public int countUsersByRole(UserRole role) {
        return (int) dummyUsers.values().stream()
                .filter(user -> user.getRole() == role)
                .count();
    }
}
