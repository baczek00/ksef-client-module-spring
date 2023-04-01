package pl.lukaszbaczek.ksefClient.interactive.session;

import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeException;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.AuthorisationChallengeResponse;
import pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest.ContextAuthRequest;

public interface SessionService {

    AuthorisationChallengeResponse authorisationChallenge(ContextAuthRequest contextAuthRequest) throws AuthorisationChallengeException;

    void initSessionSigned(byte[] request);

        void terminate();

}
