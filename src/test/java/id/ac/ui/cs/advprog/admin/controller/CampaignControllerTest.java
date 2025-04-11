package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CampaignControllerTest {

    private MockMvc mockMvc;
    private CampaignService campaignService;
    private CampaignDTO campaignDTO;

    @BeforeEach
    void setUp() {
        campaignService = mock(CampaignService.class);
        CampaignController controller = new CampaignController(campaignService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        campaignDTO = new CampaignDTO(
                1L,
                "Campaign Name",
                "Deskripsi",
                1000.0,
                500.0,
                LocalDate.now(),
                LocalDate.now().plusDays(30),
                CampaignStatus.ACTIVE
        );
    }

    @Test
    void testGetCampaignById() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1L);

        when(campaignService.getCampaignById(1L)).thenReturn(campaign);
        when(campaignService.mapToDTO(campaign)).thenReturn(campaignDTO);

        mockMvc.perform(get("/admin/campaigns/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetAllCampaigns() throws Exception {
        CampaignDTO dto2 = new CampaignDTO(
                2L,
                "Another Campaign",
                "Deskripsi 2",
                2000.0,
                1500.0,
                LocalDate.now(),
                LocalDate.now().plusDays(60),
                CampaignStatus.ACTIVE
        );

        when(campaignService.getAllCampaignDTOs()).thenReturn(List.of(campaignDTO, dto2));

        mockMvc.perform(get("/admin/campaigns"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetCampaignsByStatus() throws Exception {
        Campaign campaign = new Campaign();

        when(campaignService.getCampaignsByStatus(CampaignStatus.ACTIVE)).thenReturn(List.of(campaign));
        when(campaignService.mapToDTO(campaign)).thenReturn(campaignDTO);

        mockMvc.perform(get("/admin/campaigns/status/ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testUpdateCampaignStatus() throws Exception {
        Campaign campaign = new Campaign();

        when(campaignService.updateCampaignStatus(1L, CampaignStatus.COMPLETED)).thenReturn(campaign);
        when(campaignService.mapToDTO(campaign)).thenReturn(campaignDTO);

        mockMvc.perform(put("/admin/campaigns/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk());
    }

    @Test
    void testVerifyCampaign() throws Exception {
        Campaign campaign = new Campaign();

        when(campaignService.verifyCampaign(1L, true)).thenReturn(campaign);
        when(campaignService.mapToDTO(campaign)).thenReturn(campaignDTO);

        mockMvc.perform(post("/admin/campaigns/1/verify")
                        .param("approve", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFundUsageProofs() throws Exception {
        FundUsageProof proof = new FundUsageProof();

        when(campaignService.getFundUsageProofs(1L)).thenReturn(List.of(proof));

        mockMvc.perform(get("/admin/campaigns/1/fund-usage-proofs"))
                .andExpect(status().isOk());
    }
}
