package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;

import java.util.List;
import java.util.UUID;

public interface CampaignService {
    List<CampaignDTO> getAllCampaigns();
    List<CampaignDTO> getCampaignsByCampaignProgressStatus(CampaignProgressStatus status);
    List<CampaignDTO> getCampaignsByCampaignVerificationStatus(CampaignVerificationStatus status);
    CampaignDTO getCampaignDTOById(UUID id);
    CampaignDTO verifyCampaign(UUID id, boolean approve);
    long countCampaigns();
    long countCampaignsByStatus(CampaignProgressStatus status);
    double getTotalRaisedAmount();
    double getTotalTargetAmount();
    List<FundUsageProofDTO> getFundUsageProofsByCampaignId(UUID id);
}
