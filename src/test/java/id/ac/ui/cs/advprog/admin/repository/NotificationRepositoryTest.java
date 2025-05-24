package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.Notification;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void testSaveAndFindNotification() {
        Notification notification = new Notification();
        notification.setTitle("Notifikasi");
        notification.setMessage("Donasi berhasil diterima.");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipientsCount(1);

        Notification saved = notificationRepository.save(notification);

        Optional<Notification> found = notificationRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Donasi berhasil diterima.", found.get().getMessage());
    }

    @Test
    void testValidationFailsWhenTitleIsBlank() {
        Notification notification = new Notification();
        notification.setTitle("   "); // invalid
        notification.setMessage("Pesan valid");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipientsCount(1);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testFindAllNotifications() {
        notificationRepository.save(createValidNotification("Notifikasi 1", "Pesan satu valid."));
        notificationRepository.save(createValidNotification("Notifikasi 2", "Pesan dua valid."));

        assertEquals(2, notificationRepository.findAll().size());
    }

    private Notification createValidNotification(String title, String message) {
        Notification notif = new Notification();
        notif.setTitle(title);
        notif.setMessage(message);
        notif.setCreatedAt(LocalDateTime.now());
        notif.setRecipientsCount(1);
        return notif;
    }

}
