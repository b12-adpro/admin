package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;

import java.util.List;
import java.util.UUID;

public interface CampaignService {
    List<CampaignDTO> getAllCampaigns();
    List<CampaignDTO> getCampaignsByCampaignProgressStatus(Status status);
    List<CampaignDTO> getCampaignsByCampaignVerificationStatus(Status verificationStatus);
    String getCampaignDtoName(UUID campaignId);
    CampaignDTO getCampaignDTOById(UUID id);
    CampaignDTO verifyCampaign(UUID id, boolean approve);
    long countCampaigns();
    long countCampaignsByStatus(Status status);
    double getTotalRaisedAmount();
    double getTotalTargetAmount();
    String getFundUsageProofsByCampaignId(UUID campaignId);
}
