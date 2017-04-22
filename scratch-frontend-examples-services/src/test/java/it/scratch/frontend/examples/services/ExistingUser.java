package it.scratch.frontend.examples.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("user.existing")
public class ExistingUser extends User {
}
