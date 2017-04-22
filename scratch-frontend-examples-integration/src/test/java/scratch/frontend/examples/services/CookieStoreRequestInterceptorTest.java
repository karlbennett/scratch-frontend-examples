package scratch.frontend.examples.services;

import it.scratch.frontend.examples.services.CookieStoreRequestInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomBytes.someBytes;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

public class CookieStoreRequestInterceptorTest {

    private String requestCookie1;
    private String requestCookie2;
    private String requestCookie3;
    private Set<String> cookieStore;
    private CookieStoreRequestInterceptor interceptor;

    @Before
    public void setUp() {
        requestCookie1 = someAlphanumericString();
        requestCookie2 = someAlphanumericString();
        requestCookie3 = someAlphanumericString();
        cookieStore = spy(new HashSet<>(asList(requestCookie1, requestCookie2, requestCookie3)));
        interceptor = new CookieStoreRequestInterceptor(cookieStore);
    }

    @Test
    public void Can_store_any_cookies() throws IOException {

        final HttpRequest request = mock(HttpRequest.class);
        final byte[] bytes = someBytes();
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

        final HttpHeaders requestHeaders = mock(HttpHeaders.class);
        final ClientHttpResponse expected = mock(ClientHttpResponse.class);
        final HttpHeaders responseHeaders = mock(HttpHeaders.class);
        final String responseCookie1 = someAlphanumericString();
        final String responseCookie2 = someAlphanumericString();
        final String responseCookie3 = someAlphanumericString();

        // Given
        given(request.getHeaders()).willReturn(requestHeaders);
        given(execution.execute(request, bytes)).willReturn(expected);
        given(expected.getHeaders()).willReturn(responseHeaders);
        given(responseHeaders.get("Set-Cookie"))
            .willReturn(asList(cookie(responseCookie1), cookie(responseCookie2), cookie(responseCookie3)));

        // When
        final ClientHttpResponse actual = interceptor.intercept(request, bytes, execution);

        // Then
        verify(requestHeaders).add("Cookie", requestCookie1);
        verify(requestHeaders).add("Cookie", requestCookie2);
        verify(requestHeaders).add("Cookie", requestCookie3);
        assertThat(cookieStore, containsInAnyOrder(
            requestCookie1, requestCookie2, requestCookie3, responseCookie1, responseCookie2, responseCookie3
        ));
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_handle_no_cookies_being_returned_in_the_response() throws IOException {

        final HttpRequest request = mock(HttpRequest.class);
        final byte[] bytes = someBytes();
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

        final ClientHttpResponse expected = mock(ClientHttpResponse.class);
        final HttpHeaders responseHeaders = mock(HttpHeaders.class);

        // Given
        given(request.getHeaders()).willReturn(mock(HttpHeaders.class));
        given(execution.execute(request, bytes)).willReturn(expected);
        given(expected.getHeaders()).willReturn(responseHeaders);
        given(responseHeaders.get("Set-Cookie")).willReturn(null);

        // When
        final ClientHttpResponse actual = interceptor.intercept(request, bytes, execution);

        // Then
        assertThat(cookieStore, containsInAnyOrder(requestCookie1, requestCookie2, requestCookie3));
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_clear_the_cookies() {

        // When
        interceptor.clearCookies();

        // Then
        verify(cookieStore).clear();
    }

    private String cookie(String cookie) {
        return format("%s; %s; %s;", cookie, someAlphanumericString(), someAlphanumericString());
    }
}