package com.example.ApiGateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiGatewayConfig {
	
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
					  .route(p -> p.path("/reservations/**")
							  	   .uri("lb://ReservationMicroservice"))
					  
					  .route(p -> p.path("/guest/**")
							  	   .uri("lb://ReservationMicroservice"))
					  
					  .route(p -> p.path("/rooms/**")
							  	   .uri("lb://RoomAndInventoryService"))
					  
					  .route(p -> p.path("/auth/**")
							       .uri("lb://AuthService"))
					  
					  .route(p -> p.path("/staff/**")
							  	   .uri("lb://StaffService"))
					  			   
					  .build();
					  
	}
	
}

