package id.ac.ui.cs.advprog.admin.service.facade;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.DonationService;
import id.ac.ui.cs.advprog.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import id.ac.ui.cs.advprog.admin.service.FundUsageProofService;

@Component
public class AdminDashboardFacade {

    private final CampaignService campaignService;
    private final DonationService donationService;
    private final UserService userService;
    private final FundUsageProofService fundUsageProofService;

    @Autowired
    public AdminDashboardFacade(CampaignService campaignService,
                                DonationService donationService,
                                UserService userService, FundUsageProofService fundUsageProofService) {
        this.campaignService = campaignService;
        this.donationService = donationService;
        this.userService = userService;
        this.fundUsageProofService = fundUsageProofService;
    }

    public AdminDashboardStatsDTO getDashboardStatistics() {
        int totalCampaigns = campaignService.countAllCampaigns();
        long pendingCampaigns = campaignService.countCampaignsByStatus(CampaignStatus.PENDING);
        long activeCampaigns = campaignService.countCampaignsByStatus(CampaignStatus.ACTIVE);
        long completedCampaigns = campaignService.countCampaignsByStatus(CampaignStatus.COMPLETED);

        int totalUsers = userService.countAllUsers();
        int totalFundraisers = userService.countUsersByRole(UserRole.FUNDRAISER);
        int totalDonatur = userService.countUsersByRole(UserRole.DONATUR);

        long totalDonations = donationService.getAllDonations().size();
        long pendingProofs = fundUsageProofService.countProofsByStatus(ProofStatus.VERIFIED);

        return new AdminDashboardStatsDTO(
                totalCampaigns,
                pendingCampaigns,
                activeCampaigns,
                completedCampaigns,
                totalUsers,
                totalFundraisers,
                totalDonatur,
                totalDonations,
                pendingProofs
        );
    }

}
