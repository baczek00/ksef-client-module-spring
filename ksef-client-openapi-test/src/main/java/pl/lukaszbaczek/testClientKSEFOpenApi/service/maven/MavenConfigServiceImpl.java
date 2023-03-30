package pl.lukaszbaczek.testClientKSEFOpenApi.service.maven;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MavenConfigServiceImpl implements MavenConfigService {

    @Value("${project.name}")
    private String name;
    @Value("${project.version}")
    private String version;

    @Value("${project.description}")
    private String description;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
