package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final List<CampaignDTO> dummyCampaigns = new ArrayList<>();
    private final List<FundUsageProofDTO> dummyProofs = new ArrayList<>();

    public CampaignServiceImpl() {
        dummyCampaigns.add(new CampaignDTO(
                "1", "U001", "Kampanye A", 10000.0, 5000.0,
                LocalDate.now().minusDays(10), LocalDate.now().plusDays(10),
                CampaignVerificationStatus.VERIFIED, null
        ));

        dummyCampaigns.add(new CampaignDTO(
                "2", "U002", "Kampanye B", 20000.0, 20000.0,
                LocalDate.now().minusDays(20), LocalDate.now().minusDays(1),
                CampaignVerificationStatus.VERIFIED, null
        ));

        dummyCampaigns.add(new CampaignDTO(
                "3", "U003", "Kampanye C", 15000.0, 0.0,
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(20),
                CampaignVerificationStatus.PENDING, null
        ));

        dummyProofs.add(FundUsageProofDTO.builder()
                .id(1L)
                .campaignId(1L)
                .title("Pembelian Sembako")
                .description("Digunakan untuk membeli kebutuhan pokok.")
                .amount(1000.0)
                .submittedAt(LocalDateTime.now().minusDays(2))
                .build());

        dummyProofs.add(FundUsageProofDTO.builder()
                .id(2L)
                .campaignId(2L)
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
            return CampaignProgressStatus.PENDING;
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
    public CampaignDTO getCampaignDTOById(Long id) {
        CampaignDTO campaign = dummyCampaigns.stream()
                .filter(c -> c.getId().equals(id.toString()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Campaign not found"));
        return refreshProgressStatus(campaign);
    }

    @Override
    public List<CampaignDTO> getCampaignsByStatus(CampaignProgressStatus status) {
        return dummyCampaigns.stream()
                .map(this::refreshProgressStatus)
                .filter(c -> status.equals(c.getProgressStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO verifyCampaign(Long id, boolean approve) {
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
    public List<FundUsageProofDTO> getFundUsageProofsByCampaignId(Long id) {
        return dummyProofs.stream()
                .filter(proof -> proof.getCampaignId().equals(id))
                .collect(Collectors.toList());
    }
}
