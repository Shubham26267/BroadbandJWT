package broadbandrecomendation.config;


import broadbandrecomendation.security.CustomUserDetailsService;
import broadbandrecomendation.security.JwtAuthenticationFilter;
import broadbandrecomendation.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          JwtTokenProvider jwtTokenProvider,
                          JwtAuthenticationEntryPoint unauthorizedHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    

    // Authentication provider using our CustomUserDetailsService
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // AuthenticationManager using provider
    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider provider) {
        return new ProviderManager(Collections.singletonList(provider));
    }

    // Security Filter Chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http.cors();
    	http
            .csrf().disable()

            // unauthorized handler
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
            .and()

            // No session stored
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            // Authorization rules 
            
            .authorizeHttpRequests()
                .requestMatchers("api/getAllPlans").permitAll()
                .requestMatchers("/api/auth/**").permitAll()   // login & register
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // admin operations
                .requestMatchers("/api/plans/**").hasAnyRole("ADMIN", "CUSTOMER")
                .requestMatchers("/api/recommend/**").hasRole("CUSTOMER")
                .requestMatchers("/api/compare/**").hasRole("CUSTOMER")
                .anyRequest().authenticated()
            .and()

            // Custom authentication provider
            .authenticationProvider(authenticationProvider(null));

        // Add JWT filter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
