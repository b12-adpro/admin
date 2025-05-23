package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CampaignServiceImplTest {

    @Autowired
    private CampaignService campaignService;

    @Test
    void testGetAllCampaigns() {
        List<CampaignDTO> campaigns = campaignService.getAllCampaigns();
        assertEquals(3, campaigns.size());
    }

    @Test
    void testGetCampaignByIdSuccess() {
        UUID campaignId = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989"); // Gunakan UUID
        CampaignDTO campaign = campaignService.getCampaignDTOById(campaignId);
        assertEquals("Kampanye A", campaign.getTitle());
    }

    @Test
    void testGetCampaignDTOByIdNotFound() {
        UUID nonExistentId = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042999"); // Gunakan UUID
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            campaignService.getCampaignDTOById(nonExistentId);
        });
        assertEquals("Campaign not found", exception.getMessage());
    }

    @Test
    void testGetCampaignsByProgressStatus() {
        List<CampaignDTO> activeCampaigns = campaignService.getCampaignsByCampaignProgressStatus(CampaignProgressStatus.ACTIVE);
        assertEquals(1, activeCampaigns.size());
        assertEquals("Kampanye A", activeCampaigns.get(0).getTitle());
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
