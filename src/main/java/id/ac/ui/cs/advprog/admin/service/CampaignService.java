package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;

import java.util.List;

public interface CampaignService {
    List<CampaignDTO> getAllCampaigns();
    List<CampaignDTO> getCampaignsByStatus(CampaignStatus status);
    CampaignDTO getCampaignDTOById(Long id);
    CampaignDTO updateCampaignStatus(Long id, CampaignStatus status);
    CampaignDTO verifyCampaign(Long id, boolean approve);
    long countCampaigns();
    long countCampaignsByStatus(CampaignStatus status);
    double getTotalRaisedAmount();
    double getTotalTargetAmount();
    List<FundUsageProofDTO> getFundUsageProofsByCampaignId(Long id);
}
