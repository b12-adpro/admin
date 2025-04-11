package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.repository.CampaignRepository;
import id.ac.ui.cs.advprog.admin.service.CampaignService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + id));
    }

    @Override
    public List<Campaign> getCampaignsByStatus(CampaignStatus status) {
        return campaignRepository.findByStatus(status);
    }

    @Override
    public Campaign updateCampaignStatus(Long id, CampaignStatus status) {
        Campaign campaign = getCampaignById(id);
        campaign.setStatus(status);
        return campaignRepository.save(campaign);
    }

    @Override
    public Campaign verifyCampaign(Long id, boolean approve) {
        Campaign campaign = getCampaignById(id);

        if (campaign.getStatus() == CampaignStatus.PENDING) {
            if (approve) {
                campaign.setStatus(CampaignStatus.ACTIVE);
            } else {
                campaign.setStatus(CampaignStatus.REJECTED);
            }
            return campaignRepository.save(campaign);
        } else {
            throw new RuntimeException("Cannot verify campaign with status: " + campaign.getStatus());
        }
    }

    @Override
    public List<FundUsageProof> getFundUsageProofs(Long campaignId) {
        Campaign campaign = getCampaignById(campaignId);
        return campaign.getFundUsageProofs();
    }

    @Override
    public CampaignDTO mapToDTO(Campaign campaign) {
        return new CampaignDTO(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getDescription(),
                campaign.getTargetAmount(),
                campaign.getCurrentAmount(), // pakai currentAmount sesuai model
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus()
        );
    }

    @Override
    public List<CampaignDTO> getAllCampaignDTOs() {
        return campaignRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countCampaigns() {
        return campaignRepository.count();
    }

    @Override
    public long countCampaignsByStatus(CampaignStatus status) {
        return campaignRepository.countByStatus(status);
    }

    @Override
    public double getTotalRaisedAmount() {
        Double result = campaignRepository.getTotalRaisedAmount();
        return result != null ? result : 0.0;
    }

    @Override
    public double getTotalTargetAmount() {
        Double result = campaignRepository.getTotalTargetAmount();
        return result != null ? result : 0.0;
    }

    @Override
    public int countAllCampaigns() {
        return (int) campaignRepository.count();
    }
}