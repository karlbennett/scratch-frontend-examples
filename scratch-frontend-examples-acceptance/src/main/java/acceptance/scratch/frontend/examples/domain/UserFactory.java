package acceptance.scratch.frontend.examples.domain;

import scratch.frontend.examples.security.domain.User;

public interface UserFactory {

    User createNew();

    User createExisting();
}
