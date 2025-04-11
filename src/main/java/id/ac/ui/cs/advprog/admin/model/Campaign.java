package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotBlank(message = "Description must not be blank")
    private String description;

    @NotNull(message = "Target amount is required")
    @PositiveOrZero(message = "Target amount must be zero or positive")
    private Double targetAmount;

    @NotNull(message = "Current amount is required")
    @PositiveOrZero(message = "Current amount must be zero or positive")
    private Double currentAmount;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Campaign status is required")
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    @ManyToOne
    @NotNull(message = "Fundraiser is required")
    private User fundraiser;

    @OneToMany(mappedBy = "campaign")
    private List<Donation> donations;

    @OneToMany(mappedBy = "campaign")
    private List<FundUsageProof> fundUsageProofs;
}
