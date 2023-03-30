package pl.lukaszbaczek.testClientKSEFOpenApi.config.propertiesFile;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:ksef-api.properties")
public class ConfigPropertiesApi {

}


