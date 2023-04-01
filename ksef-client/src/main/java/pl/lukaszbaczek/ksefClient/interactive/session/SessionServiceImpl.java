package pl.lukaszbaczek.ksefClient.interactive.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeException;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeResponse;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.ContextAuthRequest;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private WebClient webClient;

    public AuthorisationChallengeResponse authorisationChallenge(ContextAuthRequest contextAuthRequest) throws AuthorisationChallengeException {
        Map<String, ContextAuthRequest> contextIdentifier = new HashMap<>();
        contextIdentifier.put("contextIdentifier", contextAuthRequest);

        try {
            String response = webClient.post().uri("/online/Session/AuthorisationChallenge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(contextIdentifier))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response);
            ZonedDateTime timestamp = ZonedDateTime.parse(rootNode.get("timestamp").asText());
            String challenge = rootNode.get("challenge").asText();
            return new AuthorisationChallengeResponse(timestamp, challenge);
        } catch (WebClientResponseException e) {
            String responseBody = e.getResponseBodyAsString();

            try {
                AuthorisationChallengeException.ExceptionResponse exceptionResponse = new ObjectMapper().readValue(responseBody, AuthorisationChallengeException.ExceptionResponse.class);

                AuthorisationChallengeException.ExceptionDetails exceptionDetails = new AuthorisationChallengeException.ExceptionDetails();
                exceptionDetails.setServiceCtx(exceptionResponse.getException().getServiceCtx());
                exceptionDetails.setServiceCode(exceptionResponse.getException().getServiceCode());
                exceptionDetails.setServiceName(exceptionResponse.getException().getServiceName());
                exceptionDetails.setTimestamp(exceptionResponse.getException().getTimestamp());
                exceptionDetails.setExceptionDetailList(exceptionResponse.getException().getExceptionDetailList());

                throw new AuthorisationChallengeException(responseBody, exceptionDetails);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing response body", ex);
            }
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
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
