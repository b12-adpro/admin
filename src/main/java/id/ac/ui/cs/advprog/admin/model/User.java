package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String fullName;
    private String phoneNumber;

    private boolean isBlocked = false;
    private boolean isVerified = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "fundraiser")
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "donatur")
    private List<Donation> donations;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Wallet wallet;

    @NotNull
    @ManyToMany(mappedBy = "recipients")
    private List<Notification> notifications;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Method to check if user is admin
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
}