package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;

import java.util.List;

public interface CampaignService {
    // Mendapatkan semua kampanye
    List<Campaign> getAllCampaigns();

    // Mendapatkan kampanye berdasarkan ID
    Campaign getCampaignById(Long id);

    // Mendapatkan kampanye berdasarkan status
    List<Campaign> getCampaignsByStatus(CampaignStatus status);

    // Memperbarui status kampanye
    Campaign updateCampaignStatus(Long id, CampaignStatus status);

    // Verifikasi kampanye
    Campaign verifyCampaign(Long id, boolean approve);

    // Mendapatkan bukti penggunaan dana
    List<FundUsageProof> getFundUsageProofs(Long campaignId);

    CampaignDTO mapToDTO(Campaign campaign);

    List<CampaignDTO> getAllCampaignDTOs();

    long countCampaigns();
    long countCampaignsByStatus(CampaignStatus status);
    double getTotalRaisedAmount();
    double getTotalTargetAmount();
    int countAllCampaigns();
}