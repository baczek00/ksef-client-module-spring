package pl.lukaszbaczek.ksefClient.interactive.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private WebClient webClient;

    @Override
    public void authorisationChallenge(ContextAuthRequest contextAuthRequest) throws RuntimeException {
        Map<String, ContextAuthRequest> contextIdentifier = new HashMap<>();
        contextIdentifier.put("contextIdentifier",contextAuthRequest);

        try {
            String response = webClient.post().uri("/online/Session/AuthorisationChallenge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(contextIdentifier))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Response: " + response);
        } catch (WebClientResponseException e) {
            String errorMessage = "Error during authorisation challenge: " + e.getResponseBodyAsString();
            throw new RuntimeException(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error during authorisation challenge: " + e.getMessage();
            throw new RuntimeException(errorMessage);
        }
    }
    @Override
    public void initSessionSigned(byte[] request) {
            webClient
                    .post()
                    .uri("/online/Session/InitSigned")
                    .header("Content-Type", "application/octet-stream")
                    .body(request, byte[].class)
                    .exchange()
                    .subscribe(response -> {
                        System.out.println("Response: " + response);
                    });
    }

    @Override
    public void terminate() {

    }
}
