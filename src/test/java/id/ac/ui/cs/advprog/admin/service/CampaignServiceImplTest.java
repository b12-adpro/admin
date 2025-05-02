package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
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
    void testUpdateCampaignStatus() {
        List<CampaignDTO> activeCampaigns = campaignService.getCampaignsByStatus(CampaignStatus.ACTIVE);
        assertEquals(1, activeCampaigns.size());
        assertEquals("Kampanye A", activeCampaigns.get(0).getTitle());
    }

    @Test
    void testVerifyCampaignApproved() {
        CampaignDTO updated = campaignService.verifyCampaign(3L, true);
        assertEquals(CampaignStatus.ACTIVE, updated.getStatus());
    }

    @Test
    void testVerifyCampaignReject() {
        CampaignDTO campaign = campaignService.verifyCampaign(3L, false); // Campaign C awalnya PENDING

        assertEquals("3", campaign.getId());
        assertEquals(CampaignStatus.REJECTED, campaign.getStatus());
    }

    @Test
    void testCountCampaigns() {
        assertEquals(3, campaignService.countCampaigns());
    }

    @Test
    void testCountCampaignsByStatus() {
        long count = campaignService.countCampaignsByStatus(CampaignStatus.COMPLETED);
        assertEquals(1, count);
    }

    @Test
    void testGetTotalRaisedAmount() {
        double total = campaignService.getTotalRaisedAmount();
        assertEquals(25000.0, total);
    }

    @Test
    void testGetTotalTargetAmount() {
        double total = campaignService.getTotalTargetAmount();
        assertEquals(45000.0, total);
    }
}
