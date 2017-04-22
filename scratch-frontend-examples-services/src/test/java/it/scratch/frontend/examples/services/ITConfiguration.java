package it.scratch.frontend.examples.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan({"it.scratch.frontend.examples.services", "scratch.frontend.examples.services"})
public class ITConfiguration {

    @Autowired
    private ExistingUser existingUser;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void setup() {
        userRepository.save(new User(existingUser.getUsername(), existingUser.getPassword()));
    }
}
