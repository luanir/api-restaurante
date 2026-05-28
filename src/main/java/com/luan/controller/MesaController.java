package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.service.MesaService;
import com.example.entity.Mesa;
import com.example.enums.StatusMesa;

@CrossOrigin (origins = "*")
@RestController
@RequestMapping("/mesas")
public class MesaController {
	private final MesaService mesaService;
	
	public MesaController (MesaService mesaService) {
		this.mesaService = mesaService;
	}
	
	@PostMapping //cria mesa
	public ResponseEntity<Mesa> create(
			@Valid @RequestBody @NonNull Mesa mesa) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(mesaService.create(mesa));
	}
	
	@GetMapping //busca todas as mesas
	public ResponseEntity<List<Mesa>> findAll(){
		return ResponseEntity.ok(mesaService.findAll());
	}
	
	@GetMapping("/{id}") //busca por id
	public ResponseEntity<Optional<Mesa>> findById(@PathVariable @NonNull Long id){
		return ResponseEntity.ok(mesaService.findById(id));
	}
	
	@DeleteMapping("/{id}") //deleta por id
	public ResponseEntity<Void> delete(@PathVariable @NonNull Long id){
		mesaService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/capacidade/{capacidade}") //busca por capacidade
	public ResponseEntity<List<Mesa>> findByCapacidade(@PathVariable @NonNull Integer capacidade){
		return ResponseEntity.ok(mesaService.findByCapacidade(capacidade));
	}
	
	@GetMapping("/status/{status}") //busca por status
	public ResponseEntity<List<Mesa>> findByStatus(@PathVariable @NonNull StatusMesa status){
		return ResponseEntity.ok(mesaService.findByStatus(status));
	}
	
	@PatchMapping("numero/{id}") //muda so o numero da mesa
	public ResponseEntity<Mesa> updateNumero(@PathVariable @NonNull Long id, @RequestBody Map<String, Integer> body){
		Integer numero = body.get("numero");
		
		if(numero == null) {
			return ResponseEntity.badRequest().build();// Evita NullPointerException se o JSON vier errado
		}
		
		return ResponseEntity.ok(mesaService.updateNumero(id, numero));
	}
	
	@PatchMapping("capacidade/{id}") //muda so a capacidade da mesa
	public ResponseEntity<Mesa> updateCapacidade(@PathVariable @NonNull Long id, @RequestBody Map<String, Integer> body) {
		Integer capacidade = body.get("capacidade");
		
		if (capacidade == null) {
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok(mesaService.updateCapacidade(id, capacidade));
	}
	
	@PatchMapping("/status/{id}") // disponivel, reservada, inativa
	public ResponseEntity<Mesa> updateStatus(@PathVariable @NonNull Long id, @RequestBody Map<String, String> body) {
		StatusMesa status = StatusMesa.valueOf(body.get("status"));
		return ResponseEntity.ok(mesaService.updateStatus(id, status));
	}
	
	@PatchMapping("/{id}/status/{status}")
	public ResponseEntity<Mesa> updateStatus2(@PathVariable @NonNull Long id, @PathVariable StatusMesa status){
		return ResponseEntity.ok(mesaService.updateStatus(id, status));
	}
	
	@PutMapping("/{id}") //muda tudo
	public ResponseEntity<Mesa> updateAllInfoMesa(@PathVariable @NonNull Long id, @RequestBody Mesa mesa){
		return ResponseEntity.ok(mesaService.updateAllInfoMesa(id,
				mesa.getNumero(),
				mesa.getCapacidade(),
				mesa.getStatus()
				)
				);
	}
	
}
