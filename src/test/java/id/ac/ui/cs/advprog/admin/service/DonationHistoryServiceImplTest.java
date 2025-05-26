package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class DonationHistoryServiceImplTest {

    private DonationHistoryServiceImpl donationHistoryService;
    private MockRestServiceServer mockServer;
    private RestTemplate restTemplate;

    private UserService userService; // Add this
    private CampaignService campaignService;
    private final String API_URL = "https://comfortable-tonia-aryaraditya-081c5726.koyeb.app/api/transaction";

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplateBuilder().build();
        mockServer = MockRestServiceServer.createServer(restTemplate);

        userService = Mockito.mock(UserService.class);
        campaignService = Mockito.mock(CampaignService.class);

        donationHistoryService = new DonationHistoryServiceImpl(
                restTemplate,
                userService,
                campaignService,
                API_URL
        );
    }

    @Test
    void testGetAllDonationHistories() {
        String jsonResponse = """
        {
          "status": "success",
          "message": "Transaction retrieved",
          "data": [
            {
              "id": "a8a79f87-4cb1-4bb2-8c13-01a344d98f23",
              "campaignId": "00000000-0000-0000-0000-000000000001",
              "amount": 100000.0,
              "timestamp": "2024-05-20T12:00:00Z",
              "type": "DONATION",
              "wallet": {
                "donaturId": "00000000-0000-0000-0000-000000000002"
              }
            },
            {
              "id": "f0b1f7f3-7e52-4063-bf6f-06d498b9a9b2",
              "campaignId": "00000000-0000-0000-0000-000000000003",
              "amount": 250000.0,
              "timestamp": "2024-05-20T13:00:00Z",
              "type": "DONATION",
              "wallet": {
                "donaturId": "00000000-0000-0000-0000-000000000004"
              }
            }
          ]
        }
    """;

        // Mock services
        when(userService.getDonaturName(any())).thenReturn("Test User");
        when(campaignService.getCampaignDtoName(any())).thenReturn("Test Campaign");

        // Mock server expectation - only once!
        mockServer.expect(requestTo(API_URL + "/type?type=DONATION"))
                .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        // Execute test
        List<DonationHistoryDTO> donationHistories = donationHistoryService.getAllDonationHistories();

        // Verify
        assertEquals(2, donationHistories.size());
        DonationHistoryDTO first = donationHistories.get(0);
        assertEquals(UUID.fromString("a8a79f87-4cb1-4bb2-8c13-01a344d98f23"), first.getId());
        assertEquals(new BigDecimal("100000.0"), first.getAmount());
        assertNotNull(first.getDonatedAt());

        mockServer.verify();
    }
}
