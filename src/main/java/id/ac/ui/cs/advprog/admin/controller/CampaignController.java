package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public CampaignDTO getCampaignById(@PathVariable UUID id) {
        return campaignService.getCampaignDTOById(id);
    }

    @GetMapping("/campaignprogressstatus/{campaignprogressstatus}")
    public List<CampaignDTO> getCampaignsByCampaignProgressStatus(@PathVariable CampaignProgressStatus campaignprogressstatus) {
        return campaignService.getCampaignsByCampaignProgressStatus(campaignprogressstatus);
    }

    @GetMapping("/campaignverificationstatus/{campaignverificationstatus}")
    public List<CampaignDTO> getCampaignsByCampaignVerificationStatus(@PathVariable CampaignVerificationStatus campaignverificationstatus) {
        return campaignService.getCampaignsByCampaignVerificationStatus(campaignverificationstatus);
    }

    @PostMapping("/{id}/verify")
    public CampaignDTO verifyCampaign(@PathVariable UUID id, @RequestParam boolean approve) {
        return campaignService.verifyCampaign(id, approve);
    }

    @GetMapping("/{id}/fund-usage-proofs")
    public List<FundUsageProofDTO> getFundUsageProofs(@PathVariable UUID id) {
        return campaignService.getFundUsageProofsByCampaignId(id);
    }
}
