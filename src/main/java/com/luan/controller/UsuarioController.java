package com.example.controller;

import java.util.List;
//import java.util.Optional;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
//import org.springframework.web.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.*;

import com.example.service.UsuarioService;
import com.example.entity.Usuario;
import com.example.enums.Role;
import com.example.dto.UsuarioRequestDTO;
import com.example.dto.UsuarioResponseDTO;


@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping //Criar Usuario
	public ResponseEntity<UsuarioResponseDTO> create(
			@Valid @RequestBody @NonNull UsuarioRequestDTO dto
		) {
		Usuario usuario = new Usuario();
		
		usuario.setNome(dto.getNome());
		usuario.setEmail(dto.getEmail());
		usuario.setSenha(dto.getSenha());
		usuario.setRoles(dto.getRoles());
		
		Usuario usuarioSalvo = usuarioService.create(usuario);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(
				usuarioSalvo.getId(),
				usuarioSalvo.getNome(),
				usuarioSalvo.getEmail(),
				usuarioSalvo.getRoles()
				
				
				);
		
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping //Buscar todos
	public ResponseEntity<List<UsuarioResponseDTO>> findAll(){
		List<UsuarioResponseDTO> response = usuarioService.findAll()
				.stream()
				.map(usuario -> new UsuarioResponseDTO(usuario.getId(),
						usuario.getNome(),
						usuario.getEmail(),
						usuario.getRoles()
						))
				.toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/nome/{nome}") //Buscar por nome
	public ResponseEntity<List<UsuarioResponseDTO>> findByNome(@PathVariable @NonNull String nome) {
		List<UsuarioResponseDTO> response = usuarioService.findByNome(nome)
				.stream()
				.map(usuario -> new UsuarioResponseDTO(usuario.getId(),
						usuario.getNome(),
						usuario.getEmail(),
						usuario.getRoles()
						))
				.toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}") //Buscar por ID
	public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable @NonNull Long id){
		
		Usuario usuario = usuarioService.findById(id)
				.orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}") //Deletar por ID
	public ResponseEntity<Void> delete(@PathVariable @NonNull Long id){
		usuarioService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/email/{email}") //Buscar por email
	public ResponseEntity<UsuarioResponseDTO> findByEmail(@PathVariable String email){
		
		Usuario usuario = usuarioService.findByEmail(email)
				.orElseThrow(()-> new RuntimeException ("Usuario não encontrado!"));
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
		
	}
	
	@GetMapping("/roles/{roles}")
	public ResponseEntity<List<UsuarioResponseDTO>> findByRoles(@PathVariable Role roles){
		List<UsuarioResponseDTO> response = usuarioService.findByRoles(roles)
				.stream()
				.map(usuario -> new UsuarioResponseDTO(usuario.getId(),
						usuario.getNome(),
						usuario.getEmail(),
						usuario.getRoles()
						))
				.toList();
		
		return ResponseEntity.ok(response);
	}
		
	@PatchMapping("/nome/{id}") //muda so o nome
	public ResponseEntity<UsuarioResponseDTO> updateNome(@PathVariable @NonNull Long id, @RequestBody Map<String, String> body){
		String nome = body.get("nome");
		
		Usuario usuario = usuarioService.updateNome(id, nome);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/email/{id}") //muda so o email
	public ResponseEntity<UsuarioResponseDTO> updateEmail(@PathVariable @NonNull Long id, @RequestBody Map<String, String> body){
		String email = body.get("email");
		
		Usuario usuario = usuarioService.updateEmail(id, email);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/senha/{id}") //muda so a senha
	public ResponseEntity<UsuarioResponseDTO> updateSenha(@PathVariable @NonNull Long id, @RequestBody Map<String, String> body){
		String senha = body.get("senha");
		
		Usuario usuario = usuarioService.updateSenha(id, senha);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/roles/{id}") //muda o role cliente, administrador
	public ResponseEntity<UsuarioResponseDTO> updateRoles(@PathVariable @NonNull Long id, @RequestBody Map<String, String > body){
		Role roles = Role.valueOf(body.get("roles"));
		
		Usuario usuario = usuarioService.updateRoles(id, roles);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}/roles/{roles}") //muda o roles pelo proprio patch sem precisar de json
	public ResponseEntity<UsuarioResponseDTO> updateRoles2(@PathVariable @NonNull Long id, @PathVariable Role roles){
		Usuario usuario = usuarioService.updateRoles(id, roles);
		
		UsuarioResponseDTO response = new UsuarioResponseDTO(usuario.getId(),
				usuario.getNome(),
				usuario.getEmail(),
				usuario.getRoles()
				);
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}") //muda tudo
	public ResponseEntity<UsuarioResponseDTO> updateAllInfoUsuario(@PathVariable @NonNull Long id,@Valid @RequestBody UsuarioRequestDTO dto){
		 Usuario usuarioAtualizado = usuarioService.updateAllInfoUsuario(
		            id,
		            dto.getNome(),
		            dto.getEmail(),
		            dto.getSenha(),
		            dto.getRoles()
		    );

		    UsuarioResponseDTO response = new UsuarioResponseDTO(
		            usuarioAtualizado.getId(),
		            usuarioAtualizado.getNome(),
		            usuarioAtualizado.getEmail(),
		            usuarioAtualizado.getRoles()
		    );

		    return ResponseEntity.ok(response);
	}
	
}
