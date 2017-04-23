package scratch.frontend.examples;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class must be in this package so that the Spring Data JPA scanning picks up the data and domain
 * classes.
 */
@Configuration
@ComponentScan({"it.scratch.frontend.examples", "scratch.frontend.examples"})
@EnableAutoConfiguration
public class UserRepositoryConfiguration {
}
