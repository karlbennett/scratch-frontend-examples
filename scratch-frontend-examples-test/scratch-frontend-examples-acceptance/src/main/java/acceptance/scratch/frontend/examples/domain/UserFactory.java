package acceptance.scratch.frontend.examples.domain;

import scratch.frontend.examples.domain.User;

public interface UserFactory {

    User createNew();

    User createExisting();
}
