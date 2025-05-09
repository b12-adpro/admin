package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

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
        // Menggunakan UUID yang valid untuk mock data
        UUID donationId1 = UUID.fromString("a8a79f87-4cb1-4bb2-8c13-01a344d98f23");
        UUID donationId2 = UUID.fromString("f0b1f7f3-7e52-4063-bf6f-06d498b9a9b2");

        // Mendapatkan semua donation histories
        List<DonationHistoryDTO> donationHistories = donationHistoryService.getAllDonationHistories();

        assertNotNull(donationHistories);
        assertEquals(2, donationHistories.size());

        // Verifikasi donation pertama
        DonationHistoryDTO firstDonation = donationHistories.get(0);
        assertEquals(donationId1, firstDonation.getId());
        assertEquals("Kampanye A", firstDonation.getCampaignTitle());
        assertEquals(100000.0, firstDonation.getAmount());
        assertNotNull(firstDonation.getDonatedAt());

        // Verifikasi donation kedua
        DonationHistoryDTO secondDonation = donationHistories.get(1);
        assertEquals(donationId2, secondDonation.getId());
        assertEquals("Kampanye C", secondDonation.getCampaignTitle());
        assertEquals(250000.0, secondDonation.getAmount());
        assertNotNull(secondDonation.getDonatedAt());
    }
}
