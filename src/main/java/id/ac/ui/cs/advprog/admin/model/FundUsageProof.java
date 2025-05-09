package id.ac.ui.cs.advprog.admin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "fund_usage_proofs")
@NoArgsConstructor
@AllArgsConstructor
public class FundUsageProof {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "campaign_id", nullable = false)
    private UUID campaignId;
    private String title;
    private String description;
    private Double amount;
    private LocalDateTime submittedAt;
    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}