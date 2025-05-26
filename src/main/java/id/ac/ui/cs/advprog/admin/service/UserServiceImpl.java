package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Value("${user.api.url}")
    private String apiUrl;

    @Value("${user.api.loginUrl}")
    private String apiLoginUrl;

    private String jwtToken;

    @Value("${user.api.admin.email}")
    private String adminEmail;

    @Value("${user.api.admin.password}")
    private String adminPassword;

    private void authenticateAndStoreJwt() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("email", adminEmail);
        loginRequest.put("password", adminPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiLoginUrl, entity, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                jwtToken = (String) response.getBody().get("token");
            } else {
                System.err.println("Login gagal: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Gagal login: " + e.getMessage());
        }
    }

    @PostConstruct
    public void init() {
        authenticateAndStoreJwt();
    }


    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<String> createAuthEntity() {
        if (jwtToken == null || jwtToken.isEmpty()) {
            authenticateAndStoreJwt();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        return new HttpEntity<>(headers);
    }


    @Override
    public List<UserDTO> getAllUsers() {
        try {
            ResponseEntity<Map[]> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.GET,
                    createAuthEntity(),
                    Map[].class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.stream(response.getBody())
                        .map(this::mapToUserDTO)
                        .collect(Collectors.toList());
            }

        } catch (RestClientException e) {
            return Collections.emptyList();
        }

        return Collections.emptyList();
    }

    @Override
    public Optional<UserDTO> getUserById(UUID id) {
        return getAllUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean isUserBlocked(UUID id) {
        return getUserById(id)
                .map(UserDTO::isBlocked)
                .orElse(false);
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        return getAllUsers().stream()
                .filter(user -> !user.isBlocked())
                .collect(Collectors.toList());
    }

    @Override
    public String getDonaturName(UUID donaturId) {
        return getUserById(donaturId)
                .map(UserDTO::getName)
                .orElse(null);
    }

    @Override
    public int countAllUsers() {
        return getAllUsers().size();
    }

    private UserDTO mapToUserDTO(Map<String, Object> map) {
        UserDTO.UserDTOBuilder builder = UserDTO.builder()
                .id(UUID.fromString((String) map.get("id")))
                .name((String) map.get("fullName"))
                .isBlocked((Boolean) map.getOrDefault("blocked", false));

        return builder.build();
    }
}
