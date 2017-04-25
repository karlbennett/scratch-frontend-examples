package scratch.frontend.examples.mustache;

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
    public CustomAuthorizeRequests mustacheCustomAuthorizeRequests() {
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
    public AuthenticationSuccessHandler loginDelegate() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public LogoutSuccessHandler logoutDelegate() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler mustacheAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/");
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/signIn");
    }
}
