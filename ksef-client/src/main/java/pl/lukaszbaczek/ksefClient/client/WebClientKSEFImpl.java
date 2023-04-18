package pl.lukaszbaczek.ksefClient.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.IOException;

@Service
public class WebClientKSEFImpl implements WebClientKSEF {

    @Autowired
    private WebClient webClient;
    private String uri;
    private HttpMethod httpMethod;
    private Object body;

    @Override
    public WebClientKSEF withUri(String uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public WebClientKSEF withHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    @Override
    public WebClientKSEF withBody(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public String execute() throws WebClientKSEFException {
        try {
            WebClient.RequestBodySpec request = webClient.method(httpMethod).uri(uri).contentType(MediaType.APPLICATION_JSON);

            if (body != null) {
                request.body(BodyInserters.fromValue(body));
            }

            String response = request.retrieve()
                    .bodyToMono(String.class)
                    .block();

            return response;
        } catch (WebClientResponseException e) {
            String responseBody = e.getResponseBodyAsString();

            try {
                WebClientKSEFException.ExceptionResponse exceptionResponse = new ObjectMapper().readValue(responseBody, WebClientKSEFException.ExceptionResponse.class);

                WebClientKSEFException.ExceptionDetails exceptionDetails = new WebClientKSEFException.ExceptionDetails();
                exceptionDetails.setServiceCtx(exceptionResponse.getException().getServiceCtx());
                exceptionDetails.setServiceCode(exceptionResponse.getException().getServiceCode());
                exceptionDetails.setServiceName(exceptionResponse.getException().getServiceName());
                exceptionDetails.setTimestamp(exceptionResponse.getException().getTimestamp());
                exceptionDetails.setExceptionDetailList(exceptionResponse.getException().getExceptionDetailList());

                throw new WebClientKSEFException(responseBody, exceptionDetails);
            } catch (IOException ex) {
                throw new RuntimeException("Error parsing response body", ex);
            }
        }
    }
}
