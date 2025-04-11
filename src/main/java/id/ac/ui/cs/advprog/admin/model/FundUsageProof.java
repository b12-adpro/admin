package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
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

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    private String title;
    private String description;

    private Double amount; // Amount of funds used

    private String imageUrl; // URL to proof image/document

    @Enumerated(EnumType.STRING)
    private ProofStatus status; // PENDING_VERIFICATION, VERIFIED, REJECTED

    private String adminNotes; // Notes from admin during verification

    private LocalDateTime submittedAt;
    private LocalDateTime verifiedAt;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}