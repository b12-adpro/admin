package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;

import java.util.List;
import java.util.UUID;

public interface DonationHistoryService {
    List<DonationHistoryDTO> getAllDonationHistories();
    List<DonationHistoryDTO> getDonationHistoryByCampaign(UUID campaignId);
    List<DonationHistoryDTO> getDonationHistoryByDonatur(UUID donaturId);
}