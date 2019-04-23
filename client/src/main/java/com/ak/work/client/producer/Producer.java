package com.ak.work.client.producer;

import com.ak.work.client.entity.Problem;
import com.ak.work.client.entity.Solution;
import com.ak.work.client.exception.CallToExternalServiceException;
import com.ak.work.client.util.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final Map<Class, ParameterizedTypeReference> paramType = new HashMap<>();

    static {
        paramType.put(Problem.class, new ParameterizedTypeReference<List<Problem>>() {
        });
        paramType.put(Solution.class, new ParameterizedTypeReference<List<Solution>>() {
        });
    }

    protected URI getUriWithPaths(Object[] objects, String... paths) {
        return getUri(objects, scheme, host, port, prefix, paths);
    }

    protected URI getUriWithPathsAndParams(List<URIUtils.QueryParam> queryParams, String... paths) {
        return getUri(queryParams, scheme, host, port, prefix, paths);
    }

    protected <T> T getOneObject(URI uri, HttpMethod method, Object body, Class<T> clazz) {
        try {
            ResponseEntity<T> response = restTemplate.exchange(
                    uri,
                    method,
                    body != null ? new HttpEntity<>(body) : null,
                    clazz);

            return response.getBody();
        } catch (RestClientException e) {
            throw new CallToExternalServiceException(method, uri);
        }
    }

    @SuppressWarnings("all")
    protected <T> List<T> getObjectList(URI uri, HttpMethod method, Object body, Class<T> clazz) {
        try {
            ResponseEntity<List<T>> response = restTemplate.exchange(
                    uri,
                    method,
                    body != null ? new HttpEntity<>(body) : null,
                    paramType.get(clazz));

            return response.getBody();
        } catch (RestClientException e) {
            throw new CallToExternalServiceException(method, uri);
        }
    }
}
