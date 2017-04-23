package acceptance.scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scratch.frontend.examples.services.domain.User;

@Component
public class SeleniumRegistrationPage implements RegistrationPage {

    private final Finders finders;

    @Autowired
    public SeleniumRegistrationPage(Finders finders) {
        this.finders = finders;
    }

    @Override
    public void register(User user) {
        finders.setTextByLabel("Username", user.getUsername());
        finders.setTextByLabel("Password", user.getPassword());
        finders.clickByValue("Register");
    }
}
