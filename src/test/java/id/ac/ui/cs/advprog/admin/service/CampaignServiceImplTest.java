package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.repository.CampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignServiceImplTest {

    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private Campaign sampleCampaign;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleCampaign = new Campaign();
        sampleCampaign.setId(1L);
        sampleCampaign.setTitle("Sample");
        sampleCampaign.setDescription("Desc");
        sampleCampaign.setTargetAmount(1000.0);
        sampleCampaign.setCurrentAmount(500.0);
        sampleCampaign.setStartDate(LocalDate.now());
        sampleCampaign.setEndDate(LocalDate.now().plusDays(30));
        sampleCampaign.setStatus(CampaignStatus.PENDING);
        sampleCampaign.setFundUsageProofs(new ArrayList<>());
    }

    @Test
    void testGetCampaignById() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(sampleCampaign));
        Campaign campaign = campaignService.getCampaignById(1L);
        assertEquals("Sample", campaign.getTitle());
    }

    @Test
    void testGetCampaignsByStatus() {
        List<Campaign> campaigns = List.of(sampleCampaign);
        when(campaignRepository.findByStatus(CampaignStatus.PENDING)).thenReturn(campaigns);

        List<Campaign> result = campaignService.getCampaignsByStatus(CampaignStatus.PENDING);
        assertEquals(1, result.size());
        assertEquals(CampaignStatus.PENDING, result.get(0).getStatus());
    }

    @Test
    void testUpdateCampaignStatus() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(sampleCampaign));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(sampleCampaign);

        Campaign updated = campaignService.updateCampaignStatus(1L, CampaignStatus.ACTIVE);
        assertEquals(CampaignStatus.ACTIVE, updated.getStatus());
    }

    @Test
    void testVerifyCampaignApproved() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(sampleCampaign));
        when(campaignRepository.save(any(Campaign.class))).thenReturn(sampleCampaign);

        Campaign verified = campaignService.verifyCampaign(1L, true);
        assertEquals(CampaignStatus.ACTIVE, verified.getStatus());
    }

    @Test
    void testGetFundUsageProofs() {
        when(campaignRepository.findById(1L)).thenReturn(Optional.of(sampleCampaign));
        List<FundUsageProof> result = campaignService.getFundUsageProofs(1L);
        assertNotNull(result);
    }

    @Test
    void testMapToDTO() {
        CampaignDTO dto = campaignService.mapToDTO(sampleCampaign);
        assertEquals(sampleCampaign.getId(), dto.getId());
        assertEquals(sampleCampaign.getTitle(), dto.getTitle());
    }

    @Test
    void testCountCampaigns() {
        when(campaignRepository.count()).thenReturn(5L);
        assertEquals(5, campaignService.countCampaigns());
    }

    @Test
    void testGetTotalRaisedAmount() {
        when(campaignRepository.getTotalRaisedAmount()).thenReturn(1234.56);
        assertEquals(1234.56, campaignService.getTotalRaisedAmount());
    }

    @Test
    void testGetTotalTargetAmount() {
        when(campaignRepository.getTotalTargetAmount()).thenReturn(4321.0);
        assertEquals(4321.0, campaignService.getTotalTargetAmount());
    }
}
