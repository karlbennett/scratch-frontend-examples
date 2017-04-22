package scratch.frontend.examples.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import scratch.frontend.examples.services.json.JacksonStreamingJsonParser;
import scratch.frontend.examples.services.jwt.JwtEncoder;
import scratch.frontend.examples.services.security.AuthenticationFactory;
import scratch.frontend.examples.services.security.CustomAuthorizeRequests;
import scratch.frontend.examples.services.security.Http200AuthenticationSuccessHandler;
import scratch.frontend.examples.services.security.Http401AuthenticationEntryPoint;
import scratch.frontend.examples.services.security.Http401AuthenticationFailureHandler;
import scratch.frontend.examples.services.security.JsonToFormUrlEncodedAuthenticationFilter;
import scratch.frontend.examples.services.security.JwtAuthenticationFilter;
import scratch.frontend.examples.services.security.JwtAuthenticationSuccessHandler;
import scratch.frontend.examples.services.security.SecurityContextHolder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthorizeRequests customAuthorizeRequests;

    @Autowired
    private AuthenticationFactory authenticationFactory;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private AuthenticationSuccessHandler delegate;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        final String loginPage = "/signIn";
        // The CSRF prevention is disabled because it greatly complicates the requirements for the sign in POST request.
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        customAuthorizeRequests.customise(
            http.authorizeRequests().antMatchers("/registration").permitAll()
        );
        http.addFilterBefore(
            new JsonToFormUrlEncodedAuthenticationFilter(new JacksonStreamingJsonParser(new ObjectMapper()), loginPage),
            UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(
            new JwtAuthenticationFilter(authenticationFactory, securityContextHolder),
            UsernamePasswordAuthenticationFilter.class
        );
        http.formLogin()
            .successHandler(new JwtAuthenticationSuccessHandler(jwtEncoder, delegate))
            .failureHandler(authenticationFailureHandler)
            .loginPage(loginPage).permitAll();
        http.logout().logoutUrl("/signOut").logoutSuccessUrl("/");
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /*
     * The @ConditionalOnMissingBean annotation used bellow allows the configured classes to be overridden by projects
     * that use this library. They can do this by simply adding their own implementation to the Spring context.
     */

    @Bean
    @ConditionalOnMissingBean(CustomAuthorizeRequests.class)
    public CustomAuthorizeRequests customAuthorizeRequests() {
        return (authorizeRequests) -> authorizeRequests.anyRequest().authenticated();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler delegate() {
        return new Http200AuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new Http401AuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationEntryPoint.class)
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new Http401AuthenticationEntryPoint();
    }
}
