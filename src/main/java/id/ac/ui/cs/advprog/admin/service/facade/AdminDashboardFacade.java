package id.ac.ui.cs.advprog.admin.service.facade;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.DonationHistoryService;
import id.ac.ui.cs.advprog.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardFacade {

    private final CampaignService campaignService;
    private final DonationHistoryService donationHistoryService;
    private final UserService userService;

    public AdminDashboardStatsDTO getDashboardStats() {
        long totalCampaigns = campaignService.countCampaigns();
        long upcomingCampaigns = campaignService.countCampaignsByStatus(Status.PENDING);
        System.out.println("Upcoming Campaigns: " + upcomingCampaigns);
        long activeCampaigns = campaignService.countCampaignsByStatus(Status.ACTIVE);
        System.out.println("Active Campaigns: " + activeCampaigns);
        long completedCampaigns = campaignService.countCampaignsByStatus(Status.INACTIVE);
        System.out.println("Completed Campaigns: " + completedCampaigns);

        int totalUsers = userService.countAllUsers();

        long totalDonations = donationHistoryService.getAllDonationHistories().size();

        return new AdminDashboardStatsDTO(
                totalCampaigns,        // totalCampaigns
                upcomingCampaigns,        // upcomingCampaigns
                activeCampaigns,        // activeCampaigns
                completedCampaigns,        // completedCampaigns
                totalUsers,        // totalUsers
                totalDonations     // totalDonations
        );
    }

}
