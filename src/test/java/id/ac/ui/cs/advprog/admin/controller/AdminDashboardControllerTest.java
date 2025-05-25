package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.service.facade.AdminDashboardFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AdminDashboardControllerTest {

    private MockMvc mockMvc;
    private AdminDashboardFacade adminDashboardFacade;

    @BeforeEach
    void setUp() {
        adminDashboardFacade = mock(AdminDashboardFacade.class);
        AdminDashboardController controller = new AdminDashboardController(adminDashboardFacade);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetDashboardStats() throws Exception {
        AdminDashboardStatsDTO dummyStats = new AdminDashboardStatsDTO();
        dummyStats.setTotalUsers(10);
        dummyStats.setTotalCampaigns(3);
        dummyStats.setTotalDonations(50000.0);

        when(adminDashboardFacade.getDashboardStats()).thenReturn(dummyStats);

        mockMvc.perform(get("/admin/dashboard/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(10))
                .andExpect(jsonPath("$.totalCampaigns").value(3))
                .andExpect(jsonPath("$.totalDonations").value(50000.0));
    }
}
