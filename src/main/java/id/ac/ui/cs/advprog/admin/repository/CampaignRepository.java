package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.CampaignStatus;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatus(CampaignStatus status);

    long countByStatus(CampaignStatus status);

    long count();

    @Query("SELECT SUM(c.currentAmount) FROM Campaign c")
    Double getTotalRaisedAmount();

    @Query("SELECT SUM(c.targetAmount) FROM Campaign c")
    Double getTotalTargetAmount();
}