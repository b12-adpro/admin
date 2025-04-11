package id.ac.ui.cs.advprog.admin.model;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FundUsageProofTest {

    private FundUsageProof proof;
    private Campaign campaign;

    @BeforeEach
    void setUp() {
        campaign = new Campaign();
        campaign.setId(1L);
        campaign.setTitle("Campaign A");

        proof = new FundUsageProof();
        proof.setId(100L);
        proof.setCampaign(campaign);
        proof.setTitle("Laporan Pembelian Sembako");
        proof.setDescription("Penggunaan dana untuk membeli sembako bagi warga");
        proof.setAmount(500_000.0);
        proof.setImageUrl("http://example.com/image.jpg");
        proof.setStatus(ProofStatus.PENDING_VERIFICATION);
        proof.setAdminNotes("Perlu ditinjau ulang jumlahnya");

        LocalDateTime now = LocalDateTime.now();
        proof.setSubmittedAt(now);
        proof.setVerifiedAt(now.plusDays(1));
    }

    @Test
    void testFundUsageProofFields() {
        assertEquals(100L, proof.getId());
        assertEquals(campaign, proof.getCampaign());
        assertEquals("Laporan Pembelian Sembako", proof.getTitle());
        assertEquals("Penggunaan dana untuk membeli sembako bagi warga", proof.getDescription());
        assertEquals(500_000.0, proof.getAmount());
        assertEquals("http://example.com/image.jpg", proof.getImageUrl());
        assertEquals(ProofStatus.PENDING_VERIFICATION, proof.getStatus());
        assertEquals("Perlu ditinjau ulang jumlahnya", proof.getAdminNotes());
        assertNotNull(proof.getSubmittedAt());
        assertNotNull(proof.getVerifiedAt());
    }

    @Test
    void testSettersUpdateValuesCorrectly() {
        proof.setTitle("Update Judul");
        proof.setAmount(750_000.0);
        proof.setStatus(ProofStatus.REJECTED);

        assertEquals("Update Judul", proof.getTitle());
        assertEquals(750_000.0, proof.getAmount());
        assertEquals(ProofStatus.REJECTED, proof.getStatus());
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
