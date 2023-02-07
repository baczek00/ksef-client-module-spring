package pl.lukaszbaczek.ksefClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackages = "pl.lukaszbaczek.ksefClient")
public class WebClientConfig {

    private final String apiUrl = "https://ksef-test.mf.gov.pl/api";

    @Bean
    public WebClient webClient() {
        return WebClient.create(apiUrl);
    }
}
