package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void testSaveAndFindNotification() {
        Notification notification = new Notification();
        notification.setMessage("Donasi berhasil");
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);

        Optional<Notification> found = notificationRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Donasi berhasil", found.get().getMessage());
    }

    @Test
    void testDeleteNotification() {
        Notification notification = new Notification();
        notification.setMessage("Laporan diterima");
        notification.setCreatedAt(LocalDateTime.now());

        Notification saved = notificationRepository.save(notification);
        UUID id = saved.getId();

        notificationRepository.deleteById(id);
        Optional<Notification> found = notificationRepository.findById(id);

        assertFalse(found.isPresent());
    }

    @Test
    void testFindAllNotifications() {
        Notification notif1 = new Notification();
        notif1.setMessage("Satu");
        notif1.setCreatedAt(LocalDateTime.now());

        Notification notif2 = new Notification();
        notif2.setMessage("Dua");
        notif2.setCreatedAt(LocalDateTime.now());

        notificationRepository.save(notif1);
        notificationRepository.save(notif2);

        assertEquals(2, notificationRepository.findAll().size());
    }
}
