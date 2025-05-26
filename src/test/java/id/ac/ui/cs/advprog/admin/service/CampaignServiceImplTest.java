package id.ac.ui.cs.advprog.admin.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.admin.dto.CampaignDTO;
import id.ac.ui.cs.advprog.admin.dto.DonationHistoryDTO;
import id.ac.ui.cs.advprog.admin.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CampaignServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private DonationHistoryService donationHistoryService;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    private UUID campaignId;
    private CampaignDTO sampleCampaign;

    @BeforeEach
    void setup() {
        campaignId = UUID.randomUUID();

        sampleCampaign = new CampaignDTO();
        sampleCampaign.setCampaignId(campaignId);
        sampleCampaign.setStatus(Status.PENDING.name());
        sampleCampaign.setTarget(1000);
        sampleCampaign.setCurrentAmount(0);
    }

    @Test
    void testGetCampaignDTOById_success() {
        when(restTemplate.getForObject(anyString(), eq(CampaignDTO.class)))
                .thenReturn(sampleCampaign);

        List<DonationHistoryDTO> donations = List.of(
                createDonation(new BigDecimal("200")),
                createDonation(new BigDecimal("300"))
        );

        when(donationHistoryService.getDonationHistoryByCampaign(campaignId)).thenReturn(donations);

        CampaignDTO result = campaignService.getCampaignDTOById(campaignId);

        assertNotNull(result);
        assertEquals(Status.PENDING.name(), result.getStatus());
        // currentAmount dihitung dari donation history (200 + 300)
        assertEquals(500, result.getCurrentAmount());
    }

    @Test
    void testGetCampaignDTOById_notFound() {
        when(restTemplate.getForObject(anyString(), eq(CampaignDTO.class)))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.NOT_FOUND));

        assertThrows(NoSuchElementException.class, () -> {
            campaignService.getCampaignDTOById(campaignId);
        });
    }

    @Test
    void testGetAllCampaigns() {
        CampaignDTO[] campaigns = new CampaignDTO[]{ sampleCampaign };

        when(restTemplate.getForObject(anyString(), eq(CampaignDTO[].class))).thenReturn(campaigns);

        List<DonationHistoryDTO> donations = List.of(createDonation(new BigDecimal("150")));
        when(donationHistoryService.getDonationHistoryByCampaign(campaignId)).thenReturn(donations);

        List<CampaignDTO> allCampaigns = campaignService.getAllCampaigns();

        assertEquals(1, allCampaigns.size());
        assertEquals(150, allCampaigns.get(0).getCurrentAmount());
        assertEquals("PENDING", allCampaigns.get(0).getStatus()); // from calculateProgressStatus because status was PENDING
    }

    @Test
    void testCountCampaignsByStatus() {
        CampaignDTO c1 = new CampaignDTO();
        c1.setStatus(Status.ACTIVE.name());

        CampaignDTO c2 = new CampaignDTO();
        c2.setStatus(Status.PENDING.name());

        CampaignDTO[] campaigns = new CampaignDTO[]{ c1, c2 };

        when(restTemplate.getForObject(anyString(), eq(CampaignDTO[].class))).thenReturn(campaigns);

        long countActive = campaignService.countCampaignsByStatus(Status.ACTIVE);
        assertEquals(1, countActive);

        long countPending = campaignService.countCampaignsByStatus(Status.PENDING);
        assertEquals(1, countPending);
    }

    @Test
    void testVerifyCampaign_approve() {
        when(restTemplate.getForObject(anyString(), eq(CampaignDTO.class))).thenReturn(sampleCampaign);

        campaignService.verifyCampaign(campaignId, true);

        verify(restTemplate).put(anyString(), isNull());
        verify(restTemplate, times(2)).getForObject(anyString(), eq(CampaignDTO.class));
    }

    @Test
    void testGetTotalRaisedAmount() {
        CampaignDTO c1 = new CampaignDTO();
        c1.setCurrentAmount(100);

        CampaignDTO c2 = new CampaignDTO();
        c2.setCurrentAmount(200);

        CampaignDTO[] campaigns = new CampaignDTO[]{ c1, c2 };
        when(restTemplate.getForObject(anyString(), eq(CampaignDTO[].class))).thenReturn(campaigns);

        double totalRaised = campaignService.getTotalRaisedAmount();

        assertEquals(300, totalRaised);
    }

    @Test
    void testGetFundUsageProofsByCampaignId_success() {
        String proofData = "Some proof data";
        when(restTemplate.getForObject(anyString(), eq(String.class))).thenReturn(proofData);

        String result = campaignService.getFundUsageProofsByCampaignId(campaignId);

        assertEquals(proofData, result);
    }

    @Test
    void testGetFundUsageProofsByCampaignId_notFound() {
        when(restTemplate.getForObject(anyString(), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        assertThrows(NoSuchElementException.class, () -> {
            campaignService.getFundUsageProofsByCampaignId(campaignId);
        });
    }

    private DonationHistoryDTO createDonation(BigDecimal amount) {
        return new DonationHistoryDTO(
                UUID.randomUUID(),
                UUID.randomUUID(),
                UUID.randomUUID(),
                "John Doe",
                "Test message",
                amount,
                LocalDateTime.now()
        );
    }
}
