package com.ak.work.client.producer;

import com.ak.work.client.entity.*;
import com.ak.work.client.exception.CallToExternalServiceException;
import com.ak.work.client.util.URIUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ak.work.client.util.URIUtils.getUri;

@Slf4j
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
        paramType.put(Expert.class, new ParameterizedTypeReference<List<Expert>>() {
        });
        paramType.put(User.class, new ParameterizedTypeReference<List<User>>() {
        });
        paramType.put(VisitHistory.class, new ParameterizedTypeReference<List<VisitHistory>>() {
        });
    }

    protected URI getUriWithPaths(Object[] pathVariables, String... paths) {
        return getUri(pathVariables, Collections.emptyList(), scheme, host, port, prefix, paths);
    }

    protected URI getUriWithPathsAndParams(List<URIUtils.QueryParam> queryParams, String... paths) {
        return getUri(null, queryParams, scheme, host, port, prefix, paths);
    }

    protected URI getClearUri(String... paths) {
        return getUri(null, Collections.emptyList(), scheme, host, port, prefix, paths);
    }

    protected URI getUriWithParamsAndPathVariables(Object[] pathVariables, List<URIUtils.QueryParam> queryParams, String... paths) {
        return getUri(pathVariables, queryParams, scheme, host, port, prefix, paths);
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
            log.error(e.getLocalizedMessage());
            throw new CallToExternalServiceException(method, uri);
        }
    }

    //т.к. мы возвращаем [][], то мы не можем это обозначить как 'T' в методе выше,
    //это не объект , а массив массивов объектов
    protected int[][] getProblemSolutionMatrix(Integer problemId, String problemPath, Integer expertId) {
        URI uri = getUriWithParamsAndPathVariables(new Object[]{problemId},
                Collections.singletonList(
                        new URIUtils.QueryParam("expertId", new Object[]{expertId})),
                problemPath, "{id}", "solutionMatrix");
        try {
            ResponseEntity<int[][]> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<int[][]>() {
                    });

            return response.getBody();
        } catch (RestClientException e) {
            throw new CallToExternalServiceException(HttpMethod.GET, uri);
        }
    }
}
