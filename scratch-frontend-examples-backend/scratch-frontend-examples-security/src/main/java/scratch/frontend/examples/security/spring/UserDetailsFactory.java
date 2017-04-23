package scratch.frontend.examples.security.spring;

import org.springframework.security.core.userdetails.UserDetails;
import scratch.frontend.examples.domain.User;

public interface UserDetailsFactory {

    UserDetails create(User user);
}
