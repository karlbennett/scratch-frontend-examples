package scratch.frontend.examples.htmljs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ControllerConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/signIn").setViewName("signIn");
        registry.addViewController("/registration-success").setViewName("register-success");
        registry.addViewController("/profile").setViewName("profile");
    }
}
