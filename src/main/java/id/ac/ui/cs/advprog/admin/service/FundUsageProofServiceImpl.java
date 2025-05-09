package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.FundUsageProofDTO;
import id.ac.ui.cs.advprog.admin.model.FundUsageProof;
import id.ac.ui.cs.advprog.admin.repository.FundUsageProofRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundUsageProofServiceImpl implements FundUsageProofService {

    private final FundUsageProofRepository fundUsageProofRepository;

    @Override
    public List<FundUsageProofDTO> getAllProofsDTO() {
        List<FundUsageProof> proofs = fundUsageProofRepository.findAll();
        return proofs.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FundUsageProofDTO> getProofsByCampaignDTO(UUID campaignId) {
        List<FundUsageProof> proofs = fundUsageProofRepository.findByCampaignId(campaignId);
        return proofs.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private FundUsageProofDTO toDTO(FundUsageProof proof) {
        return FundUsageProofDTO.builder()
                .id(proof.getId())
                .campaignId(proof.getCampaignId())
                .title(proof.getTitle())
                .description(proof.getDescription())
                .amount(proof.getAmount())
                .submittedAt(proof.getSubmittedAt())
                .build();
    }
}