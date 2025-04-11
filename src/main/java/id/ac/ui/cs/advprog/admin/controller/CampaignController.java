package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    @Autowired
    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // Get campaign by ID
    @GetMapping("/{id}")
    public CampaignDTO getCampaignById(@PathVariable Long id) {
        Campaign campaign = campaignService.getCampaignById(id);
        return campaignService.mapToDTO(campaign);
    }

    // Get all campaigns
    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaignDTOs();
    }

    @GetMapping("/status/{status}")
    public List<CampaignDTO> getCampaignsByStatus(@PathVariable CampaignStatus status) {
        List<Campaign> campaigns = campaignService.getCampaignsByStatus(status);
        return campaigns.stream()
                .map(campaignService::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update campaign status manually
    @PutMapping("/{id}/status")
    public CampaignDTO updateCampaignStatus(@PathVariable Long id, @RequestParam CampaignStatus status) {
        Campaign updated = campaignService.updateCampaignStatus(id, status);
        return campaignService.mapToDTO(updated);
    }

    // Verify or reject campaign
    @PostMapping("/{id}/verify")
    public CampaignDTO verifyCampaign(@PathVariable Long id, @RequestParam boolean approve) {
        Campaign verified = campaignService.verifyCampaign(id, approve);
        return campaignService.mapToDTO(verified);
    }

    // Get fund usage proofs for a campaign
    @GetMapping("/{id}/fund-usage-proofs")
    public List<FundUsageProof> getFundUsageProofs(@PathVariable Long id) {
        return campaignService.getFundUsageProofs(id);
    }
}
