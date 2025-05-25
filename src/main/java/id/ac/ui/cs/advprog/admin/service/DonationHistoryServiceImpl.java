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
    private final UserService userService;
    private final CampaignService campaignService;
    private final String baseUrl;

    @Autowired
    public DonationHistoryServiceImpl(RestTemplate restTemplate, @Lazy UserService userService, @Lazy CampaignService campaignService, @Value("${external.transaction.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.userService = userService;
        this.campaignService = campaignService;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<DonationHistoryDTO> getAllDonationHistories() {
        String url = baseUrl;
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .filter(tx -> "DONATION".equalsIgnoreCase(tx.getType()))
                .map(this::mapToDonationHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByCampaign(UUID campaignId) {
        String url = baseUrl + "/donations/campaign/" + campaignId;
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .map(this::mapToDonationHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DonationHistoryDTO> getDonationHistoryByDonatur(UUID donaturId) {
        String url = baseUrl + "/user/" + donaturId;
        List<DonationTransactionDTO> transactions = fetchTransactions(url);

        return transactions.stream()
                .filter(tx -> "DONATION".equalsIgnoreCase(tx.getType()))
                .map(this::mapToDonationHistoryDTO)
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

    private DonationHistoryDTO mapToDonationHistoryDTO(DonationTransactionDTO dto) {
        UUID donaturId = dto.getWallet().getDonaturId();
        UUID campaignId = dto.getCampaignId();
        String donaturName = userService.getDonaturName(donaturId);
        String campaignTitle = campaignService.getCampaignDtoName(campaignId);

        return DonationHistoryDTO.builder()
                .id(dto.getId())
                .campaignId(campaignId)
                .donaturId(donaturId)
                .donaturName(donaturName)
                .campaignTitle(campaignTitle)
                .amount(dto.getAmount())
                .donatedAt(dto.getTimestamp())
                .build();
    }
}
