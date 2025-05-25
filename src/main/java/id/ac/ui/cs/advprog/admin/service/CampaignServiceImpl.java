package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService {

    // Dummy storage untuk campaigns dan donations
    private final Map<UUID, CampaignDTO> campaignStore = new HashMap<>();
    private final Map<UUID, List<DonationHistoryDTO>> donationStore = new HashMap<>();

    public CampaignServiceImpl() {
        // Initialize dummy data
        initDummyData();
    }

    private void initDummyData() {
        UUID c1 = UUID.randomUUID();
        UUID c2 = UUID.randomUUID();

        CampaignDTO campaign1 = new CampaignDTO();
        campaign1.setCampaignId(c1);
        campaign1.setJudul("Campaign 1");
        campaign1.setStatus(Status.PENDING.name());
        campaign1.setTarget(10000);
        campaign1.setCurrentAmount(0);
        campaign1.setBuktiPenggalanganDana("https://example.com/bukti1.jpg");
        campaign1.setStatus(Status.PENDING.name());
        campaignStore.put(c1, campaign1);

        CampaignDTO campaign2 = new CampaignDTO();
        campaign2.setCampaignId(c2);
        campaign2.setJudul("Campaign 2");
        campaign2.setStatus(Status.ACTIVE.name());
        campaign2.setTarget(5000);
        campaign2.setCurrentAmount(2000);
        campaignStore.put(c2, campaign2);

        donationStore.put(c1, new ArrayList<>());
        donationStore.put(c2, new ArrayList<>(
                List.of(new DonationHistoryDTO(
                        c2,                    // campaignId
                        UUID.randomUUID(),     // userId
                        UUID.randomUUID(),     // paymentId
                        "Dummy User",          // userName
                        "Dummy Payment",       // paymentMethod
                        new BigDecimal("2000"),// amount
                        LocalDateTime.now()    // timestamp
                ))
        ));
    }

    private int calculateCurrentAmount(UUID campaignId) {
        List<DonationHistoryDTO> donations = donationStore.getOrDefault(campaignId, Collections.emptyList());
        return donations.stream()
                .map(DonationHistoryDTO::getAmount)
                .mapToInt(BigDecimal::intValue)
                .sum();
    }

    private String calculateProgressStatus(CampaignDTO campaign) {
        if (campaign.getStatus().equals(Status.PENDING.name())) {
            return "UPCOMING";
        } else if (campaign.getStatus().equals(Status.ACTIVE.name())) {
            return "ACTIVE";
        } else {
            return "INACTIVE";
        }
    }

    private CampaignDTO refreshProgressStatus(CampaignDTO campaign) {
        if (campaign.getStatus() == null) {
            campaign.setStatus(calculateProgressStatus(campaign));
        }
        // update current amount from dummy donation store
        campaign.setCurrentAmount(calculateCurrentAmount(campaign.getCampaignId()));
        return campaign;
    }

    @Override
    public String getCampaignDtoName(UUID campaignId) {
        CampaignDTO campaign = campaignStore.get(campaignId);
        if (campaign == null) {
            throw new NoSuchElementException("Campaign not found");
        }
        return campaign.getJudul();
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        return campaignStore.values().stream()
                .map(this::refreshProgressStatus)
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO getCampaignDTOById(UUID id) {
        CampaignDTO campaign = campaignStore.get(id);
        if (campaign == null) {
            throw new NoSuchElementException("Campaign not found");
        }
        return refreshProgressStatus(campaign);
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignProgressStatus(Status campaignProgressStatus) {
        return campaignStore.values().stream()
                .map(this::refreshProgressStatus)
                .filter(c -> campaignProgressStatus.name().equals(c.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignVerificationStatus(Status verificationStatus) {
        return campaignStore.values().stream()
                .map(this::refreshProgressStatus)
                .filter(c -> c.getStatus() != null && verificationStatus.name().equals(c.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO verifyCampaign(UUID id, boolean approve) {
        CampaignDTO campaign = campaignStore.get(id);
        if (campaign == null) {
            throw new NoSuchElementException("Campaign not found");
        }
        campaign.setStatus(approve ? Status.ACTIVE.name() : Status.INACTIVE.name());
        campaignStore.put(id, campaign);
        return refreshProgressStatus(campaign);
    }

    @Override
    public long countCampaigns() {
        return campaignStore.size();
    }

    @Override
    public long countCampaignsByStatus(Status status) {
        return campaignStore.values().stream()
                .filter(c -> status.name().equals(c.getStatus()))
                .count();
    }

    @Override
    public double getTotalRaisedAmount() {
        return campaignStore.values().stream()
                .mapToDouble(c -> c.getCurrentAmount())
                .sum();
    }

    @Override
    public String getFundUsageProofsByCampaignId(UUID campaignId) {
        if (!campaignStore.containsKey(campaignId)) {
            throw new NoSuchElementException("Fund usage proof not found for campaign: " + campaignId);
        }
        return "Dummy fund usage proof for campaign " + campaignId;
    }

}
