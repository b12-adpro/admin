package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.enums.ProofStatus;

public interface FundUsageProofService {
    long countProofsByStatus(ProofStatus status);
}
