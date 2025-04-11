package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.Donation;
import id.ac.ui.cs.advprog.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByCampaign(Campaign campaign);
    List<Donation> findByDonatur(User donatur);
    List<Donation> findByCampaignId(Long campaignId);

    @Query("SELECT SUM(d.amount) FROM Donation d")
    Double sumTotalAmount();

    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.campaign.id = ?1")
    Double sumDonationAmountByCampaignId(Long campaignId);

    @Query("SELECT COUNT(d) FROM Donation d")
    long countTotalDonations();
}