package acceptance.scratch.frontend.examples.step;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.security.domain.User;

@Component
@Scope("cucumber-glue")
public class UserHolder extends GenericHolder<User> {
}
