package com.caretrack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;//this enable methods security

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt for password hashing
        return new BCryptPasswordEncoder();
    }

     //here AM  is the central component that performs actual authentication of username/password (or tokens).
    //When a user logs in:Controller receives username/password-->You create an Authentication object (e.g., UsernamePasswordAuthenticationToken)
    // You pass that obj to AuthenticationManager
    //AuthenticationManager delegates authentication to:UserDetailsService,PasswordEncoder and AuthenticationProvider.If successful → returns an authenticated object
    //If not → throws exception
     @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        // Expose AuthenticationManager (delegates to configured UserDetailsService + PasswordEncoder)
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // "Cross-Origin Resource Sharing""""Enable CORS with permissive defaults for dev; tighten for prod ...Allows frontend (React/Angular/Postman) to call API.What this block does
                //✔ Allow all origins (*) from browser.mobile
                //✔ Allow all methods (GET, POST, PUT…)
                //✔ Allow all headers
                //✔ Allow Authorization header to be read by frontend.
                //Used for development.
                //In production you should restrict allowed origins.
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration c = new CorsConfiguration();
                c.setAllowedOrigins(List.of("*"));
                c.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                c.setAllowedHeaders(List.of("*"));
                c.setExposedHeaders(List.of("Authorization", "Content-Type"));
                c.setAllowCredentials(false);
                return c;
            }))
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs..CSRF only applies when you use browser forms + sessions.
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // H2 console needs frameOptions disabled
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Spring Security will NOT create session
                                                                                                   // Each request MUST contain a JWT
                                                                                                   //Backend does not store login status.This is mandatory for JWT authentication.
                // Authorization rules
            .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/api/auth/**").permitAll()  // Public endpoints..→ Anyone can call login API.no jwt needed..eg :for ;ogin ,register
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll() // for testing
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Health or status endpoints (optional)
                .requestMatchers("/actuator/health").permitAll()


                //web security customizer class is not supporting in spring security 6 made changes to overcome this issue
                    .requestMatchers(
                            "/favicon.ico",
                            "/assets/**",
                            "/error"
                    ).permitAll()


            // All other endpoints require authentication i.e jwt token
                    .anyRequest().authenticated()
            )
            // Add JWT filter...It tells Spring Security:"Before Spring tries to log in a user using username/password, first run my JWT filter."
                //Always check JWT token before checking username/password.
                //➡ If token is valid → authenticate user directly.Your JWT filter runs first → Valid token? OK->UsernamePasswordAuthenticationFilter is never needed
                //➡ If token missing → allow normal login process to continue.UsernamePasswordAuthenticationFilter handles login->Generates JWT
                //Returns to client
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            // Default exception handling
            .httpBasic(Customizer.withDefaults());

        return http.build(); //In Spring Security 6, every configuration must return a SecurityFilterChain bean.
    }

}
