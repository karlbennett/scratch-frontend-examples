package acceptance.scratch.frontend.examples.page;

import acceptance.scratch.frontend.examples.finder.Finders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.waiting.Wait;

@Component
public class SeleniumProfilePage implements ProfilePage {

    private final Finders finders;

    @Autowired
    public SeleniumProfilePage(Finders finders) {
        this.finders = finders;
    }

    @Wait(waitFor = StringNotEmpty.class)
    @Override
    public String getUsername() {
        return finders.findByClassName("main-heading").getText();
    }
}
