package pl.lukaszbaczek.ksefClient.interactive.session;

public interface SessionService {

    void authorisationChallenge(ContextAuthRequest contextAuthRequest);

    void terminate();

}
