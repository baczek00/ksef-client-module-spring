package pl.lukaszbaczek.ksefClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import pl.lukaszbaczek.ksefClient.client.WebClientKSEFImpl;
import pl.lukaszbaczek.ksefClient.interactive.session.SessionService;
import pl.lukaszbaczek.ksefClient.interactive.session.SessionServiceImpl;

@Configuration
public class WebClientConfig {

    private final String apiUrl = "https://ksef-test.mf.gov.pl/api";

    @Bean
    public WebClient webClient() {
        return WebClient.create(apiUrl);
    }

    @Bean
    public WebClientKSEFImpl webClientKSEF() {
        return new WebClientKSEFImpl();
    }
    @Bean
    public SessionService sessionService() {
        return new SessionServiceImpl();
    }


}
