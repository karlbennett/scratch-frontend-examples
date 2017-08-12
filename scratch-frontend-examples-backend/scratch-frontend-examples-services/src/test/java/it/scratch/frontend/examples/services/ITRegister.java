package it.scratch.frontend.examples.services;

import it.scratch.frontend.examples.CookieStoreRequestInterceptor;
import it.scratch.frontend.examples.IntegrationTestConfiguration;
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
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static shiver.me.timbers.data.random.RandomStrings.someAlphanumericString;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = IntegrationTestConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITRegister {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieStoreRequestInterceptor cookieStoreRequestInterceptor;

    @Before
    public void setUp() {
        cookieStoreRequestInterceptor.clearCookies();
    }

    @Test
    public void Can_register_a_new_user() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        final String username = someAlphanumericString(8);
        final String password = someAlphanumericString(13);

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", username);
        body.add("password", password);

        // When
        final ResponseEntity<Void> actual = restTemplate
            .postForEntity("/api/register", new HttpEntity<>(body, headers), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
        final User user = userRepository.findByUsername(username);
        assertThat(user.getUsername(), equalTo(username));
        assertThat(user.getPassword(), equalTo(password));
    }

    @Test
    public void Can_register_a_new_user_with_json() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final Map<String, String> body = new HashMap<>();

        final String username = someAlphanumericString(8);
        final String password = someAlphanumericString(13);

        // Given
        headers.setContentType(APPLICATION_JSON);
        body.put("username", username);
        body.put("password", password);

        // When
        final ResponseEntity<Void> actual = restTemplate
            .postForEntity("/api/register", new HttpEntity<>(body, headers), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
        final User user = userRepository.findByUsername(username);
        assertThat(user.getUsername(), equalTo(username));
        assertThat(user.getPassword(), equalTo(password));
    }

    @Test
    public void Can_login_a_newly_registered_user() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        final String username = someAlphanumericString(8);
        final String password = someAlphanumericString(13);

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", username);
        body.add("password", password);
        restTemplate.postForEntity("/api/register", new HttpEntity<>(body, headers), Void.class);

        // When
        final ResponseEntity<Void> actual = restTemplate
            .postForEntity("/api/signIn", new HttpEntity<>(body, headers), Void.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
    }

    @Test
    public void Can_retrieve_a_newly_registered_user() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        final String username = someAlphanumericString(8);
        final String password = someAlphanumericString(13);

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", username);
        body.add("password", password);
        restTemplate.postForEntity("/api/register", new HttpEntity<>(body, headers), Void.class);
        restTemplate.postForEntity("/api/signIn", new HttpEntity<>(body, headers), Void.class);

        // When
        final ResponseEntity<User> actual = restTemplate.getForEntity("/api/user", User.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
        final User user = actual.getBody();
        assertThat(user.getUsername(), equalTo(username));
        assertThat(user.getPassword(), nullValue());
    }
}
