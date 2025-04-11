package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should find user by username")
    void testFindByUsername() {
        User user = new User();
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setRole(UserRole.FUNDRAISER);
        user.setBlocked(false);
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("john_doe");
        assertTrue(found.isPresent());
        assertEquals("john@example.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
        User user = new User();
        user.setUsername("jane_doe");
        user.setEmail("jane@example.com");
        user.setRole(UserRole.DONATUR);
        user.setBlocked(false);
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("jane@example.com");
        assertTrue(found.isPresent());
        assertEquals("jane_doe", found.get().getUsername());
    }

    @Test
    @DisplayName("Should find users by role")
    void testFindByRole() {
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        user1.setRole(UserRole.DONATUR);
        user1.setBlocked(false);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        user2.setRole(UserRole.DONATUR);
        user2.setBlocked(false);

        userRepository.saveAll(List.of(user1, user2));

        List<User> donaturs = userRepository.findByRole(UserRole.DONATUR);
        assertEquals(2, donaturs.size());
    }

    @Test
    @DisplayName("Should count users by role")
    void testCountByRole() {
        User user = new User();
        user.setUsername("fundraiser");
        user.setEmail("fundraiser@example.com");
        user.setRole(UserRole.FUNDRAISER);
        user.setBlocked(false);
        userRepository.save(user);

        long count = userRepository.countByRole(UserRole.FUNDRAISER);
        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should find users by block status")
    void testFindByIsBlocked() {
        User blockedUser = new User();
        blockedUser.setUsername("blocked");
        blockedUser.setEmail("blocked@example.com");
        blockedUser.setRole(UserRole.FUNDRAISER);
        blockedUser.setBlocked(true);

        User activeUser = new User();
        activeUser.setUsername("active");
        activeUser.setEmail("active@example.com");
        activeUser.setRole(UserRole.FUNDRAISER);
        activeUser.setBlocked(false);

        userRepository.saveAll(List.of(blockedUser, activeUser));

        List<User> blocked = userRepository.findByIsBlocked(true);
        assertEquals(1, blocked.size());
        assertEquals("blocked", blocked.get(0).getUsername());
    }
}
