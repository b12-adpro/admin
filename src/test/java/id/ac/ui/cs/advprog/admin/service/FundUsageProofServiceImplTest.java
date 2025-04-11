package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import id.ac.ui.cs.advprog.admin.repository.FundUsageProofRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FundUsageProofServiceImplTest {

    @Mock
    private FundUsageProofRepository fundUsageProofRepository;

    @InjectMocks
    private FundUsageProofServiceImpl fundUsageProofService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should count proofs by status")
    void testCountProofsByStatus() {
        ProofStatus status = ProofStatus.VERIFIED;
        when(fundUsageProofRepository.countByStatus(status)).thenReturn(5L);

        long count = fundUsageProofService.countProofsByStatus(status);

        assertEquals(5L, count);
        verify(fundUsageProofRepository, times(1)).countByStatus(status);
    }
}
