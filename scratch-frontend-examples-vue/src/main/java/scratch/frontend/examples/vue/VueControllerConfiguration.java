package scratch.frontend.examples.vue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.CacheControl.maxAge;

@Configuration
public class VueControllerConfiguration extends WebMvcConfigurerAdapter {

    @Value("${public.cache.maxAge:2}")
    private long maxAge;

    @Value("${public.cache.maxAgeUnit:MINUTES}")
    private TimeUnit maxAgeUnit;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // We must use a controller to map the root path.
        registry.addViewController("/").setViewName("/html/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
            .setCacheControl(maxAge(maxAge, maxAgeUnit))
            .addResourceLocations("classpath:/META-INF/resources/css/");
        registry.addResourceHandler("/script/**")
            .setCacheControl(maxAge(maxAge, maxAgeUnit))
            .addResourceLocations("classpath:/javascript/");
        // Redirect all other paths to the index page.
        registry.addResourceHandler("/*", "/**")
            .setCacheControl(maxAge(maxAge, maxAgeUnit))
            .resourceChain(false)
            .addResolver(new ClasspathFileResourceResolver("/html/index.html"));
    }
}
