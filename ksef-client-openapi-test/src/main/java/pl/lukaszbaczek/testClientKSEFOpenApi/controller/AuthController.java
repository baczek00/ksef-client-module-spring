package pl.lukaszbaczek.testClientKSEFOpenApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lukaszbaczek.ksefClient.interactive.session.ContextAuthRequest;
import pl.lukaszbaczek.ksefClient.interactive.session.SessionService;

@RestController
public class AuthController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/v1/alaMaKota")
    public void loginUser(ContextAuthRequest contextAuthRequest) {
            sessionService.authorisationChallenge(contextAuthRequest);

    }
}