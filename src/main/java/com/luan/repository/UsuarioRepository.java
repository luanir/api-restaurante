package com.example.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.entity.*;
import com.example.enums.Role;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail (String email);
	boolean existsByEmail(String email);
	List<Usuario> findByNome (String nome);
	List<Usuario> findByRoles (Role roles);
}
