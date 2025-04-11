package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.DonationService;
import id.ac.ui.cs.advprog.admin.service.UserService;
import id.ac.ui.cs.advprog.admin.service.facade.AdminDashboardFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminDashboardControllerTest {

    private MockMvc mockMvc;
    private AdminDashboardFacade dashboardFacade;
    private CampaignService campaignService;
    private UserService userService;
    private DonationService donationService;

    @BeforeEach
    void setUp() {
        dashboardFacade = mock(AdminDashboardFacade.class);
        campaignService = mock(CampaignService.class);
        userService = mock(UserService.class);
        donationService = mock(DonationService.class);
        AdminDashboardController controller = new AdminDashboardController(
                dashboardFacade, campaignService, userService, donationService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testGetDashboardStats() throws Exception {
        AdminDashboardStatsDTO statsDTO = new AdminDashboardStatsDTO(
                10, 3, 5, 2, 100, 40, 60, 5000000.0, 4
        );

        when(dashboardFacade.getDashboardStatistics()).thenReturn(statsDTO);

        mockMvc.perform(get("/admin/dashboard/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCampaigns").value(10))
                .andExpect(jsonPath("$.pendingCampaigns").value(3))
                .andExpect(jsonPath("$.activeCampaigns").value(5))
                .andExpect(jsonPath("$.completedCampaigns").value(2))
                .andExpect(jsonPath("$.totalUsers").value(100))
                .andExpect(jsonPath("$.totalFundraisers").value(40))
                .andExpect(jsonPath("$.totalDonaturs").value(60))
                .andExpect(jsonPath("$.totalDonations").value(5000000.0))
                .andExpect(jsonPath("$.pendingProofs").value(4));
    }

    @Test
    void testGetCampaignById() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        CampaignDTO dto = new CampaignDTO(1L, "Judul", "Deskripsi", 100000.0, 50000.0, null, null, null);

        when(campaignService.getCampaignById(1L)).thenReturn(campaign);
        when(campaignService.mapToDTO(campaign)).thenReturn(dto);

        mockMvc.perform(get("/admin/dashboard/campaign/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/admin/dashboard/users"))
                .andExpect(status().isOk());
    }

    @Test
    void testBlockUser() throws Exception {
        User user = new User();
        user.setId(1L);
        when(userService.blockUser(1L)).thenReturn(user);

        mockMvc.perform(put("/admin/dashboard/users/1/block"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);

        mockMvc.perform(delete("/admin/dashboard/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllDonations() throws Exception {
        Donation donation = new Donation();
        donation.setId(1L);
        when(donationService.getAllDonations()).thenReturn(List.of(donation));

        mockMvc.perform(get("/admin/dashboard/donations"))
                .andExpect(status().isOk());
    }
}
