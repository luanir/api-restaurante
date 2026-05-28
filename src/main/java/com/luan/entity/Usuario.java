package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
//import com.fasterxml.jackson.annotation.JsonIgnore;

import com.example.enums.Role;

@Entity
@Table ( name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nome;
    
    @NotBlank
    @Email
    private String email;

    @NotBlank
    //@JsonIgnore
    private String senha;
    
    @Enumerated(EnumType.STRING)
    private Role roles;
    
    public Usuario() {//Construtor Vazio
    	
    }

    public Long getId() {//getter
    	return id;
    }
    
    public void setId(Long id) {//setter
    	this.id = id;
    }
    
    public String getNome() {//getter
    	return nome;
    }
    
    public void setNome(String nome) {//setter
    	this.nome = nome;
    }
    
    public String getEmail() {//getter
    	return email;
    }
    
    public void setEmail(String email) {//setter
    	this.email = email;
    }
    
    public String getSenha () {//getter
    	return senha;
    }
    
    public void setSenha(String senha) {//setter
    	this.senha = senha;
    }
    
    public Role getRoles() {
    	return roles;
    }
    
    public void setRoles(Role roles) {
    	this.roles = roles;
    }
    
    
}
