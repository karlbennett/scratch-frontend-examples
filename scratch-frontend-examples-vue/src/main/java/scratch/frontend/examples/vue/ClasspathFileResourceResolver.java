package scratch.frontend.examples.vue;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.AbstractResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This will resolve any path to the supplied file. The file must fall within the current class path.
 */
class ClasspathFileResourceResolver extends AbstractResourceResolver {

    private final String path;

    ClasspathFileResourceResolver(String path) {
        this.path = path;
    }

    @Override
    protected Resource resolveResourceInternal(
        HttpServletRequest request,
        String requestPath,
        List<? extends Resource> locations,
        ResourceResolverChain chain
    ) {
        return new ClassPathResource(path);
    }

    @Override
    protected String resolveUrlPathInternal(
        String resourceUrlPath,
        List<? extends Resource> locations,
        ResourceResolverChain chain
    ) {
        return resourceUrlPath;
    }
}
