package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation getDonationById(Long id) {
        Optional<Donation> donation = donationRepository.findById(id);
        return donation.orElse(null);
    }

    @Override
    public List<Donation> getDonationsByCampaignId(Long campaignId) {
        return donationRepository.findByCampaignId(campaignId);
    }

    @Override
    public double getTotalDonationAmountByCampaignId(Long campaignId) {
        List<Donation> donations = donationRepository.findByCampaignId(campaignId);
        return donations.stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }
}