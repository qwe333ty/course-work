package com.ak.work.client.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

public class URIUtils {

    public static URI getUri(Object[] pathVariables, List<QueryParam> queryParams, String scheme, String host, String port, String prefix, String... paths) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(prefix)
                .pathSegment(paths);

        for (QueryParam param : queryParams) {
            builder.queryParam(param.name, param.objects);
        }

        UriComponents components;
        if (pathVariables != null) {
            components = builder.buildAndExpand(pathVariables);
        } else {
            components = builder.build();
        }
        return components.toUri();
    }

    @Data
    @AllArgsConstructor
    public static class QueryParam {
        private String name;
        private Object[] objects;
    }
}
