package id.ac.ui.cs.advprog.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardStatsDTO {
    private long totalCampaigns;
    private long pendingCampaigns;
    private long activeCampaigns;
    private long completedCampaigns;
    private int totalUsers;
    private int totalFundraisers;
    private int totalDonaturs;
    private double totalDonations;
}
