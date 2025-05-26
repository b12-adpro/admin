package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.dto.Donation.DonationTransactionDTO;
import id.ac.ui.cs.advprog.admin.dto.Donation.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DonationHistoryServiceImpl implements DonationHistoryService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public DonationHistoryServiceImpl(RestTemplate restTemplate, @Lazy UserService userService, @Lazy CampaignService campaignService, @Value("${external.transaction.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<DonationHistoryDTO> getAllDonationHistories() {
        String url = baseUrl + "/type?type=DONATION";
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .map(tx -> {
                    return DonationHistoryDTO.builder()
                            .id(tx.getId())
                            .amount(tx.getAmount())
                            .donatedAt(tx.getTimestamp())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByCampaign(UUID campaignId) {
        String url = baseUrl + "/donations/campaign/" + campaignId;
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .map(tx -> {
                    return DonationHistoryDTO.builder()
                            .id(tx.getId())
                            .amount(tx.getAmount())
                            .donatedAt(tx.getTimestamp())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByDonatur(UUID donaturId) {
        String url = baseUrl + "/user/" + donaturId;
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .map(tx -> {
                    return DonationHistoryDTO.builder()
                            .id(tx.getId())
                            .amount(tx.getAmount())
                            .donatedAt(tx.getTimestamp())
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<DonationTransactionDTO> fetchTransactions(String url) {
        ParameterizedTypeReference<GeneralResponse<List<DonationTransactionDTO>>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<GeneralResponse<List<DonationTransactionDTO>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                responseType
        );

        return response.getBody().getData();
    }
}
