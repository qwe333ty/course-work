package com.ak.work.client.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.ak.work.client.util.URIUtils.getUri;

public abstract class Producer {

    @Value("${gateway.scheme}")
    protected String scheme;

    @Value("${gateway.host}")
    protected String host;

    @Value("${gateway.port}")
    protected String port;

    @Value("${api.prefix}")
    protected String prefix;

    @Autowired
    protected RestTemplate restTemplate;

    protected URI getUriWithPaths(String... paths) {
        return getUri(
                scheme,
                host,
                port,
                prefix,
                paths);
    }
}
