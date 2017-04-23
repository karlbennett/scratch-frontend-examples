package scratch.frontend.examples.mustache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.mustache.controller.ProfileUserArgumentResolver;

import java.util.List;

@Configuration
public class ControllerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("homepage");
        registry.addViewController("/signIn").setViewName("signIn");
        registry.addViewController("/registration/success").setViewName("registration-success");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ProfileUserArgumentResolver(userRepository));
    }
}
