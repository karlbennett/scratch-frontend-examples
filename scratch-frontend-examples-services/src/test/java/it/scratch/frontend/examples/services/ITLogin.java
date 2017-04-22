package it.scratch.frontend.examples.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static scratch.frontend.examples.services.security.JwtConstants.X_AUTH_TOKEN;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("/test.properties")
public class ITLogin {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Can_sign_in() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", existingUser.getUsername());
        body.add("password", existingUser.getPassword());

        // When
        final ResponseEntity<Void> actual = restTemplate
            .postForEntity("/signIn", new HttpEntity<>(body, headers), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
    }

    @Test
    public void Can_fail_to_sign_in() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", existingUser.getUsername());
        body.add("password", someAlphanumericString(10));

        // When
        final ResponseEntity<Void> actual = restTemplate
            .postForEntity("/signIn", new HttpEntity<>(body, headers), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(UNAUTHORIZED));
    }

    @Test
    public void Cannot_access_a_secure_url_when_not_signed_in() throws URISyntaxException {

        // When
        final ResponseEntity<Void> actual = restTemplate.getForEntity("/user", Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(UNAUTHORIZED));
    }

    @Test
    public void Can_access_a_secure_url_when_signed_in() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", existingUser.getUsername());
        body.add("password", existingUser.getPassword());
        final HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
        final ResponseEntity<Void> login = restTemplate.postForEntity("/signIn", entity, Void.class);
        final HttpHeaders secureHeaders = new HttpHeaders();
        secureHeaders.add("Cookie", extractSessionCookie(login));

        // When
        final ResponseEntity<Void> actual = restTemplate
            .exchange("/user", GET, new HttpEntity<Void>(secureHeaders), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
    }

    private String extractSessionCookie(ResponseEntity<Void> response) {
        final List<String> cookies = response.getHeaders().get("Set-Cookie");
        return cookies.stream().filter(s -> s.contains(X_AUTH_TOKEN)).findFirst()
            .map(s -> s.split(";")[0])
            .orElseThrow(() -> new IllegalStateException("Could not find the session cookie."));
    }
}
