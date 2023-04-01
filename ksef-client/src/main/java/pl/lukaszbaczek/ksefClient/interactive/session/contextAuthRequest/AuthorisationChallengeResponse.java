package pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class AuthorisationChallengeResponse {
    private ZonedDateTime timestamp;
    private String challenge;
}
