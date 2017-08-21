package scratch.frontend.examples.hybrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import scratch.frontend.examples.hybrid.controller.PubliclyCachedHandlerInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.springframework.http.CacheControl.maxAge;

@Configuration
public class HybridControllerConfiguration extends WebMvcConfigurerAdapter {

    @Value("#{'${public.cache.paths}'.split(',')}")
    private List<String> paths;

    @Value("${public.cache.maxAge}")
    private long maxAge;

    @Value("${public.cache.maxAgeUnit}")
    private TimeUnit maxAgeUnit;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/components/page-sign-in")
                .setViewName("/components/page-sign-in");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").setCacheControl(maxAge(maxAge, maxAgeUnit))
                .addResourceLocations("classpath:/META-INF/resources/css/");
        registry.addResourceHandler("/webjars/**").setCacheControl(maxAge(maxAge, maxAgeUnit))
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PubliclyCachedHandlerInterceptor(paths, maxAge, maxAgeUnit));
    }
}
