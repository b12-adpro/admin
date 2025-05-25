package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampaignDTO {
    private UUID campaignId;
    private UUID fundraiserId;
    private String fundraiserName;
    private String judul;
    private int target;
    private int currentAmount;
    private LocalDateTime datetime;
    private String status;
    private String deskripsi;
    private String buktiPenggalanganDana;
}
