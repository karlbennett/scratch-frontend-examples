package scratch.frontend.examples.security.spring;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

public interface CustomHttpSecurity {

    void customise(HttpSecurity http);
}
