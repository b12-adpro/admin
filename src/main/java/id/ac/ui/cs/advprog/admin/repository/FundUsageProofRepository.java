package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FundUsageProofRepository extends JpaRepository<FundUsageProof, UUID> {
    List<FundUsageProof> findByCampaignId(UUID campaignId);
}