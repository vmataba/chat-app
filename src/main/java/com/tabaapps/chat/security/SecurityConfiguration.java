
package com.tabaapps.chat.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(customizer -> {
            customizer.requestMatchers("/login","/error","/")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        });
        security.cors(AbstractHttpConfigurer::disable);
        security.csrf(AbstractHttpConfigurer::disable);
        security.formLogin(AbstractAuthenticationFilterConfigurer::disable);
        security.httpBasic(customizer -> {
            customizer.init(security);
//            customizer.authenticationEntryPoint((request, response, authException) -> {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                response.setContentType("application/json");
//                response.getWriter().write("{\"code\":403,\"message\":\"Incorrect username or password\"}");
//            });
        });

        return security.build();
    }

    @Bean
    public PasswordEncoder buildPasswordEncoder(){
        return passwordEncoder;
    }

}
