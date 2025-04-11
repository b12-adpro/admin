package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FundUsageProofRepositoryTest {

    @Autowired
    private FundUsageProofRepository fundUsageProofRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private UserRepository userRepository;

    private Campaign campaign;
    private User fundraiser;

    @BeforeEach
    void setUp() {
        // Setup dummy fundraiser
        fundraiser = new User();
        fundraiser.setUsername("fundraiser1");
        fundraiser.setEmail("fundraiser@example.com");
        fundraiser.setRole(UserRole.FUNDRAISER);
        userRepository.save(fundraiser);

        // Setup dummy campaign
        campaign = new Campaign();
        campaign.setTitle("Test Campaign");
        campaign.setDescription("Test Description");
        campaign.setTargetAmount(10000.0);
        campaign.setCurrentAmount(1000.0);
        campaign.setStartDate(LocalDate.now());
        campaign.setEndDate(LocalDate.now().plusDays(30));
        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setFundraiser(fundraiser);
        campaignRepository.save(campaign);
    }

    @Test
    void testFindByCampaign() {
        FundUsageProof proof = new FundUsageProof();
        proof.setCampaign(campaign);
        proof.setStatus(ProofStatus.PENDING_VERIFICATION);
        fundUsageProofRepository.save(proof);

        List<FundUsageProof> results = fundUsageProofRepository.findByCampaign(campaign);

        assertEquals(1, results.size());
        assertEquals(ProofStatus.PENDING_VERIFICATION, results.get(0).getStatus());
    }

    @Test
    void testFindByStatus() {
        FundUsageProof proof = new FundUsageProof();
        proof.setCampaign(campaign);
        proof.setStatus(ProofStatus.VERIFIED);
        fundUsageProofRepository.save(proof);

        List<FundUsageProof> results = fundUsageProofRepository.findByStatus(ProofStatus.VERIFIED);

        assertEquals(1, results.size());
        assertEquals(campaign.getId(), results.get(0).getCampaign().getId());
    }

    @Test
    void testCountByStatus() {
        FundUsageProof proof1 = new FundUsageProof();
        proof1.setCampaign(campaign);
        proof1.setStatus(ProofStatus.REJECTED);
        fundUsageProofRepository.save(proof1);

        FundUsageProof proof2 = new FundUsageProof();
        proof2.setCampaign(campaign);
        proof2.setStatus(ProofStatus.REJECTED);
        fundUsageProofRepository.save(proof2);

        long count = fundUsageProofRepository.countByStatus(ProofStatus.REJECTED);
        assertEquals(2, count);
    }
}
