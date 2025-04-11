package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DonationTest {

    Donation donation;

    @BeforeEach
    void setUp() {
        this.donation = new Donation();
        this.donation.setId(1L);
        this.donation.setCampaign(new Campaign());
        this.donation.getCampaign().setId(3L);
        this.donation.setDonatur(new User());
        this.donation.setAmount(250000.0);
        this.donation.setMessage("Keep up the good work!");
        this.donation.setAnonymous(true);
        this.donation.setTransactionId("TX123456");

        User user = new User();
        user.setId(2L);
        user.setUsername("donaturUser");
        user.setEmail("donatur@example.com");
        user.setPassword("password");
        user.setRole(UserRole.DONATUR);
        this.donation.setDonatur(user);

        Campaign campaign = new Campaign();
        campaign.setId(3L);
        campaign.setTitle("Campaign Title");
        campaign.setDescription("A great cause");
        donation.setCampaign(campaign);

        donation.setDonationDate(LocalDateTime.of(2025, 4, 11, 12, 0));
    }

    @Test
    void testGetId() {
        assertEquals(1L, donation.getId());
    }

    @Test
    void testGetAmount() {
        assertEquals(250000.0, donation.getAmount());
    }

    @Test
    void testGetMessage() {
        assertEquals("Keep up the good work!", donation.getMessage());
    }

    @Test
    void testIsAnonymous() {
        assertTrue(donation.isAnonymous());
    }

    @Test
    void testGetTransactionId() {
        assertEquals("TX123456", donation.getTransactionId());
    }

    @Test
    void testGetDonationDate() {
        assertEquals(LocalDateTime.of(2025, 4, 11, 12, 0), donation.getDonationDate());
    }

    @Test
    void testGetDonatur() {
        assertEquals(2L, donation.getDonatur().getId());
    }

    @Test
    void testGetCampaign() {
        assertEquals(3L, donation.getCampaign().getId());
    }
    
    @Test
    void testGetDonationDateSetOnCreate() {
        Donation donation = new Donation();
        donation.onCreate();

        LocalDateTime now = LocalDateTime.now();

        assertTrue(donation.getDonationDate().isBefore(now.plusSeconds(1)) &&
                donation.getDonationDate().isAfter(now.minusSeconds(1)));
    }

}
