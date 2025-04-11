package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.model.User;
import id.ac.ui.cs.advprog.admin.repository.DonationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DonationServiceImplTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationServiceImpl donationService;

    private Donation donation1, donation2;
    private Campaign campaign;
    private User donatur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        donatur = new User();
        donatur.setUsername("donatur1");

        campaign = new Campaign();
        campaign.setTitle("Test Campaign");

        donation1 = new Donation();
        donation1.setId(1L);
        donation1.setAmount(100.0);
        donation1.setCampaign(campaign);
        donation1.setDonatur(donatur);
        donation1.setDonationDate(LocalDateTime.now());

        donation2 = new Donation();
        donation2.setId(2L);
        donation2.setAmount(200.0);
        donation2.setCampaign(campaign);
        donation2.setDonatur(donatur);
        donation2.setDonationDate(LocalDateTime.now());
    }

    @Test
    @DisplayName("Should return all donations")
    void testGetAllDonations() {
        when(donationRepository.findAll()).thenReturn(Arrays.asList(donation1, donation2));

        List<Donation> donations = donationService.getAllDonations();

        assertEquals(2, donations.size());
        verify(donationRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return donation by id")
    void testGetDonationById() {
        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation1));

        Donation result = donationService.getDonationById(1L);

        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
        verify(donationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return donations by campaign id")
    void testGetDonationsByCampaignId() {
        when(donationRepository.findByCampaignId(anyLong())).thenReturn(Arrays.asList(donation1, donation2));

        List<Donation> result = donationService.getDonationsByCampaignId(1L);

        assertEquals(2, result.size());
        verify(donationRepository, times(1)).findByCampaignId(1L);
    }

    @Test
    @DisplayName("Should calculate total donation amount for a campaign")
    void testGetTotalDonationAmountByCampaignId() {
        when(donationRepository.findByCampaignId(anyLong())).thenReturn(Arrays.asList(donation1, donation2));

        double total = donationService.getTotalDonationAmountByCampaignId(1L);

        assertEquals(300.0, total);
        verify(donationRepository, times(1)).findByCampaignId(1L);
    }
}
