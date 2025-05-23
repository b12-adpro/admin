package id.ac.ui.cs.advprog.admin.service.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NotificationPublisher {
    private final List<NotificationListener> listeners;

    @Autowired
    public NotificationPublisher(List<NotificationListener> listeners) {
        this.listeners = listeners;
    }

    public void publish(String message) {
        for (NotificationListener listener : listeners) {
            listener.onNotification(message);
        }
    }
}

