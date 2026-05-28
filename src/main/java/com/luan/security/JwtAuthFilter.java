package com.luan.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.luan.security.JwtService;
import com.luan.security.UsuarioDetailsServiceImpl;

import java.io.IOException;

@SuppressWarnings("unused")
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
		private final JwtService jwtService;
		private final UsuarioDetailsServiceImpl userDetailsService;
		
		public JwtAuthFilter (JwtService jwtService,  UsuarioDetailsServiceImpl userDetailsService) {
			this.jwtService = jwtService;
			this.userDetailsService = userDetailsService;
		}
		
		@Override
		protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException , IOException {
			String authHeader = request.getHeader("Authorization");
			
			try {
				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					
					if(jwtService.isValid(token)) {
						String email = jwtService.getEmail(token);
						
						var userDetails = userDetailsService.loadUserByUsername(email);
						
						var auth = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
					
						 SecurityContextHolder
                         .getContext()
                         .setAuthentication(auth);
					}
				}
			}catch(UsernameNotFoundException e){
				SecurityContextHolder.clearContext();
			}
			
			filterChain.doFilter(request, response);
		}
}
