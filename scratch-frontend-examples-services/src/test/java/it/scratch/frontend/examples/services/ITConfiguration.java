package it.scratch.frontend.examples.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;

import static java.util.Collections.singletonList;

@Configuration
@ComponentScan({"it.scratch.frontend.examples.services", "scratch.frontend.examples.services"})
@PropertySource("application-test.properties")
public class ITConfiguration {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CookieStoreRequestInterceptor cookieStoreRequestInterceptor;

    @Bean
    public CookieStoreRequestInterceptor cookieStoreRequestInterceptor() {
        return new CookieStoreRequestInterceptor(new HashSet<>());
    }

    @PostConstruct
    public void setup() {
        userRepository.save(new User(existingUser.getUsername(), existingUser.getPassword()));
        restTemplate.getRestTemplate().setInterceptors(singletonList(cookieStoreRequestInterceptor));
    }
}
