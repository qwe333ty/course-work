package com.ak.work.client.exception;

import org.springframework.http.HttpMethod;

import java.net.URI;

public class CallToExternalServiceException extends RuntimeException {
    public CallToExternalServiceException() {
        super();
    }

    public CallToExternalServiceException(String message) {
        super(message);
    }

    public CallToExternalServiceException(HttpMethod method, URI uri) {
        super(String.format("Call to server failed. %s method. The uri was %s", method.toString(), uri.toString()));
    }
}
