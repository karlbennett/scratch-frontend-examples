package scratch.frontend.examples.mustache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.frontend.examples.data.UserRepository;

@Configuration
public class ControllerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/signIn").setViewName("signIn");
        registry.addViewController("/registration-success").setViewName("register-success");
        registry.addViewController("/profile").setViewName("profile");
    }
}
