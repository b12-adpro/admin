package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FundUsageProofRepositoryTest {

    @Autowired
    private FundUsageProofRepository fundUsageProofRepository;

    @BeforeEach
    void setUp() {
        UUID campaignId1 = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989");
        UUID campaignId2 = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042990");

        FundUsageProof proof1 = new FundUsageProof();
        proof1.setCampaignId(campaignId1);
        proof1.setTitle("Bukti 1");
        proof1.setDescription("Deskripsi 1");
        proof1.setAmount(100000.0);
        proof1.setSubmittedAt(LocalDateTime.now());

        FundUsageProof proof2 = new FundUsageProof();
        proof2.setCampaignId(campaignId2);
        proof2.setTitle("Bukti 2");
        proof2.setDescription("Deskripsi 2");
        proof2.setAmount(200000.0);
        proof2.setSubmittedAt(LocalDateTime.now());

        fundUsageProofRepository.save(proof1);
        fundUsageProofRepository.save(proof2);
    }

    @Test
    void testFindByCampaignId() {
        UUID campaignId1 = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989");

        List<FundUsageProof> result = fundUsageProofRepository.findByCampaignId(campaignId1);

        assertEquals(1, result.size());
        assertEquals(campaignId1, result.get(0).getCampaignId());
        assertEquals("Bukti 1", result.get(0).getTitle());
    }
}
