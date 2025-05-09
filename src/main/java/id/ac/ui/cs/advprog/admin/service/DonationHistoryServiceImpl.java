package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonationHistoryServiceImpl implements DonationHistoryService {

    private final List<DonationHistoryDTO> dummyDonations = new ArrayList<>();

    public DonationHistoryServiceImpl() {
        // Menggunakan UUID untuk ID dan CampaignId
        UUID donasiId1 = UUID.fromString("a8a79f87-4cb1-4bb2-8c13-01a344d98f23");
        UUID donasiId2 = UUID.fromString("f0b1f7f3-7e52-4063-bf6f-06d498b9a9b2");

        UUID campaignId1 = UUID.fromString("d4eaf8a4-90ab-47c1-9d5e-748fa4b374ed");
        UUID campaignId2 = UUID.fromString("c5c83917-92b2-496f-a0b7-d7acfa7d8df0");

        UUID donaturId1 = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042990");
        UUID donaturId2 = UUID.fromString("7e8725e7-c9d8-4176-a392-4c3897042989");

        // Menambahkan data dengan UUID yang benar
        dummyDonations.add(DonationHistoryDTO.builder()
                .id(donasiId1)
                .campaignId(campaignId1)
                .donaturId(donaturId1)
                .donaturName("Andi")
                .campaignTitle("Kampanye A")
                .amount(100000.0)
                .donatedAt(LocalDateTime.now().minusDays(3))
                .build());

        dummyDonations.add(DonationHistoryDTO.builder()
                .id(donasiId2)
                .campaignId(campaignId2)
                .donaturId(donaturId2)
                .donaturName("Siti")
                .campaignTitle("Kampanye C")
                .amount(250000.0)
                .donatedAt(LocalDateTime.now().minusDays(1))
                .build());
    }

    @Override
    public List<DonationHistoryDTO> getAllDonationHistories() {
        return dummyDonations;
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByCampaign(UUID campaignId) {
        return dummyDonations.stream()
                .filter(donation -> donation.getCampaignId().equals(campaignId))
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByDonatur(UUID donaturId) {
        return dummyDonations.stream()
                .filter(d -> d.getDonaturId().equals(donaturId))
                .collect(Collectors.toList());
    }
}
