package com.example.ApiGateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean 
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception{
		
		return http.csrf(customizer -> customizer.disable())
					.cors(cust -> cust.configurationSource(corsConfigurationSource()))
				   .authorizeExchange(exchange -> exchange.pathMatchers("/auth/login")
						   									.permitAll()
						   									.anyExchange()
						   									.authenticated())
				   .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				   .build();
	}
	
	
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

}
