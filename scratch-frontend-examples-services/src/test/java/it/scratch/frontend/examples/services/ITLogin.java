package it.scratch.frontend.examples.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URISyntaxException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITLogin {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CookieStoreRequestInterceptor cookieStoreRequestInterceptor;

    @Before
    public void setUp() {
        cookieStoreRequestInterceptor.clearCookies();
    }

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
        restTemplate.postForEntity("/signIn", entity, Void.class);

        // When
        final ResponseEntity<Void> actual = restTemplate.getForEntity("/user", Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
    }
}
