package id.ac.ui.cs.advprog.admin.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FundUsageProofControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllProofs() throws Exception {
        mockMvc.perform(get("/admin/fund-usage")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProofsByCampaign() throws Exception {
        mockMvc.perform(get("/admin/fund-usage/campaign/" + "7e8725e7-c9d8-4176-a392-4c3897042989")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
