package com.luan.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
	
		private static final String SECRET="minha-chave-super-secreta-com-pelo-menos-32-caracteres";
		private static final long EXPIRATION = 1000 * 60 * 60 * 2; //2h
		
		
		private Key getSigningKey() {
			return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
		}
		
		public String generateToken(String email) {
			return Jwts.builder()
					.setSubject(email)
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
					.signWith(getSigningKey(),SignatureAlgorithm.HS256)
					.compact();
		}
		
		
		public String getEmail(String token) {
			return Jwts.parserBuilder()
					.setSigningKey(getSigningKey())
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
		}
		
		public boolean isValid(String token) {
			try {
				getEmail(token);
				
				return true;
			} catch (JwtException | IllegalArgumentException e) {
				return false;
			}
		}
}
