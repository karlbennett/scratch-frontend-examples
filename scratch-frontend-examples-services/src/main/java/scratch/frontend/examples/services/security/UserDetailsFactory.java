package scratch.frontend.examples.services.security;

import org.springframework.security.core.userdetails.UserDetails;
import scratch.frontend.examples.services.domain.User;

public interface UserDetailsFactory {

    UserDetails create(User user);
}
