package id.ac.ui.cs.advprog.admin.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;
import id.ac.ui.cs.advprog.admin.repository.FundUsageProofRepository;

@Service
public class FundUsageProofServiceImpl implements FundUsageProofService {

    private final FundUsageProofRepository fundUsageProofRepository;

    @Autowired
    public FundUsageProofServiceImpl(FundUsageProofRepository fundUsageProofRepository) {
        this.fundUsageProofRepository = fundUsageProofRepository;
    }

    @Override
    public long countProofsByStatus(ProofStatus status) {
        return fundUsageProofRepository.countByStatus(status);
    }
}