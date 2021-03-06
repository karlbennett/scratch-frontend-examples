package scratch.frontend.examples.security.spring;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

@Component
public class RepositoryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsFactory userDetailsFactory;

    public RepositoryUserDetailsService(UserRepository userRepository, UserDetailsFactory userDetailsFactory) {
        this.userRepository = userRepository;
        this.userDetailsFactory = userDetailsFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);

        if (user != null) {
            return userDetailsFactory.create(user);
        }

        throw new UsernameNotFoundException("No user found for username: " + username);
    }
}
