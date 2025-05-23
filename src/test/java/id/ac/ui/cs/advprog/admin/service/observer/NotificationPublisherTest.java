package id.ac.ui.cs.advprog.admin.service.observer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

class NotificationPublisherTest {

    private NotificationListener listener1;
    private NotificationListener listener2;
    private NotificationPublisher publisher;

    @BeforeEach
    void setUp() {
        listener1 = mock(NotificationListener.class);
        listener2 = mock(NotificationListener.class);

        // Inisialisasi NotificationPublisher dengan listener yang dimock
        publisher = new NotificationPublisher(List.of(listener1, listener2));
    }

    @Test
    void testPublish_shouldCallOnNotificationOnAllListeners() {
        String message = "Test notification";

        // Jalankan publish
        publisher.publish(message);

        // Verifikasi bahwa semua listener menerima notifikasi
        verify(listener1, times(1)).onNotification(message);
        verify(listener2, times(1)).onNotification(message);
    }
}
