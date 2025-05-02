package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public CampaignDTO getCampaignById(@PathVariable Long id) {
        return campaignService.getCampaignDTOById(id);
    }

    @GetMapping("/status/{status}")
    public List<CampaignDTO> getCampaignsByStatus(@PathVariable CampaignStatus status) {
        return campaignService.getCampaignsByStatus(status);
    }

    @PutMapping("/{id}/status")
    public CampaignDTO updateCampaignStatus(@PathVariable Long id, @RequestParam CampaignStatus status) {
        return campaignService.updateCampaignStatus(id, status);
    }

    @PostMapping("/{id}/verify")
    public CampaignDTO verifyCampaign(@PathVariable Long id, @RequestParam boolean approve) {
        return campaignService.verifyCampaign(id, approve);
    }

    @GetMapping("/{id}/fund-usage-proofs")
    public List<FundUsageProofDTO> getFundUsageProofs(@PathVariable Long id) {
        return campaignService.getFundUsageProofsByCampaignId(id);
    }
}
