package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.service.DonationHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/donation-history")
public class DonationHistoryController {

    private final DonationHistoryService donationHistoryService;

    public DonationHistoryController(DonationHistoryService donationHistoryService) {
        this.donationHistoryService = donationHistoryService;
    }

    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<DonationHistoryDTO>> getDonationHistoryByCampaign(
            @PathVariable UUID campaignId) {
        List<DonationHistoryDTO> donationHistory = donationHistoryService.getDonationHistoryByCampaign(campaignId);
        return ResponseEntity.ok(donationHistory);
    }

    @GetMapping
    public ResponseEntity<List<DonationHistoryDTO>> getAllDonationHistories() {
        List<DonationHistoryDTO> donationHistory = donationHistoryService.getAllDonationHistories();
        return ResponseEntity.ok(donationHistory);
    }

    @GetMapping("/donatur/{donaturId}")
    public ResponseEntity<List<DonationHistoryDTO>> getDonationsByDonatur(@PathVariable UUID donaturId) {
        return ResponseEntity.ok(donationHistoryService.getDonationHistoryByDonatur(donaturId));
    }
}
