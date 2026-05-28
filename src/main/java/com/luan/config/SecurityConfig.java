package com.example.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.example.security.*;
@Configuration
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;
	
	public SecurityConfig (JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.csrf(csrf -> csrf.disable())
		.sessionManagement(sess -> 
		sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.requestMatchers("/", "/index.html", "/favicon.ico").permitAll()
				
				
				//Reservas
				
				.requestMatchers(HttpMethod.GET, "/reservas/**").permitAll()
				
				.requestMatchers("/reservas/**").hasRole("administrador")
				
			/*	.requestMatchers(HttpMethod.POST,"/reservas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PUT,"/reservas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PATCH,"/reservas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.DELETE,"/reservas/**").hasRole("administrador") */
				
				
				//Usuários
				.requestMatchers(HttpMethod.GET,"/usuarios/**").permitAll()
				
				.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll() // Depois de criado o primeiro admin, pode e tirar essa liberação!
				
				.requestMatchers("/usuarios/**").hasRole("administrador")
				
			/*	.requestMatchers(HttpMethod.POST,"/usuarios/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PUT,"/usuarios/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PATCH,"/usuarios/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.DELETE,"/usuarios/**").hasRole("administrador") */
				
				
				
				//Mesas
				.requestMatchers(HttpMethod.GET, "/mesas/**").permitAll()
				
				.requestMatchers("/mesas/**").hasRole("administrador")

			/*	.requestMatchers(HttpMethod.POST,"/mesas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PUT,"/mesas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.PATCH,"/mesas/**").hasRole("administrador")
				
				.requestMatchers(HttpMethod.DELETE,"/mesas/**").hasRole("administrador") */
				
				//Autenticação
				.requestMatchers("/auth/me").authenticated()
				
				.requestMatchers("/auth/login").permitAll()
				
				.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(List.of("*"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setExposedHeaders(List.of("Authorization"));
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
