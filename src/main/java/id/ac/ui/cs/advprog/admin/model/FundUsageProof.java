package id.ac.ui.cs.advprog.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "fund_usage_proofs")
@NoArgsConstructor
@AllArgsConstructor
public class FundUsageProof {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long campaignId;
    private String title;
    private String description;
    private Double amount; // Amount of funds used
    private LocalDateTime submittedAt;
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}