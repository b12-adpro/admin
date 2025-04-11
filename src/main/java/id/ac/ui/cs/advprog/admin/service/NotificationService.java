package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.Notification;

import java.util.List;

public interface NotificationService {
    // Membuat notifikasi baru
    Notification createNotification(String title, String content);

    // Mendapatkan semua notifikasi
    List<Notification> getAllNotifications();

    // Mendapatkan notifikasi berdasarkan ID
    Notification getNotificationById(Long id);
}