package id.ac.ui.cs.advprog.admin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FundUsageProofTest {

    private FundUsageProof proof;

    @BeforeEach
    void setUp() {
        proof = new FundUsageProof();
        proof.setId(100L);
        proof.setCampaignId(100L);
        proof.setTitle("Laporan Pembelian Sembako");
        proof.setDescription("Penggunaan dana untuk membeli sembako bagi warga");
        proof.setAmount(500_000.0);
        LocalDateTime now = LocalDateTime.now();
        proof.setSubmittedAt(now);
    }

    @Test
    void testFundUsageProofFields() {
        assertEquals(100L, proof.getId());
        assertEquals(100L, proof.getCampaignId());
        assertEquals("Laporan Pembelian Sembako", proof.getTitle());
        assertEquals("Penggunaan dana untuk membeli sembako bagi warga", proof.getDescription());
        assertEquals(500_000.0, proof.getAmount());
        assertNotNull(proof.getSubmittedAt());
    }

    @Test
    void testGetDonationDateSetOnCreate() {
        FundUsageProof fund = new FundUsageProof();
        fund.onCreate();

        LocalDateTime now = LocalDateTime.now();

        assertTrue(fund.getSubmittedAt().isBefore(now.plusSeconds(1)) &&
                fund.getSubmittedAt().isAfter(now.minusSeconds(1)));
    }
}
