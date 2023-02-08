package pl.lukaszbaczek.ksefClient.interactive.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private WebClient webClient;

    @Override
    public void authorisationChallenge(ContextAuthRequest contextAuthRequest) {
        Map<String, ContextAuthRequest> contextIdentifier = new HashMap<>();
        contextIdentifier.put("contextIdentifier",contextAuthRequest);

        webClient.post().uri("/online/Session/AuthorisationChallenge")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(contextIdentifier))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    System.out.println("Response: " + response);
                });
    }

    @Override
    public void terminate() {

    }
}
