package com.mentorship.food_delivery_app.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Phase-1 security configuration.
 *
 * <p>Cart endpoints and Swagger UI are open so we can exercise them from
 * Postman / browsers without juggling rotating Basic-Auth passwords. Real
 * authentication (JWT / OAuth) belongs in a later phase — this filter chain
 * is the place where it'll plug in.
 *
 * <p>What each tweak does:
 * <ul>
 *   <li>{@code csrf.disable()} — we serve a stateless JSON API; CSRF tokens
 *       are not relevant.</li>
 *   <li>{@code SessionCreationPolicy.STATELESS} — no JSESSIONID, every
 *       request is independent.</li>
 *   <li>{@code anyRequest().authenticated()} — anything we did NOT explicitly
 *       permit (e.g. future actuator endpoints) stays default-deny.</li>
 *   <li>{@code httpBasic} — left enabled so locked-down routes still have a
 *       fallback authentication mechanism in dev.</li>
 * </ul>
 *
 * <p>You will still see "Using generated security password: ..." in the log
 * on startup. That belongs to Spring Boot's default in-memory user, which
 * remains so any non-permitted route can fall back to Basic Auth. It is not
 * required to hit the cart endpoints — ignore it during cart testing.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/v1/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(b -> {});

        return http.build();
    }
}
