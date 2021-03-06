package it.scratch.frontend.examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;

import static java.util.Collections.singletonList;

@Configuration
@ComponentScan({"it.scratch.frontend.examples", "scratch.frontend.examples"})
@PropertySource("application-test.properties")
public class IntegrationTestConfiguration {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CookieStoreRequestInterceptor cookieStoreRequestInterceptor;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CookieStoreRequestInterceptor cookieStoreRequestInterceptor() {
        return new CookieStoreRequestInterceptor(new HashSet<>());
    }

    @PostConstruct
    public void setup() {
        restTemplate.getRestTemplate().setInterceptors(singletonList(cookieStoreRequestInterceptor));
        userRepository.save(new User(existingUser.getUsername(), existingUser.getPassword()));
    }
}
