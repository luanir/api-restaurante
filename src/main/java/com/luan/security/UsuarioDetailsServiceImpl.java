package com.luan.security;

import com.luan.entity.Usuario;
import com.luan.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {
	
	private final UsuarioRepository userRepo;
	public UsuarioDetailsServiceImpl(UsuarioRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
			Usuario usuario = userRepo.findByEmail(email)
					.orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado!"));
			return org.springframework.security.core.userdetails.User
					.withUsername(usuario.getEmail())
					.password(usuario.getSenha())
					.roles(usuario.getRoles().name())
					.build();
	}
}
