package scratch.frontend.examples.services;

import it.scratch.frontend.examples.services.CookieStoreRequestInterceptor;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomBytes.someBytes;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

public class CookieStoreRequestInterceptorTest {

    @Test
    public void Can_store_any_cookies() throws IOException {

        @SuppressWarnings("unchecked") final List<String> cookieStore = mock(List.class);
        final HttpRequest request = mock(HttpRequest.class);
        final byte[] bytes = someBytes();
        final ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);

        final ClientHttpResponse expected = mock(ClientHttpResponse.class);
        final HttpHeaders headers = mock(HttpHeaders.class);
        final String cookie1 = someAlphanumericString();
        final String cookie2 = someAlphanumericString();
        final String cookie3 = someAlphanumericString();

        // Given
        given(execution.execute(request, bytes)).willReturn(expected);
        given(expected.getHeaders()).willReturn(headers);
        given(headers.get("Set-Cookie")).willReturn(asList(cookie(cookie1), cookie(cookie2), cookie(cookie3)));

        // When
        final ClientHttpResponse actual = new CookieStoreRequestInterceptor(cookieStore)
            .intercept(request, bytes, execution);

        // Then
        verify(headers).add("Cookie", cookie1);
        verify(headers).add("Cookie", cookie2);
        verify(headers).add("Cookie", cookie3);
        verify(cookieStore).add(cookie1);
        verify(cookieStore).add(cookie2);
        verify(cookieStore).add(cookie3);
        assertThat(actual, is(expected));
    }

    private String cookie(String cookie) {
        return format("%s; %s; %s;", cookie, someAlphanumericString(), someAlphanumericString());
    }
}