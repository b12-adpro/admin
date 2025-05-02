package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonationHistoryServiceImpl implements DonationHistoryService {

    private final List<DonationHistoryDTO> dummyDonations = new ArrayList<>();

    public DonationHistoryServiceImpl() {
        dummyDonations.add(DonationHistoryDTO.builder()
                .id(1L)
                .campaignTitle("Bantuan Pendidikan")
                .donorName("Andi")
                .amount(100000.0)
                .donatedAt(LocalDateTime.now().minusDays(3))
                .build());

        dummyDonations.add(DonationHistoryDTO.builder()
                .id(2L)
                .campaignTitle("Bantu Korban Banjir")
                .donorName("Siti")
                .amount(250000.0)
                .donatedAt(LocalDateTime.now().minusDays(1))
                .build());
    }

    @Override
    public List<DonationHistoryDTO> getAllDonationHistories() {
        return dummyDonations;
    }
}
