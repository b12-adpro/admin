package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.repository.FundUsageProofRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FundUsageProofServiceImplTest {

    @InjectMocks
    private FundUsageProofServiceImpl fundUsageProofService;

    @Mock
    private FundUsageProofRepository fundUsageProofRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all fund usage proofs")
    void testGetAllProofs() {
        // Data dummy untuk testing
        FundUsageProof proof1 = new FundUsageProof();
        proof1.setId(1L);
        proof1.setCampaignId(10L);
        proof1.setTitle("Laporan 1");
        proof1.setDescription("Deskripsi 1");
        proof1.setAmount(100000.0);
        proof1.setSubmittedAt(LocalDateTime.of(2025, 4, 1, 10, 0));

        FundUsageProof proof2 = new FundUsageProof();
        proof2.setId(2L);
        proof2.setCampaignId(10L);
        proof2.setTitle("Laporan 2");
        proof2.setDescription("Deskripsi 2");
        proof2.setAmount(200000.0);
        proof2.setSubmittedAt(LocalDateTime.of(2025, 4, 2, 14, 30));

        // Mocking repository
        when(fundUsageProofRepository.findAll()).thenReturn(List.of(proof1, proof2));

        // Menguji service
        List<FundUsageProofDTO> result = fundUsageProofService.getAllProofsDTO();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laporan 1", result.get(0).getTitle());
        assertEquals(200000.0, result.get(1).getAmount());
    }

    @Test
    @DisplayName("Should return fund usage proofs by campaign ID")
    void testGetProofsByCampaign() {
        Long campaignId = 5L;
        FundUsageProof proof = new FundUsageProof();
        proof.setId(3L);
        proof.setCampaignId(campaignId);
        proof.setTitle("Bukti Penggunaan Dana");
        proof.setDescription("Dana dipakai untuk logistik");
        proof.setAmount(50000.0);
        proof.setSubmittedAt(LocalDateTime.of(2025, 4, 3, 9, 0));

        // Mocking repository
        when(fundUsageProofRepository.findByCampaignId(campaignId)).thenReturn(List.of(proof));

        // Menguji service
        List<FundUsageProofDTO> result = fundUsageProofService.getProofsByCampaignDTO(campaignId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bukti Penggunaan Dana", result.get(0).getTitle());
        assertEquals(campaignId, result.get(0).getCampaignId());
    }
}
