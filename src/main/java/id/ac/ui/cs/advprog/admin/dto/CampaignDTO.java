package id.ac.ui.cs.advprog.admin.dto;

import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private String id;
    private String fundraiserId;
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignVerificationStatus verificationStatus;
    private CampaignProgressStatus progressStatus;
}
