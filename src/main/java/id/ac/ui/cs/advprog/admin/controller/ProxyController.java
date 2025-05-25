package id.ac.ui.cs.advprog.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/proxy")
public class ProxyController {

    private final WebClient webClient = WebClient.create();

    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
    public Mono<ResponseEntity<String>> proxyAll(HttpServletRequest request,
                                                 @RequestBody(required = false) String body) {
        String path = extractPath(request); // misal: profile/all atau api/campaign/all atau api/transaction/123
        HttpMethod method = HttpMethod.valueOf(request.getMethod());

        // Routing ke base URL sesuai prefix path
        String targetBaseUrl;
        if (path.startsWith("profile/")) {
            targetBaseUrl = "https://kind-danyelle-nout-721a9e0a.koyeb.app/";
        } else if (path.startsWith("api/campaign")) {
            targetBaseUrl = "http://3.211.204.60/";
        } else if (path.startsWith("api/transaction")) {
            targetBaseUrl = "https://comfortable-tonia-aryaraditya-081c5726.koyeb.app/";
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("API not found"));
        }

        // Buat URL lengkap target
        String targetUri = targetBaseUrl + path;

        WebClient.RequestBodySpec requestSpec = webClient.method(method)
                .uri(targetUri);

        if (body != null && (method == HttpMethod.POST || method == HttpMethod.PUT)) {
            requestSpec.bodyValue(body);
        }

        return requestSpec.retrieve()
                .toEntity(String.class);
    }

    private String extractPath(HttpServletRequest request) {
        String fullPath = request.getRequestURI(); // contoh: /proxy/profile/all
        return fullPath.substring("/proxy/".length()); // hasil: profile/all
    }
}

