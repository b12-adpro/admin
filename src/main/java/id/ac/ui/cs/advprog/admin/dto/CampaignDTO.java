package id.ac.ui.cs.advprog.admin.dto;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private String id;
    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignStatus status;
}
