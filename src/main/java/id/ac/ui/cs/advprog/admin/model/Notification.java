package id.ac.ui.cs.advprog.admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "notifications")
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, message = "Judul minimum 2 karakter")
    private String title;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 2, message = "Pesan minimum memiliki 2 karakter")
    private String message;
    private LocalDateTime createdAt;
    private int recipientsCount;

    @PrePersist
    protected void onCreateAndValidate() {
        createdAt = LocalDateTime.now();

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (recipientsCount < 0) {
            throw new IllegalArgumentException("Recipients count must be zero or positive");
        }
    }

    @PreUpdate
    private void validateOnUpdate() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
        if (recipientsCount < 0) {
            throw new IllegalArgumentException("Recipients count must be zero or positive");
        }
    }
}