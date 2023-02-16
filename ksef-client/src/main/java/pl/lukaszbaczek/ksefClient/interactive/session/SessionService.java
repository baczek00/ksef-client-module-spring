package pl.lukaszbaczek.ksefClient.interactive.session;

import reactor.core.publisher.Mono;

public interface SessionService {

    void authorisationChallenge(ContextAuthRequest contextAuthRequest);

    void initSessionSigned(byte[] request);

        void terminate();

}
