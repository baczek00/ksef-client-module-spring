package pl.lukaszbaczek.testClientKSEFOpenApi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lukaszbaczek.testClientKSEFOpenApi.config.propertiesFile.api.ApiProperties;
import pl.lukaszbaczek.testClientKSEFOpenApi.service.maven.MavenConfigService;

@Configuration
public class OpenApiConfig {

    @Autowired
    private MavenConfigService mavenConfigService;

    @Autowired
    private ApiProperties apiProperties;

    @Bean
    public OpenAPI loadOpenApiConfig() {
        System.out.println("### OpenAPI documentation: http://localhost:" + apiProperties.getPort() + "/swagger-ui.html");
        return new OpenAPI().info(new Info()
                        .title(mavenConfigService.getName())
                        .description(mavenConfigService.getDescription())
                        .version(mavenConfigService.getVersion()))
                .components(new Components()
                        .addSecuritySchemes("besicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                        .addHeaders("Authorization",
                                new Header()
                                        .description("auth token")
                                        .required(true)));
    }
}

