package id.ac.ui.cs.advprog.admin.dto;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CampaignDTO {
    private Long id;
    private String title;
    private String description;
    private Double targetAmount;
    private Double currentAmount;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignStatus status;
}
