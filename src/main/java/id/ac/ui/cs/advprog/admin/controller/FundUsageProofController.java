package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.service.FundUsageProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/fund-usage")
@RequiredArgsConstructor
public class FundUsageProofController {

    private final FundUsageProofService fundUsageProofService;

    @GetMapping
    public ResponseEntity<List<FundUsageProofDTO>> getAllProofs() {
        return ResponseEntity.ok(fundUsageProofService.getAllProofsDTO());
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<FundUsageProofDTO>> getProofsByCampaign(@PathVariable Long campaignId) {
        return ResponseEntity.ok(fundUsageProofService.getProofsByCampaignDTO(campaignId));
    }
}
