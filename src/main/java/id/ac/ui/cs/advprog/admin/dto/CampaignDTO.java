package id.ac.ui.cs.advprog.admin.dto;

import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private UUID id;
    private UUID fundraiserId;
    private String fundraiserName;
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignVerificationStatus verificationStatus;
    private CampaignProgressStatus progressStatus;
}
