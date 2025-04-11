package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CampaignTest {

    Campaign campaign;

    @BeforeEach
    void setUp() {
        this.campaign = new Campaign();
        this.campaign.setId(1L);
        this.campaign.setTitle("Test Campaign");
        this.campaign.setDescription("This is a test campaign.");
        this.campaign.setTargetAmount(1000.0);
        this.campaign.setCurrentAmount(500.0);
        this.campaign.setStartDate(LocalDate.now());
        this.campaign.setEndDate(LocalDate.now().plusDays(30));
        this.campaign.setStatus(CampaignStatus.ACTIVE);

        User user = new User(); 
        user.setId(2L);
        user.setUsername("testuser");
        user.setEmail("tV2tH@example.com");
        user.setPassword("password");   
        user.setRole(UserRole.FUNDRAISER);
        this.campaign.setFundraiser(user); // Assuming User class has a default constructor
        
        Donation donation = new Donation();
        donation.setId(3L);
        donation.setAmount(100000.0);
        donation.setCampaign(this.campaign);
        
        List<Donation> donations = new ArrayList<>();
        donations.add(donation);
        this.campaign.setDonations(donations);

        FundUsageProof fundUsageProof = new FundUsageProof();
        fundUsageProof.setTitle("Fund usage proof title");
        fundUsageProof.setDescription("Fund usage proof description");
        fundUsageProof.setCampaign(this.campaign);

        List<FundUsageProof> fundUsageProofs = new ArrayList<>();
        fundUsageProofs.add(fundUsageProof);
        this.campaign.setFundUsageProofs(fundUsageProofs);
    }
    
    @Test
    void testGetId() {
        assertEquals(1L, this.campaign.getId());
    }

    @Test
    void testGetTitle() {
        assertEquals("Test Campaign", this.campaign.getTitle());
    }

    @Test
    void testGetCurrentAmount() {
        assertEquals(500.0, this.campaign.getCurrentAmount());
    }

    @Test
    void testGetStartDate() {
        assertEquals(LocalDate.now(), this.campaign.getStartDate());
    }

    @Test
    void testGetEndDate() {
        assertEquals(LocalDate.now().plusDays(30), this.campaign.getEndDate());
    }

    @Test
    void testGetStatus() {
        assertEquals(CampaignStatus.ACTIVE, this.campaign.getStatus());
    }

    @Test
    void testGetDonations() {
        assertNotNull(this.campaign.getDonations());
    }

    @Test
    void testGetFundraiser() {
        assertEquals(2L, this.campaign.getFundraiser().getId());
    }
}
