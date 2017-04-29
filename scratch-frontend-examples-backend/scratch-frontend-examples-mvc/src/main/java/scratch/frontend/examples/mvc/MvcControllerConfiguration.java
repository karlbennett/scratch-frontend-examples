package scratch.frontend.examples.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.mvc.controller.ProfileUserArgumentResolver;

import java.util.List;

@Configuration
public class MvcControllerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ProfileUserArgumentResolver(userRepository));
    }
}
