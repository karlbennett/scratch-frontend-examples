/*
 * Copyright 2015 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scratch.frontend.examples.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * This is the main class that Spring boot uses to start the stand alone application.
 */
@SpringBootApplication
public class ServletApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServletApplication.class);
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
        registration.setLoadOnStartup(1);
        return registration;
    }


    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
    }
}
