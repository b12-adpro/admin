package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;

import java.util.List;

public interface DonationHistoryService {
    List<DonationHistoryDTO> getAllDonationHistories();
}