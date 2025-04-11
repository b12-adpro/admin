package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setPassword("securepassword");
        user.setRole(UserRole.FUNDRAISER);
        user.setFullName("John Doe");
        user.setPhoneNumber("08123456789");
    }

    @Test
    void testIsAdminShouldReturnFalseForNonAdminUser() {
        user.setRole(UserRole.FUNDRAISER);
        assertFalse(user.isAdmin());
    }

    @Test
    void testIsAdminShouldReturnTrueForAdminUser() {
        user.setRole(UserRole.ADMIN);
        assertTrue(user.isAdmin());
    }

    @Test
    void testOnCreateSetsCreatedAtAndUpdatedAt() {
        user.onCreate();
        LocalDateTime now = LocalDateTime.now();

        assertNotNull(user.getCreatedAt(), "createdAt should not be null");
        assertNotNull(user.getUpdatedAt(), "updatedAt should not be null");

        assertTrue(user.getCreatedAt().isBefore(now.plusSeconds(1)) &&
                user.getCreatedAt().isAfter(now.minusSeconds(1)));

        assertTrue(user.getUpdatedAt().isBefore(now.plusSeconds(1)) &&
                user.getUpdatedAt().isAfter(now.minusSeconds(1)));
    }

    @Test
    void testOnUpdateChangesOnlyUpdatedAt() throws InterruptedException {
        user.onCreate();
        LocalDateTime originalCreatedAt = user.getCreatedAt();
        LocalDateTime firstUpdatedAt = user.getUpdatedAt();

        Thread.sleep(10); // delay to ensure time difference

        user.onUpdate();
        LocalDateTime secondUpdatedAt = user.getUpdatedAt();

        assertEquals(originalCreatedAt, user.getCreatedAt(), "createdAt should remain unchanged");
        assertTrue(secondUpdatedAt.isAfter(firstUpdatedAt), "updatedAt should be later after onUpdate");
    }
}
