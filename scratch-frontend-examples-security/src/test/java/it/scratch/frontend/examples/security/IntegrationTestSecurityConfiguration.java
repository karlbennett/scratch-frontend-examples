package it.scratch.frontend.examples.security;

import it.scratch.frontend.examples.ExistingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.security.data.UserRepository;
import scratch.frontend.examples.security.domain.User;

import javax.annotation.PostConstruct;

@Configuration
public class IntegrationTestSecurityConfiguration {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void setup() {
        userRepository.save(new User(existingUser.getUsername(), existingUser.getPassword()));
    }
}
