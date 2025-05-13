package com.example.ApiGateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
<<<<<<< HEAD
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
=======
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;

>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean 
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception{
		
		return http.csrf(customizer -> customizer.disable())
<<<<<<< HEAD
					.cors(cust -> cust.configurationSource(corsConfigurationSource()))
=======
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
				   .authorizeExchange(exchange -> exchange.pathMatchers("/auth/login")
						   									.permitAll()
						   									.anyExchange()
						   									.authenticated())
				   .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				   .build();
	}
<<<<<<< HEAD
	
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:4200"); // Frontend URL (allow your frontend)
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedHeader("*");  // Allow any headers
        config.setAllowCredentials(true);  // Allow credentials (cookies, auth headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // Apply to all routes

        return  source;
    }
=======
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964

}
