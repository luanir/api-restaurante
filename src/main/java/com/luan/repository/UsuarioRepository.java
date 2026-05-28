package com.luan.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


import com.luan.entity.*;
import com.luan.enums.Role;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail (String email);
	boolean existsByEmail(String email);
	List<Usuario> findByNome (String nome);
	List<Usuario> findByRoles (Role roles);
}
