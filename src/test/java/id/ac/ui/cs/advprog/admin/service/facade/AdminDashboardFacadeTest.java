package id.ac.ui.cs.advprog.admin.service.facade;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.DonationHistoryService;
import id.ac.ui.cs.advprog.admin.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

class AdminDashboardFacadeTest {

    @Mock
    private CampaignService campaignService;

    @Mock
    private DonationHistoryService donationHistoryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminDashboardFacade adminDashboardFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardStats() {
        when(campaignService.countCampaigns()).thenReturn(10L);
        when(campaignService.countCampaignsByStatus(Status.PENDING)).thenReturn(2L);
        when(campaignService.countCampaignsByStatus(Status.ACTIVE)).thenReturn(5L);
        when(campaignService.countCampaignsByStatus(Status.INACTIVE)).thenReturn(3L);

        when(userService.countAllUsers()).thenReturn(100);

        List<DonationHistoryDTO> dummyDonations = List.of(
                mock(DonationHistoryDTO.class),
                mock(DonationHistoryDTO.class),
                mock(DonationHistoryDTO.class)
        );
        when(donationHistoryService.getAllDonationHistories()).thenReturn(dummyDonations);

        AdminDashboardStatsDTO stats = adminDashboardFacade.getDashboardStats();
        assertEquals(10L, stats.getTotalCampaigns());
        assertEquals(2L, stats.getUpcomingCampaigns());
        assertEquals(5L, stats.getActiveCampaigns());
        assertEquals(3L, stats.getCompletedCampaigns());
        assertEquals(100, stats.getTotalUsers());
        assertEquals(3L, stats.getTotalDonations());
    }
}
