package pl.lukaszbaczek.testClientKSEFOpenApi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import pl.lukaszbaczek.testClientKSEFOpenApi.config.propertiesFile.api.ApiProperties;

@Slf4j
@Configuration
public class ApiConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        try {
            factory.setPort(apiProperties.getPort());
        } catch (NumberFormatException e) {
            log.error("Nieprawidłowy port - PORT MUSI BYĆ OKREŚLONY LICZBĄ");
            e.printStackTrace();
        }
    }
}
