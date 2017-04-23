package acceptance.scratch.frontend.examples.domain;

import scratch.frontend.examples.services.domain.User;

public interface UserFactory {

    User createNew();

    User createExisting();
}
