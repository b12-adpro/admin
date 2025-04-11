package id.ac.ui.cs.advprog.admin.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donations")
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "donatur_id")
    private User donatur;

    @NotNull
    private Double amount;

    private String message; // Optional message from donatur

    private boolean isAnonymous; // Whether donation is anonymous

    private LocalDateTime donationDate;

    private String transactionId; // Reference to payment gateway transaction

    @PrePersist
    protected void onCreate() {
        donationDate = LocalDateTime.now();
    }
}