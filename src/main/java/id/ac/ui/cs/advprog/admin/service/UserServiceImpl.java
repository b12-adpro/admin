package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, UserDTO> dummyUsers = new HashMap<>();

    public UserServiceImpl() {
        dummyUsers.put(1L, UserDTO.builder().id(1L).name("Andi").role(UserRole.FUNDRAISER).isBlocked(false).build());
        dummyUsers.put(2L, UserDTO.builder().id(2L).name("Siti").role(UserRole.DONATUR).isBlocked(false).build());
        dummyUsers.put(3L, UserDTO.builder().id(3L).name("Budi").role(UserRole.FUNDRAISER).isBlocked(true).build());
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return new ArrayList<>(dummyUsers.values());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return Optional.ofNullable(dummyUsers.get(id));
    }

    @Override
    public UserDTO blockUser(Long id) {
        UserDTO user = dummyUsers.get(id);
        if (user != null) {
            user.setBlocked(true);
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        dummyUsers.remove(id);
    }

    @Override
    public boolean isUserBlocked(Long id) {
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
