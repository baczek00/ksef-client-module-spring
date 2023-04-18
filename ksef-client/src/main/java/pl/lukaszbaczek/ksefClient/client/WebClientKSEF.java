package pl.lukaszbaczek.ksefClient.client;

import org.springframework.http.HttpMethod;

public interface WebClientKSEF {
    WebClientKSEF withUri(String uri);

    WebClientKSEF withHttpMethod(HttpMethod httpMethod);

    WebClientKSEF withBody(Object body);

    String execute() throws WebClientKSEFException;
}
