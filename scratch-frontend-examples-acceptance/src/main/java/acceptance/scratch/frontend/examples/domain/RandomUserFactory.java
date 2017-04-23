package acceptance.scratch.frontend.examples.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.services.data.UserRepository;
import scratch.frontend.examples.services.domain.User;

import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;

@Component
public class RandomUserFactory implements UserFactory {

    private final UserRepository userRepository;

    @Autowired
    public RandomUserFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createNew() {
        return new User(
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build(),
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build()
        );
    }

    @Override
    public User createExisting() {
        final scratch.frontend.examples.services.domain.User user = userRepository.save(new scratch.frontend.examples.services.domain.User(
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build(),
            buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build()
        ));

        return new User(user.getUsername(), user.getPassword());
    }
}
