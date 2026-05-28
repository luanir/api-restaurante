package com.luan.controller;

import com.luan.entity.*;
import com.luan.dto.*;
//import com.example.service.*;
import com.luan.repository.*;
import com.luan.security.*;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	private final UsuarioRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	
	
	public AuthController (UsuarioRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
		System.out.println("CHEGOU NO LOGIN");
		Usuario usuario = userRepo.findByEmail(dto.getEmail())
				.orElseThrow(()-> new RuntimeException("Login inválido!"));
		
		if(!passwordEncoder.matches(dto.getSenha(),usuario.getSenha())) {
			throw new RuntimeException("Login inválido!");
		}
		String token = jwtService.generateToken(usuario.getEmail());
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> me(Authentication authentication){
		if(authentication == null) {
			return ResponseEntity.status(401).body(Map.of("erro", "Token ausente ou inválido"));
		}
		
		return ResponseEntity.ok(Map.of(
				"email", authentication.getName()));
	}
	
	
	
}
	
