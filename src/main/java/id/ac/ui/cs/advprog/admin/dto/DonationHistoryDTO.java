package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DonationHistoryDTO {
    private Long id;
    private String campaignTitle;
    private String donorName;
    private Double amount;
    private LocalDateTime donatedAt;
}
