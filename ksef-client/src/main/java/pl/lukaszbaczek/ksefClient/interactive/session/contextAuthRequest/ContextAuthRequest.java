package pl.lukaszbaczek.ksefClient.interactive.session.contextAuthRequest;

import lombok.Data;

@Data
public class ContextAuthRequest {
    private String type;
    private String identifier;
}
