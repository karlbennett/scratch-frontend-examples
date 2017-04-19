package cucumber.scratch.frontend.examples.page;

import cucumber.scratch.frontend.examples.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeleniumProfilePage implements ProfilePage {

    private final Finders finders;

    @Autowired
    public SeleniumProfilePage(Finders finders) {
        this.finders = finders;
    }

    @Override
    public String getUsername() {
        return finders.findByClassName("username").getText();
    }
}
