package id.ac.ui.cs.advprog.admin.dto.Donation;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonationTransactionDTO {
    private UUID id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private WalletDTO wallet;
    private UUID donationId;
    private UUID campaignId;
}