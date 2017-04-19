package cucumber.scratch.frontend.examples.page;

import cucumber.scratch.frontend.examples.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeleniumRegistrationSuccessPage implements RegistrationSuccessPage {

    private final Finders finders;

    @Autowired
    public SeleniumRegistrationSuccessPage(Finders finders) {
        this.finders = finders;
    }

    @Override
    public String getWelcome() {
        return finders.findTextByClassName("registration-success-heading");
    }

    @Override
    public String getMessage() {
        return finders.findTextByClassName("registration-success-message");
    }
}
