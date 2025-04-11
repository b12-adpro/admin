package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CampaignRepositoryTest {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should save and retrieve campaign successfully")
    void testFindById() {
        User fundraiser = new User();
        fundraiser.setUsername("johnDoe");
        fundraiser.setEmail("john@example.com");
        fundraiser.setPassword("password");
        fundraiser.setRole(UserRole.FUNDRAISER);
        fundraiser = userRepository.save(fundraiser);

        Campaign campaign = new Campaign();
        campaign.setTitle("Bantu Anak Yatim");
        campaign.setDescription("Donasi untuk pendidikan anak yatim");
        campaign.setFundraiser(fundraiser);
        campaign.setTargetAmount(5000000.0);
        campaign.setCurrentAmount(1000000.0);
        campaign.setStatus(CampaignStatus.PENDING);
        campaign.setStartDate(LocalDate.now());
        campaign.setEndDate(LocalDate.now().plusDays(30));
        campaign = campaignRepository.save(campaign);

        Optional<Campaign> result = campaignRepository.findById(campaign.getId());

        assertTrue(result.isPresent());
        assertEquals("Bantu Anak Yatim", result.get().getTitle());
        assertEquals(CampaignStatus.PENDING, result.get().getStatus());
    }

    @Test
    @DisplayName("Should return campaigns by status")
    void testFindByStatus() {
        User fundraiser = new User();
        fundraiser.setUsername("janeDoe");
        fundraiser.setEmail("jane@example.com");
        fundraiser.setPassword("password");
        fundraiser.setRole(UserRole.FUNDRAISER);
        fundraiser = userRepository.save(fundraiser);

        Campaign campaign1 = new Campaign();
        campaign1.setTitle("Bantu Sekolah");
        campaign1.setDescription("Donasi sekolah");
        campaign1.setStatus(CampaignStatus.PENDING);
        campaign1.setFundraiser(fundraiser);
        campaign1.setTargetAmount(1000000.0);
        campaign1.setCurrentAmount(0.0);
        campaign1.setStartDate(LocalDate.now());
        campaign1.setEndDate(LocalDate.now().plusDays(30));
        campaignRepository.save(campaign1); // ✅

        Campaign campaign2 = new Campaign();
        campaign2.setTitle("Bantu Medis");
        campaign2.setDescription("Donasi kesehatan");
        campaign2.setStatus(CampaignStatus.ACTIVE);
        campaign2.setFundraiser(fundraiser);
        campaign2.setTargetAmount(2000000.0);
        campaign2.setCurrentAmount(0.0);
        campaign2.setStartDate(LocalDate.now());
        campaign2.setEndDate(LocalDate.now().plusDays(30));
        campaignRepository.save(campaign2); // ✅

        List<Campaign> pendingCampaigns = campaignRepository.findByStatus(CampaignStatus.PENDING);

        assertEquals(1, pendingCampaigns.size());
        assertEquals("Bantu Sekolah", pendingCampaigns.get(0).getTitle());
    }
}
