package it.scratch.frontend.examples.services;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

public class CookieStoreRequestInterceptor implements ClientHttpRequestInterceptor {

    public CookieStoreRequestInterceptor(List<String> cookieStore) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ClientHttpResponse intercept(
        HttpRequest httpRequest,
        byte[] bytes,
        ClientHttpRequestExecution clientHttpRequestExecution
    ) throws IOException {
        throw new UnsupportedOperationException();
    }
}
