package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        mockMvc.perform(get("/admin/campaigns/status/ACTIVE"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCampaignById() throws Exception {
        mockMvc.perform(get("/admin/campaigns/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("Kampanye A"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }


    @Test
    void testUpdateCampaignStatus() throws Exception {
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .put("/admin/campaigns/1/status")
                        .param("status", "COMPLETED")
        ).andExpect(status().isOk());
    }

    @Test
    void testVerifyCampaignApprove() throws Exception {
        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders
                        .post("/admin/campaigns/1/verify")
                        .param("approve", "true")
        ).andExpect(status().isOk());
    }

    @Test
    void testGetFundUsageProofs() throws Exception {
        mockMvc.perform(get("/admin/campaigns/1/fund-usage-proofs"))
                .andExpect(status().isOk());
    }
}
