package com.luan.dto;

import com.luan.enums.Role;

public class UsuarioResponseDTO {
	private Long id;
	
	private String nome;
	
	private String email;
	
	private Role roles;
	
	public UsuarioResponseDTO (Long id, String nome, String email, Role roles) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public Role getRoles() {
		return roles;
	}
}
