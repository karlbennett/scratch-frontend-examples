package scratch.frontend.examples.vue;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class VueControllerConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // We must use a controller to map the root path.
        registry.addViewController("/").setViewName("/html/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/*").addResourceLocations("classpath:/META-INF/resources/css/");
        registry.addResourceHandler("/script/*").addResourceLocations("classpath:/javascript/");
        // Redirect all other paths to the index page.
        registry.addResourceHandler("/*", "/**/*").resourceChain(false)
            .addResolver(new ClasspathFileResourceResolver("/html/index.html"));
    }
}
