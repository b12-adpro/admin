package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;

import java.util.List;
import java.util.UUID;

public interface FundUsageProofService {
    List<FundUsageProofDTO> getAllProofsDTO();
    List<FundUsageProofDTO> getProofsByCampaignDTO(UUID campaignId);
}
