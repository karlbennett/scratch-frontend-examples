package it.scratch.frontend.examples;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CookieStoreRequestInterceptor implements ClientHttpRequestInterceptor {

    private final Set<String> cookieStore;

    public CookieStoreRequestInterceptor(Set<String> cookieStore) {
        this.cookieStore = cookieStore;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] bytes, ClientHttpRequestExecution execution)
        throws IOException {
        addCookies(request);
        return storeCookies(execution.execute(request, bytes));
    }

    public void clearCookies() {
        cookieStore.clear();
    }

    private void addCookies(HttpRequest request) {
        final HttpHeaders headers = request.getHeaders();
        cookieStore.forEach(cookie -> headers.add("Cookie", cookie));
    }

    private ClientHttpResponse storeCookies(ClientHttpResponse response) {
        final List<String> cookies = response.getHeaders().get("Set-Cookie");
        if (cookies != null) {
            cookies.stream().map(cookie -> cookie.split(";")[0]).forEach(cookieStore::add);
        }
        return response;
    }
}
