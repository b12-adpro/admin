package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class FundUsageProofDTO {
    private Long id;
    private Long campaignId;
    private String title;
    private String description;
    private Double amount;
    private LocalDateTime submittedAt;
}
