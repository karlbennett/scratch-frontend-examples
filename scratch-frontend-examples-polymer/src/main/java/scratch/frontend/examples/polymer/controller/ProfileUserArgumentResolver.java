package scratch.frontend.examples.polymer.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import scratch.frontend.examples.data.UserRepository;
import scratch.frontend.examples.domain.User;

import java.security.Principal;

/**
 * We use an argument resolver for the profile {@link User} instead of a
 * {@link org.springframework.web.bind.annotation.ModelAttribute} because it seems to be the only way from stopping the
 * {@link org.springframework.web.bind.annotation.SessionAttributes} {@code User} from being injected into the method.
 */
public class ProfileUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;

    public ProfileUserArgumentResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ProfileController.class.equals(parameter.getDeclaringClass()) &&
            User.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest request,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        final Principal principal = request.getUserPrincipal();

        if (principal == null) {
            return null;
        }

        return userRepository.findByUsername(principal.getName());
    }
}

