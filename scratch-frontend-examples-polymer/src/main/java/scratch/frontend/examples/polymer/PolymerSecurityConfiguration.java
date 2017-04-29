package scratch.frontend.examples.polymer;

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
public class PolymerSecurityConfiguration {

    @Bean
    public CustomAuthorizeRequests polymerCustomAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests
            .antMatchers(
                "/",
                "/favicon.ico",
                "/css/*",
                "/sign-in.html",
                "/registration.html",
                "/register",
                "/registration-success.html",
                "/webjars/**/*",
                "/components/**/*",
                "/page-layout",
                "/page-header"
            ).permitAll()
            .anyRequest().authenticated();
    }

    @Bean
    public AuthenticationSuccessHandler polymerLoginDelegate() {
        return new SimpleUrlAuthenticationSuccessHandler("/");
    }

    @Bean
    public LogoutSuccessHandler polymerLogoutDelegate() {
        return new SimpleUrlLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler polymerAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/");
    }

    @Bean
    public AuthenticationEntryPoint polymerAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/sign-in.html");
    }
}
