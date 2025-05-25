package id.ac.ui.cs.advprog.admin.dto.Donation;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTO {
    private UUID id;
    private UUID donaturId;
    private Double balance;
}
