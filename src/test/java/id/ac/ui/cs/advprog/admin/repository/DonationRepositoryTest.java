package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class DonationRepositoryTest {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Test
    @DisplayName("Should save and retrieve donations by campaign and donatur")
    void testFindByCampaignAndDonatur() {
        User donor = new User();
        donor.setUsername("donor1");
        donor.setEmail("donor1@example.com");
        donor.setPassword("pass");
        donor.setRole(UserRole.DONATUR);
        donor = userRepository.save(donor); // ✅

        User fundraiser = new User();
        fundraiser.setUsername("fundraiser1");
        fundraiser.setEmail("fundraiser@example.com");
        fundraiser.setPassword("pass");
        fundraiser.setRole(UserRole.FUNDRAISER);
        fundraiser = userRepository.save(fundraiser); // ✅

        Campaign campaign = new Campaign();
        campaign.setTitle("Donasi Pendidikan");
        campaign.setDescription("Deskripsi...");
        campaign.setTargetAmount(100000.0);
        campaign.setCurrentAmount(0.0);
        campaign.setStartDate(LocalDate.now());
        campaign.setEndDate(LocalDate.now().plusDays(30));
        campaign.setStatus(id.ac.ui.cs.advprog.admin.enums.CampaignStatus.ACTIVE);
        campaign.setFundraiser(fundraiser);
        campaign = campaignRepository.save(campaign); // ✅

        Donation donation = new Donation();
        donation.setDonatur(donor);
        donation.setCampaign(campaign);
        donation.setAmount(50000.0);
        donation.setDonationDate(LocalDateTime.now());
        donationRepository.save(donation); // ✅

        List<Donation> byCampaign = donationRepository.findByCampaign(campaign);
        List<Donation> byDonatur = donationRepository.findByDonatur(donor);

        assertEquals(1, byCampaign.size());
        assertEquals(1, byDonatur.size());
        assertEquals(50000.0, byCampaign.get(0).getAmount());
    }

    @Test
    @DisplayName("Should calculate total and per-campaign donation amount")
    void testSumDonationAmounts() {
        User donor = new User();
        donor.setUsername("donor2");
        donor.setEmail("donor2@example.com");
        donor.setPassword("pass");
        donor.setRole(UserRole.DONATUR);
        donor = userRepository.save(donor); // ✅

        User fundraiser = new User();
        fundraiser.setUsername("fundraiser2");
        fundraiser.setEmail("fundraiser2@example.com");
        fundraiser.setPassword("pass");
        fundraiser.setRole(UserRole.FUNDRAISER);
        fundraiser = userRepository.save(fundraiser); // ✅

        Campaign campaign = new Campaign();
        campaign.setTitle("Kesehatan");
        campaign.setDescription("Deskripsi...");
        campaign.setTargetAmount(200000.0);
        campaign.setCurrentAmount(0.0);
        campaign.setStartDate(LocalDate.now());
        campaign.setEndDate(LocalDate.now().plusDays(30));
        campaign.setStatus(id.ac.ui.cs.advprog.admin.enums.CampaignStatus.ACTIVE);
        campaign.setFundraiser(fundraiser);
        campaign = campaignRepository.save(campaign); // ✅

        Donation donation1 = new Donation();
        donation1.setDonatur(donor);
        donation1.setCampaign(campaign);
        donation1.setAmount(10000.0);
        donation1.setDonationDate(LocalDateTime.now());

        Donation donation2 = new Donation();
        donation2.setDonatur(donor);
        donation2.setCampaign(campaign);
        donation2.setAmount(20000.0);
        donation2.setDonationDate(LocalDateTime.now());

        donationRepository.save(donation1);
        donationRepository.save(donation2);

        Double total = donationRepository.sumTotalAmount();
        Double campaignTotal = donationRepository.sumDonationAmountByCampaignId(campaign.getId());

        assertNotNull(total);
        assertEquals(30000.0, campaignTotal);
        assertEquals(30000.0, total);
    }
}
