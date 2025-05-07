package id.ac.ui.cs.advprog.admin.service.facade;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignProgressStatus;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
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
        long pendingCampaigns = campaignService.countCampaignsByStatus(CampaignProgressStatus.PENDING);
        long activeCampaigns = campaignService.countCampaignsByStatus(CampaignProgressStatus.ACTIVE);
        long completedCampaigns = campaignService.countCampaignsByStatus(CampaignProgressStatus.COMPLETED);

        int totalUsers = userService.countAllUsers();
        int totalFundraisers = userService.countUsersByRole(UserRole.FUNDRAISER);
        int totalDonatur = userService.countUsersByRole(UserRole.DONATUR);

        long totalDonations = donationHistoryService.getAllDonationHistories().size();

        //TODO: totalCampaigns, pendingCampaigns, activeCampaigns, completedCampaigns, totalUsers, totalFundraisers, totalDonatur, totalDonations, pendingProofs

        return new AdminDashboardStatsDTO(
                totalCampaigns,        // totalCampaigns
                pendingCampaigns,        // pendingCampaigns
                activeCampaigns,        // activeCampaigns
                completedCampaigns,        // completedCampaigns
                totalUsers,        // totalUsers
                totalFundraisers,        // totalFundraisers
                totalDonatur,        // totalDonaturs
                totalDonations     // totalDonations
        );
    }

}
