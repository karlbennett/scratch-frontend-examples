package scratch.frontend.examples.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.security.spring.CustomAuthorizeRequests;
import scratch.frontend.examples.security.spring.CustomHttpSecurity;

@Configuration
public class RestSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(CustomHttpSecurity.class)
    public CustomHttpSecurity customHttpSecurity() {
        return (http) -> http.antMatcher("/api/**/*");
    }

    @Bean
    @ConditionalOnMissingBean(CustomAuthorizeRequests.class)
    public CustomAuthorizeRequests customAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests
            .antMatchers("/api/register").permitAll()
            .anyRequest().authenticated();
    }
}
