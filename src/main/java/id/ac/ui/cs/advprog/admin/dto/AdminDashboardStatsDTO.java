package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardStatsDTO {
    private long totalCampaigns;
    private long upcomingCampaigns;
    private long activeCampaigns;
    private long completedCampaigns;
    private int totalUsers;
    private double totalDonations;
}
