package com.ak.work.client.util;

import com.ak.work.client.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MessageProducer {

    @Value("${gateway.scheme}")
    private String scheme;

    @Value("${gateway.host}")
    private String host;

    @Value("${gateway.port}")
    private String port;

    @Value("${api.prefix}")
    private String prefix;

    @Value("${api.client-ep}")
    private String endpoint;

    @Autowired
    private RestTemplate restTemplate;

    public HttpStatus sendMessage(String name, String mes) {
        Message message = new Message(name, mes);
        try {
            return restTemplate.exchange(
                    URIUtils.getUri(
                            scheme,
                            host,
                            port,
                            prefix, endpoint),
                    HttpMethod.POST,
                    new HttpEntity<>(message),
                    Message.class).getStatusCode();
        } catch (RestClientException e) {
            return HttpStatus.BAD_REQUEST;
        }
    }

    public ResponseEntity<List<Message>> getMessageHistory() {
        return restTemplate.exchange(
                URIUtils.getUri(
                        scheme,
                        host,
                        port,
                        prefix, endpoint, "/all"),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Message>>() {
                }
        );
    }
}
