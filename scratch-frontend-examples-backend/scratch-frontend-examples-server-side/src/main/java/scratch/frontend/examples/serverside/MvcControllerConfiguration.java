package scratch.frontend.examples.serverside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.serverside.controller.ProfileUserArgumentResolver;
import scratch.frontend.examples.serverside.controller.UsernameHandlerInterceptor;

import java.util.List;

@Configuration
public class MvcControllerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/signIn").setViewName("sign-in");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ProfileUserArgumentResolver(userRepository));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UsernameHandlerInterceptor());
    }
}
