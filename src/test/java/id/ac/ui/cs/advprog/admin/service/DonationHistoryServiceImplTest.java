package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonationHistoryServiceImplTest {

    private DonationHistoryServiceImpl donationHistoryService;

    @BeforeEach
    void setUp() {
        donationHistoryService = new DonationHistoryServiceImpl();
    }

    @Test
    @DisplayName("Should return all donation histories")
    void testGetAllDonationHistories() {
        List<DonationHistoryDTO> donationHistories = donationHistoryService.getAllDonationHistories();

        assertNotNull(donationHistories);
        assertEquals(2, donationHistories.size());

        DonationHistoryDTO firstDonation = donationHistories.get(0);
        DonationHistoryDTO secondDonation = donationHistories.get(1);

        assertEquals(1L, firstDonation.getId());
        assertEquals("Bantuan Pendidikan", firstDonation.getCampaignTitle());
        assertEquals("Andi", firstDonation.getDonorName());
        assertEquals(100000.0, firstDonation.getAmount());
        assertNotNull(firstDonation.getDonatedAt());

        assertEquals(2L, secondDonation.getId());
        assertEquals("Bantu Korban Banjir", secondDonation.getCampaignTitle());
        assertEquals("Siti", secondDonation.getDonorName());
        assertEquals(250000.0, secondDonation.getAmount());
        assertNotNull(secondDonation.getDonatedAt());
    }
}
