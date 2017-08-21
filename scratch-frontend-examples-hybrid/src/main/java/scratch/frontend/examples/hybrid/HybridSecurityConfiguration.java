package scratch.frontend.examples.hybrid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.security.spring.CustomAuthorizeRequests;
import scratch.frontend.examples.security.spring.CustomHttpSecurity;

@Configuration
public class HybridSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(CustomAuthorizeRequests.class)
    public CustomAuthorizeRequests customAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/scripts/*",
                        "/css/*",
                        "/registration",
                        "/registration/success",
                        "/webjars/**",
                        "/components/*"
                ).permitAll()
                .anyRequest().authenticated();
    }
}
