package id.ac.ui.cs.advprog.admin.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CampaignControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CampaignService campaignService;

    private CampaignController campaignController;

    @BeforeEach
    void setup() {
        campaignController = new CampaignController(campaignService);
        mockMvc = MockMvcBuilders.standaloneSetup(campaignController).build();
    }

    private CampaignDTO createDummyCampaignDTO() {
        return new CampaignDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Fundraiser Test",
                "Judul Campaign",
                1000000,
                500000,
                LocalDateTime.now(),
                "ACTIVE",
                "Deskripsi campaign",
                "bukti.jpg"
        );
    }

    @Test
    void testGetAllCampaigns() throws Exception {
        when(campaignService.getAllCampaigns())
                .thenReturn(List.of(createDummyCampaignDTO()));

        mockMvc.perform(get("/admin/campaigns"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCampaignsByStatus() throws Exception {
        when(campaignService.getCampaignsByCampaignProgressStatus(Status.ACTIVE))
                .thenReturn(List.of(createDummyCampaignDTO()));

        mockMvc.perform(get("/admin/campaigns/campaignprogressstatus/ACTIVE"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFundUsageProofs() throws Exception {
        UUID dummyId = UUID.randomUUID();
        when(campaignService.getFundUsageProofsByCampaignId(dummyId))
                .thenReturn("Dummy Proof");

        mockMvc.perform(get("/admin/campaigns/" + dummyId + "/fund-usage-proofs"))
                .andExpect(status().isOk());
    }
}

