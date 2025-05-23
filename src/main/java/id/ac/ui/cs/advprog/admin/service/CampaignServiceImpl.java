package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final List<CampaignDTO> dummyCampaigns = new ArrayList<>();
    private final List<FundUsageProofDTO> dummyProofs = new ArrayList<>();

    @PostConstruct
    private void initDummyData() {
        UUID campaignAId = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989");
        UUID fundraiserAId = UUID.fromString("f1d8eabc-1234-4c00-aaaa-bbbbcccc0001");

        UUID campaignBId = UUID.fromString("8c3629ee-5f71-4df1-82a3-2bbbe2455612");
        UUID fundraiserBId = UUID.fromString("f1d8eabc-1234-4c00-aaaa-bbbbcccc0002");

        UUID campaignCId = UUID.fromString("e9a3c4d1-d9de-44f4-8be4-b0bb5f822017");
        UUID fundraiserCId = UUID.fromString("f1d8eabc-1234-4c00-aaaa-bbbbcccc0003");

        dummyCampaigns.add(new CampaignDTO(
                campaignAId, fundraiserAId, "Andi", "Kampanye A", 10000.0, 5000.0,
                LocalDate.now().minusDays(10), LocalDate.now().plusDays(10),
                CampaignVerificationStatus.VERIFIED, null
        ));

        dummyCampaigns.add(new CampaignDTO(
                campaignBId, fundraiserBId, "Budi", "Kampanye B", 20000.0, 20000.0,
                LocalDate.now().minusDays(20), LocalDate.now().minusDays(1),
                CampaignVerificationStatus.VERIFIED, null
        ));

        dummyCampaigns.add(new CampaignDTO(
                campaignCId, fundraiserCId, "Sita", "Kampanye C", 15000.0, 0.0,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(20),
                CampaignVerificationStatus.PENDING, null
        ));

        dummyProofs.add(FundUsageProofDTO.builder()
                .id(UUID.randomUUID())
                .campaignId(campaignAId)
                .title("Pembelian Sembako")
                .description("Digunakan untuk membeli kebutuhan pokok.")
                .amount(1000.0)
                .submittedAt(LocalDateTime.now().minusDays(2))
                .build());

        dummyProofs.add(FundUsageProofDTO.builder()
                .id(UUID.randomUUID())
                .campaignId(campaignBId)
                .title("Biaya Operasional")
                .description("Transportasi dan logistik.")
                .amount(2000.0)
                .submittedAt(LocalDateTime.now().minusDays(5))
                .build());
    }

    private CampaignProgressStatus calculateProgressStatus(CampaignDTO campaign) {
        if (campaign.getVerificationStatus() != CampaignVerificationStatus.VERIFIED) {
            return null;
        }
        LocalDate today = LocalDate.now();
        if (today.isBefore(campaign.getStartDate())) {
            return CampaignProgressStatus.UPCOMING;
        } else if (today.isAfter(campaign.getEndDate())) {
            return CampaignProgressStatus.COMPLETED;
        } else {
            return CampaignProgressStatus.ACTIVE;
        }
    }

    private CampaignDTO refreshProgressStatus(CampaignDTO campaign) {
        campaign.setProgressStatus(calculateProgressStatus(campaign));
        return campaign;
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        return dummyCampaigns.stream()
                .map(this::refreshProgressStatus)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO getCampaignDTOById(UUID id) {
        CampaignDTO campaign = dummyCampaigns.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Campaign not found"));
        return refreshProgressStatus(campaign);
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignProgressStatus(CampaignProgressStatus campaignProgressStatus) {
        return dummyCampaigns.stream()
                .map(this::refreshProgressStatus)
                .filter(c -> campaignProgressStatus.equals(c.getProgressStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignVerificationStatus(CampaignVerificationStatus verificationStatus) {
        return dummyCampaigns.stream()
                .map(this::refreshProgressStatus)
                .filter(c -> verificationStatus.equals(c.getVerificationStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO verifyCampaign(UUID id, boolean approve) {
        CampaignDTO campaign = getCampaignDTOById(id);
        campaign.setVerificationStatus(approve ? CampaignVerificationStatus.VERIFIED : CampaignVerificationStatus.REJECTED);
        campaign.setProgressStatus(calculateProgressStatus(campaign));
        return campaign;
    }

    @Override
    public long countCampaigns() {
        return dummyCampaigns.size();
    }

    @Override
    public long countCampaignsByStatus(CampaignProgressStatus status) {
        return dummyCampaigns.stream()
                .map(this::refreshProgressStatus)
                .filter(c -> status.equals(c.getProgressStatus()))
                .count();
    }

    @Override
    public double getTotalRaisedAmount() {
        return dummyCampaigns.stream()
                .mapToDouble(CampaignDTO::getCurrentAmount)
                .sum();
    }

    @Override
    public double getTotalTargetAmount() {
        return dummyCampaigns.stream()
                .mapToDouble(CampaignDTO::getTargetAmount)
                .sum();
    }

    @Override
    public List<FundUsageProofDTO> getFundUsageProofsByCampaignId(UUID id) {
        return dummyProofs.stream()
                .filter(proof -> proof.getCampaignId().equals(id))
                .collect(Collectors.toList());
    }
}
