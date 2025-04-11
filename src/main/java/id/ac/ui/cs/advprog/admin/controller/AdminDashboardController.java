package id.ac.ui.cs.advprog.admin.controller;

import id.ac.ui.cs.advprog.admin.dto.AdminDashboardStatsDTO;
import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.service.CampaignService;
import id.ac.ui.cs.advprog.admin.service.UserService;
import id.ac.ui.cs.advprog.admin.service.DonationService;
import id.ac.ui.cs.advprog.admin.service.facade.AdminDashboardFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardFacade dashboardFacade;
    private final CampaignService campaignService;
    private final UserService userService;
    private final DonationService donationService;

    @Autowired
    public AdminDashboardController(AdminDashboardFacade dashboardFacade,
                                    CampaignService campaignService,
                                    UserService userService,
                                    DonationService donationService) {
        this.dashboardFacade = dashboardFacade;
        this.campaignService = campaignService;
        this.userService = userService;
        this.donationService = donationService;
    }

    // ✅ Endpoint statistik ringkasan via FACADE
    @GetMapping("/stats")
    public AdminDashboardStatsDTO getDashboardStats() {
        return dashboardFacade.getDashboardStatistics();
    }

    // Endpoint detail kampanye berdasarkan ID
    @GetMapping("/campaign/{id}")
    public CampaignDTO getCampaignById(@PathVariable Long id) {
        Campaign campaign = campaignService.getCampaignById(id);
        return campaignService.mapToDTO(campaign);
    }

    // Endpoint melihat semua pengguna
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Endpoint memblokir pengguna
    @PutMapping("/users/{id}/block")
    public User blockUser(@PathVariable Long id) {
        return userService.blockUser(id);
    }

    // Endpoint menghapus pengguna
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // Endpoint daftar donasi (riwayat)
    @GetMapping("/donations")
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }
}
