package it.scratch.frontend.examples;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.domain.User;

@Component
@ConfigurationProperties("user.existing")
public class ExistingUser extends User {
}
