package id.ac.ui.cs.advprog.admin.repository;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import id.ac.ui.cs.advprog.admin.model.Campaign;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundUsageProofRepository extends JpaRepository<FundUsageProof, Long> {
    List<FundUsageProof> findByCampaign(Campaign campaign);
    List<FundUsageProof> findByStatus(ProofStatus status);
    long countByStatus(ProofStatus status);
}