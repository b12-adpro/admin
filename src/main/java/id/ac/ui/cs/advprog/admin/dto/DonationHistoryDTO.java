package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class DonationHistoryDTO {
    private UUID id;
    private UUID campaignId;
    private UUID donaturId;
    private String donaturName;
    private String campaignTitle;
    private BigDecimal amount;
    private LocalDateTime donatedAt;
}
