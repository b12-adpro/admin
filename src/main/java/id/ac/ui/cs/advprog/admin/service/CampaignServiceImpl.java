package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CampaignServiceImpl implements CampaignService {

    private RestTemplate restTemplate;
    private final DonationHistoryService donationHistoryService;

    @Value("${external.campaign.api.url}")
    private String campaignApiUrl;

    @Autowired
    public CampaignServiceImpl(RestTemplate restTemplate, DonationHistoryService donationHistoryService) {
        this.restTemplate = restTemplate;
        this.donationHistoryService = donationHistoryService;
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
        return campaign;
    }

    @Override
    public String getCampaignDtoName(UUID campaignId) {
        if (campaignId == null) {
            throw new IllegalArgumentException("Campaign ID cannot be null");
        }
        return null;
    }

    private int calculateCurrentAmount(UUID campaignId) {
        List<DonationHistoryDTO> donations = donationHistoryService.getDonationHistoryByCampaign(campaignId);

        return donations.stream()
                .map(DonationHistoryDTO::getAmount)
                .mapToInt(BigDecimal::intValue)
                .sum();
    }

    @Override
    public List<CampaignDTO> getAllCampaigns() {
        CampaignDTO[] campaigns = restTemplate.getForObject(campaignApiUrl + "/all", CampaignDTO[].class);

        if (campaigns.length == 0) {
            return new ArrayList<>();
        }

        return List.of(campaigns).stream()
                .map(campaign -> {
                    campaign.setCurrentAmount(calculateCurrentAmount(campaign.getCampaignId()));
                    return refreshProgressStatus(campaign);
                })
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO getCampaignDTOById(UUID id) {
        try {
            String url = campaignApiUrl + "/campaignId/" + id;
            CampaignDTO campaign = restTemplate.getForObject(url, CampaignDTO.class);
            campaign.setCurrentAmount(calculateCurrentAmount(campaign.getCampaignId()));
            return refreshProgressStatus(campaign);
        } catch (HttpClientErrorException e) {
            throw new NoSuchElementException("Campaign with id " + id + " not found");
        }
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignProgressStatus(Status campaignProgressStatus) {
        CampaignDTO[] campaigns = restTemplate.getForObject(campaignApiUrl + "/all", CampaignDTO[].class);
        List<CampaignDTO> refreshedCampaigns = List.of(campaigns).stream()
                .map(this::refreshProgressStatus)
                .filter(c -> campaignProgressStatus.name().equals(c.getStatus()))
                .collect(Collectors.toList());

        return refreshedCampaigns;
    }

    @Override
    public List<CampaignDTO> getCampaignsByCampaignVerificationStatus(Status verificationStatus) {
        CampaignDTO[] campaigns = restTemplate.getForObject(campaignApiUrl + "/all", CampaignDTO[].class);
        return List.of(campaigns).stream()
                .map(this::refreshProgressStatus)
                .filter(c -> c.getStatus() != null &&
                        verificationStatus.name().equals(c.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public CampaignDTO verifyCampaign(UUID id, boolean approve) {
        CampaignDTO campaign = getCampaignDTOById(id);
        if ((approve && campaign.getStatus().equals(Status.ACTIVE.name())) ||
                (!approve && campaign.getStatus().equals(Status.INACTIVE.name()))) {
            throw new IllegalStateException("Campaign is already in the desired status: " + campaign.getStatus());
        }

        String statusPath = approve ? "/activate" : "/inactivate";
        String url = campaignApiUrl + "/" + id + statusPath;

        try {
            restTemplate.put(url, null);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new RuntimeException("Failed to update campaign status: " + ex.getResponseBodyAsString(), ex);
        }

        return getCampaignDTOById(id); // ambil ulang untuk status ter-update
    }



    @Override
    public long countCampaigns() {
        return getAllCampaigns().size();
    }

    @Override
    public long countCampaignsByStatus(Status status) {
        CampaignDTO[] campaigns = restTemplate.getForObject(campaignApiUrl + "/all", CampaignDTO[].class);

        if (campaigns == null || campaigns.length == 0) {
            return 0;
        }

        return List.of(campaigns).stream()
                .filter(c -> status.name().equals(c.getStatus()))
                .count();
    }

    @Override
    public double getTotalRaisedAmount() {
        CampaignDTO[] campaigns = restTemplate.getForObject(campaignApiUrl + "/all", CampaignDTO[].class);

        if (campaigns == null || campaigns.length == 0) {
            return 0.0;
        }

        return List.of(campaigns).stream()
                .mapToDouble(CampaignDTO::getCurrentAmount)
                .sum();
    }

    @Override
    public String getFundUsageProofsByCampaignId(UUID campaignId) {
        try {
            String url = campaignApiUrl + "/fund-usage-proofs/" + campaignId;
            String result = restTemplate.getForObject(url, String.class);
            if (result == null) {
                throw new NoSuchElementException("Fund usage proof not found for campaign: " + campaignId);
            }
            return result;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NoSuchElementException("Fund usage proof not found for campaign: " + campaignId);
            }
            throw e;
        }
    }
}
