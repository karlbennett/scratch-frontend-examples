package scratch.frontend.examples.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import scratch.frontend.examples.security.spring.CustomAuthorizeRequests;

@Configuration
public class MvcSecurityConfiguration {

    @Bean
    public CustomAuthorizeRequests mvcCustomAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests
            .antMatchers(
                "/",
                "/favicon.ico",
                "/scripts/*",
                "/css/*",
                "/registration",
                "/registration/success"
            ).permitAll()
            .anyRequest().authenticated();
    }

    @Bean
    public AuthenticationSuccessHandler mvnLoginDelegate() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public LogoutSuccessHandler mvnLogoutDelegate() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler mvcAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/");
    }

    @Bean
    public AuthenticationEntryPoint mvcAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/signIn");
    }
}
