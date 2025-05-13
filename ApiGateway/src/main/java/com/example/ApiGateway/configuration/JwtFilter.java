package com.example.ApiGateway.configuration;


import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.ApiGateway.entity.Role;
import com.example.ApiGateway.service.JwtService;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter, Ordered{
	
	@Autowired 
	private JwtService jwtService;
	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		ServerHttpRequest request = exchange.getRequest();
	
		String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		String jwt;
		String userEmail;
		System.out.println("here in filter");
<<<<<<< HEAD
		System.out.println(authHeader);
=======
>>>>>>> 7cd6b6941e801dc5b2cf4da7f8f3de81aaa09964
		
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			System.out.println("No Auth Header");
			return chain.filter(exchange);
			
		}
		
		System.out.println("here");
		jwt = authHeader.substring(7);
		
		userEmail = jwtService.extractUserName(jwt);
		
		System.out.println("Token "+jwt);
		System.out.println(userEmail);
		
		
		
		if(userEmail != null) {
			
			System.out.println("before token validation");
			Role role = null;
			
			String path = request.getURI().getPath();
			
			if(path.contains("admin")) 
				role = Role.ADMIN;
			
			else if(path.contains("manager") || path.contains("staff") || path.contains("/rooms"))
				role = Role.MANAGER;
			
			else
				role = Role.RECEPTIONIST;
			
			if(path.contains("/rooms/getAll") || path.contains("/rooms/filter"))
				role = Role.RECEPTIONIST;
				
			if(jwtService.isTokenValid(jwt, role)) {
				
				System.out.println("Authenticated");
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority(role.name())));
				
				return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
			}
			
		}
		
		return chain.filter(exchange);
	}



	@Override
	public int getOrder() {
		
		return -1;	//this negative value ensures that it will run before spring security filter
	}

}
	
