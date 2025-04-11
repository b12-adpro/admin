package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

    private String title;
    private String message;
    private String createdAtFormatted;

    // Constructor khusus buat convert dari entity Notification
    public NotificationDTO(String title, String message, LocalDateTime createdAt) {
        this.title = title;
        this.message = message;
        this.createdAtFormatted = createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }
}