package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.Donation;

import java.util.List;

public interface DonationService {
    // Mendapatkan semua donasi
    List<Donation> getAllDonations();

    // Mendapatkan donasi berdasarkan ID
    Donation getDonationById(Long id);

    // Mendapatkan donasi berdasarkan ID kampanye
    List<Donation> getDonationsByCampaignId(Long campaignId);

    // Mendapatkan total jumlah donasi untuk kampanye
    double getTotalDonationAmountByCampaignId(Long campaignId);
}