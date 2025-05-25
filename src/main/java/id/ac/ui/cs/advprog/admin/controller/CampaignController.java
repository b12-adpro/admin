package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/campaigns")
public class CampaignController {

    private CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    public CampaignDTO getCampaignById(@PathVariable UUID id) {
        return campaignService.getCampaignDTOById(id);
    }

    @GetMapping("/campaignprogressstatus/{campaignprogressstatus}")
    public List<CampaignDTO> getCampaignsByCampaignProgressStatus(@PathVariable Status campaignprogressstatus) {
        return campaignService.getCampaignsByCampaignProgressStatus(campaignprogressstatus);
    }

    @GetMapping("/campaignverificationstatus/{campaignverificationstatus}")
    public List<CampaignDTO> getCampaignsByCampaignVerificationStatus(@PathVariable Status campaignverificationstatus) {
        return campaignService.getCampaignsByCampaignVerificationStatus(campaignverificationstatus);
    }

    @PostMapping("/{id}/verify")
    public CampaignDTO verifyCampaign(@PathVariable UUID id, @RequestParam boolean approve) {
        return campaignService.verifyCampaign(id, approve);
    }

    @GetMapping("/{campaignId}/fund-usage-proofs")
    public String getFundUsageProofs(@PathVariable UUID campaignId) {
        return campaignService.getFundUsageProofsByCampaignId(campaignId);
    }
}
