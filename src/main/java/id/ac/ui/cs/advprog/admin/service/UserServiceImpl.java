package id.ac.ui.cs.advprog.admin.service;

import id.ac.ui.cs.advprog.admin.dto.UserDTO;
import id.ac.ui.cs.advprog.admin.enums.UserRole;
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

    @Value("${user.jwt.token}")
    private String jwtToken;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<String> createAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
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
    public UserDTO setBlockedStatus(UUID id, boolean status) {
        // Tidak didukung oleh API external (kecuali kamu punya endpoint PATCH/PUT)
        throw new UnsupportedOperationException("setBlockedStatus is not supported without an API endpoint.");
    }

    @Override
    public void deleteUser(UUID id) {
        // Tidak didukung oleh API external (kecuali kamu punya endpoint DELETE)
        throw new UnsupportedOperationException("deleteUser is not supported without an API endpoint.");
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
