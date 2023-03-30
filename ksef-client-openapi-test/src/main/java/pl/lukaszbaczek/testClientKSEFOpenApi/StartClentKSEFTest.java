package pl.lukaszbaczek.testClientKSEFOpenApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan("pl.lukaszbaczek.testClientKSEFOpenApi.config.propertiesFile")
public class StartClentKSEFTest {

    public static void main(String[] args) {
        SpringApplication.run(StartClentKSEFTest.class, args);

    }
}
