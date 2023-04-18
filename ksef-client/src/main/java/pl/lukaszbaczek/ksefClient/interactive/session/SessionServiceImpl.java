package pl.lukaszbaczek.ksefClient.interactive.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import pl.lukaszbaczek.ksefClient.client.WebClientKSEF;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeException;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeResponse;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.ContextAuthRequest;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private WebClientKSEF webClientKSEF;

    public AuthorisationChallengeResponse authorisationChallenge(ContextAuthRequest contextAuthRequest) throws AuthorisationChallengeException {
        Map<String, ContextAuthRequest> contextIdentifier = new HashMap<>();
        contextIdentifier.put("contextIdentifier", contextAuthRequest);
        String response = webClientKSEF
                .withUri("/online/Session/AuthorisationChallenge")
                .withHttpMethod(HttpMethod.POST)
                .withBody(contextAuthRequest)
                .execute();

        ZonedDateTime timestamp;
        String challenge;
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            timestamp = ZonedDateTime.parse(rootNode.get("timestamp").asText());
            challenge = rootNode.get("challenge").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new AuthorisationChallengeResponse(timestamp, challenge);

    }


    @Override
    public void initSessionSigned(byte[] request) {
    /*    webClient
                .post()
                .uri("/online/Session/InitSigned")
                .header("Content-Type", "application/octet-stream")
                .body(request, byte[].class)
                .exchange()
                .subscribe(response -> {
                    System.out.println("Response: " + response);
                });*/
    }

    @Override
    public void terminate() {

    }
}
