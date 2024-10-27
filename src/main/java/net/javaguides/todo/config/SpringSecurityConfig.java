package net.javaguides.todo.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//basic authentication, ensures all HTTP requests are authenticated and sets up CSRF protection.
@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SpringSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //responsible for securing HTTP requests

        httpSecurity
                .csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/api/**")
                )
                .authorizeHttpRequests((authorize)->{ //ensures that all HTTP requests require authentication

                    authorize.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults());
        return httpSecurity.build(); //builds and returns the configured SecurityFilterChain object.
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder provided by Spring Security for encoding passwords.
        return new BCryptPasswordEncoder(); //strong hashing algorithm that is widely used for password hashing
    }

    @Bean
    public UserDetailsService userDetailsService(){
        PasswordEncoder encoder = passwordEncoder();

        UserDetails radwa = User.builder()
                .username("root")
                //.password("{noop}root")
                .password(encoder.encode("root")) // Encode the password
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(radwa, admin);
    }
}
