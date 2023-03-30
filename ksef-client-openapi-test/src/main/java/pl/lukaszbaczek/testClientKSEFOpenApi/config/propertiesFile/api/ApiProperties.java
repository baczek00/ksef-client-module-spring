package pl.lukaszbaczek.testClientKSEFOpenApi.config.propertiesFile.api;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@ConfigurationProperties(prefix = "api")
@ConfigurationPropertiesScan
public class ApiProperties {

    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
