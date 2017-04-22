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
import scratch.frontend.examples.services.domain.User;

import java.net.URISyntaxException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ITConfiguration.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ITUser {

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
    public void Can_retrieve_a_user_when_logged_in() throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        final String username = existingUser.getUsername();
        final String password = existingUser.getPassword();

        // Given
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        body.add("username", username);
        body.add("password", password);
        restTemplate.postForEntity("/signIn", new HttpEntity<>(body, headers), Void.class);

        // When
        final ResponseEntity<User> actual = restTemplate.getForEntity("/user", User.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(OK));
        final User user = actual.getBody();
        assertThat(user.getUsername(), equalTo(username));
        assertThat(user.getPassword(), equalTo(password));
    }

    @Test
    public void Cannot_retrieve_a_user_when_not_logged_in() throws URISyntaxException {

        // When
        final ResponseEntity<User> actual = restTemplate.getForEntity("/user", User.class);

        // Then
        assertThat(actual.getStatusCode(), equalTo(UNAUTHORIZED));
    }
}
