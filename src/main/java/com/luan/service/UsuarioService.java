package com.luan.service;

import static com.luan.exception.Exceptions.lanca;
import com.luan.repository.UsuarioRepository;
//import  com.example.exception.Exceptions;
import java.util.List;
import java.util.Optional;


import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.luan.entity.Usuario;
import com.luan.enums.Role;

@Service
public class UsuarioService {
	
	private final UsuarioRepository userRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService (UsuarioRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		//this.dto = dto;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public Usuario create(@NonNull Usuario usuario) {
			if (userRepo.existsByEmail(usuario.getEmail())) {
				lanca("Email já cadastrado!");
			}
			
			if (usuario.getSenha() == null) {
			    throw new RuntimeException("Senha é obrigatória!");
			}
			
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			
		return userRepo.save(usuario);
		
	}
	
	
	public List<Usuario> findByNome(String nome){
		return userRepo.findByNome(nome);
	}
	
	
	public List<Usuario> findAll(){
		return userRepo.findAll();
	}
	
	
	public void delete(@NonNull Long id) {
		 userRepo.deleteById(id);
	}
	
	
	public Optional<Usuario> findById(@NonNull Long id) {
		 return userRepo.findById(id);
	}
	
	
	public Optional<Usuario> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	public List<Usuario> findByRoles(Role roles){
		return userRepo.findByRoles(roles);
	}
	
	
	public Usuario updateNome(@NonNull Long id, String nome) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(lanca("Usuário não encontrado!"));
		usuario.setNome(nome);
		return userRepo.save(usuario);
	}
	
	
	public Usuario updateEmail(@NonNull Long id, String email) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(lanca("Usuário nao encontrado!"));
		usuario.setEmail(email);
		return userRepo.save(usuario);
	}
	
	
	public Usuario updateSenha(@NonNull Long id, String senha) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(lanca("Usuário não encontrado!"));
		usuario.setSenha(passwordEncoder.encode(senha));
			return userRepo.save(usuario);
	}
	
	
	public Usuario updateRoles(@NonNull Long id, Role roles) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(lanca("Usuário não encontrado!"));
		usuario.setRoles(roles);
		return userRepo.save(usuario);
	}
	
	
	public Usuario updateAllInfoUsuario (@NonNull Long id, String nome, String email, String senha, Role roles) {
		Usuario usuario = userRepo.findById(id)
				.orElseThrow(lanca("Usuário não encontrado!"));
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(passwordEncoder.encode(senha));
		usuario.setRoles(roles);
		
		return userRepo.save(usuario);
	}
	
}
