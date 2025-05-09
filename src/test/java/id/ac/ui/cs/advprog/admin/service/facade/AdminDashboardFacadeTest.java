package id.ac.ui.cs.advprog.admin.service.facade;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.DonationHistoryService;
import id.ac.ui.cs.advprog.admin.service.FundUsageProofService;
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

    @Mock
    private FundUsageProofService fundUsageProofService;

    @InjectMocks
    private AdminDashboardFacade adminDashboardFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardStats() {
        when(campaignService.countCampaigns()).thenReturn(10L);
        when(campaignService.countCampaignsByStatus(CampaignProgressStatus.UPCOMING)).thenReturn(2L);
        when(campaignService.countCampaignsByStatus(CampaignProgressStatus.ACTIVE)).thenReturn(5L);
        when(campaignService.countCampaignsByStatus(CampaignProgressStatus.COMPLETED)).thenReturn(3L);

        when(userService.countAllUsers()).thenReturn(100);
        when(userService.countUsersByRole(UserRole.FUNDRAISER)).thenReturn(40);
        when(userService.countUsersByRole(UserRole.DONATUR)).thenReturn(60);

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
        assertEquals(40, stats.getTotalFundraisers());
        assertEquals(60, stats.getTotalDonaturs());
        assertEquals(3L, stats.getTotalDonations()); // sesuai jumlah mock
    }
}
