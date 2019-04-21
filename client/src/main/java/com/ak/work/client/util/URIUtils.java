package com.ak.work.client.util;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class URIUtils {

    public static URI getUri(String scheme, String host, String port, String prefix, String... paths) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(prefix)
                .pathSegment(paths).build().toUri();
    }
}
