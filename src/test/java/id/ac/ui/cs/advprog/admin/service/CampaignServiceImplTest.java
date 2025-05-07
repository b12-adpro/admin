package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.CampaignVerificationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CampaignServiceImplTest {

    private CampaignServiceImpl campaignService;

    @BeforeEach
    void setUp() {
        campaignService = new CampaignServiceImpl();
    }

    @Test
    void testGetAllCampaigns() {
        List<CampaignDTO> campaigns = campaignService.getAllCampaigns();
        assertEquals(3, campaigns.size());
    }

    @Test
    void testGetCampaignByIdSuccess() {
        CampaignDTO campaign = campaignService.getCampaignDTOById(1L);
        assertEquals("Kampanye A", campaign.getTitle());
    }

    @Test
    void testGetCampaignDTOByIdNotFound() {
        Long nonExistentId = 999L;
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            campaignService.getCampaignDTOById(nonExistentId);
        });
        assertEquals("Campaign not found", exception.getMessage());
    }

    @Test
    void testGetCampaignsByProgressStatus() {
        List<CampaignDTO> activeCampaigns = campaignService.getCampaignsByStatus(CampaignProgressStatus.ACTIVE);
        assertEquals(1, activeCampaigns.size());
        assertEquals("Kampanye A", activeCampaigns.get(0).getTitle());
    }

    @Test
    void testVerifyCampaignApproved() {
        CampaignDTO updated = campaignService.verifyCampaign(3L, true); // Kampanye C: startDate di masa depan
        assertEquals(CampaignVerificationStatus.VERIFIED, updated.getVerificationStatus());
        assertEquals(CampaignProgressStatus.PENDING, updated.getProgressStatus()); // karena belum mulai
    }

    @Test
    void testVerifyCampaignRejected() {
        CampaignDTO updated = campaignService.verifyCampaign(3L, false);
        assertEquals("3", updated.getId());
        assertEquals(CampaignVerificationStatus.REJECTED, updated.getVerificationStatus());
        assertNull(updated.getProgressStatus()); // status progress tidak dihitung jika belum diverifikasi
    }

    @Test
    void testCountCampaigns() {
        assertEquals(3, campaignService.countCampaigns());
    }

    @Test
    void testCountCampaignsByProgressStatus() {
        long completedCount = campaignService.countCampaignsByStatus(CampaignProgressStatus.COMPLETED);
        assertEquals(1, completedCount);
    }

    @Test
    void testGetTotalRaisedAmount() {
        double totalRaised = campaignService.getTotalRaisedAmount();
        assertEquals(25000.0, totalRaised);
    }

    @Test
    void testGetTotalTargetAmount() {
        double totalTarget = campaignService.getTotalTargetAmount();
        assertEquals(45000.0, totalTarget);
    }
}
