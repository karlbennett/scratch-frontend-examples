package scratch.frontend.examples.services;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import scratch.frontend.examples.security.spring.CustomAuthorizeRequests;

@Configuration
public class RestSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean(CustomAuthorizeRequests.class)
    public CustomAuthorizeRequests restCustomAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests
            .antMatchers("/register").permitAll()
            .anyRequest().authenticated();
    }
}
