package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}