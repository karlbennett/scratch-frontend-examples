package cucumber.scratch.frontend.examples.page;

import cucumber.scratch.frontend.examples.domain.User;
import cucumber.scratch.frontend.examples.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
