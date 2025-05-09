package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllCampaigns() throws Exception {
        mockMvc.perform(get("/admin/campaigns"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCampaignsByStatus() throws Exception {
        mockMvc.perform(get("/admin/campaigns/campaignprogressstatus/ACTIVE"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetFundUsageProofs() throws Exception {
        String campaignId = "7e8725e7-c9d8-4176-a392-4c3897042989";  // UUID valid from dummy data

        mockMvc.perform(get("/admin/campaigns/" + campaignId + "/fund-usage-proofs"))
                .andExpect(status().isOk());
    }
}
