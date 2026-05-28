package com.luan.controller;

import java.time.LocalDateTime;
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

import com.luan.service.ReservaService;
//import com.luan.enums.StatusMesa;
import com.luan.enums.StatusReserva;
import com.luan.entity.*;
import com.luan.dto.ReservaRequestDTO;
import com.luan.dto.ReservaResponseDTO;

@CrossOrigin (origins="*")
@RestController
@RequestMapping("/reservas")
public class ReservaController {

	private final ReservaService reservaService;
	
	public ReservaController (ReservaService reservaService) {
		this.reservaService = reservaService;
	}
	
	@PostMapping
	public ResponseEntity<ReservaResponseDTO> create(
	        @Valid @RequestBody @NonNull ReservaRequestDTO dto
	) {
	    Reserva reservaSalvo = reservaService.create(dto);

	    ReservaResponseDTO response = toDTO(reservaSalvo);

	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<ReservaResponseDTO>> findAll() {
	    List<ReservaResponseDTO> response = reservaService.findAll()
	            .stream()
	            .map(this::toDTO)
	            .toList();

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReservaResponseDTO> findById(@PathVariable @NonNull Long id) {
	    Reserva reserva = reservaService.findById(id)
	            .orElseThrow(() -> new RuntimeException("Reserva não encontrada!"));

	    return ResponseEntity.ok(toDTO(reserva));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity <Void> deleteById(@PathVariable @NonNull Long id) {
		reservaService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/data/{dataReserva}")
	public ResponseEntity<List<ReservaResponseDTO>> findByDataReserva(@PathVariable @NonNull LocalDateTime dataReserva){
		List<ReservaResponseDTO> response =  reservaService.findByDataReserva(dataReserva)
				.stream()
				.map(this::toDTO)
				.toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/mesa/{mesaId}")
	public ResponseEntity<List<ReservaResponseDTO>> findByMesaId(
	        @PathVariable @NonNull Long mesaId) {

	    List<ReservaResponseDTO> response = reservaService.findByMesaId(mesaId)
	            .stream()
	            .map(this::toDTO)
	            .toList();

	    return ResponseEntity.ok(response);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<ReservaResponseDTO>> findByUsuarioId (@PathVariable @NonNull Long usuarioId) {
		List<ReservaResponseDTO> response =  reservaService.findByUsuarioId(usuarioId)
				.stream()
				.map(this::toDTO)
				.toList();
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/status/{status}")
	public ResponseEntity<List<ReservaResponseDTO>> findByStatus (@PathVariable @NonNull StatusReserva status) {
		List<ReservaResponseDTO> response =  reservaService.findByStatus(status)
				.stream()
				.map(this::toDTO)
				.toList();
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/datareserva/{id}")
	public ResponseEntity<ReservaResponseDTO> updateDataReserva(@PathVariable @NonNull Long id, @RequestBody Map<String, LocalDateTime> body){
		LocalDateTime dataReserva = body.get("dataReserva");
		
		Reserva reserva = reservaService.updateDataReserva(id, dataReserva);
		
		return ResponseEntity.ok(toDTO(reserva));
		
	}
	
	@PatchMapping("/status/{id}") // ativo,cancelado
	public ResponseEntity<ReservaResponseDTO> updateStatus(@PathVariable @NonNull Long id, @RequestBody Map<String, String> body) {
		StatusReserva status = StatusReserva.valueOf(body.get("status"));
		
		Reserva reserva = reservaService.updateStatus(id, status);
		
		return ResponseEntity.ok(toDTO(reserva));
	}
	
	@PatchMapping("/{id}/status/{status}") //muda o status pelo proprio patch sem precisar de json
	public ResponseEntity<ReservaResponseDTO> updateStatus2(@PathVariable @NonNull Long id, @PathVariable StatusReserva status){
		Reserva reserva = reservaService.updateStatus(id, status);
		return ResponseEntity.ok(toDTO(reserva));
	}
	
	@PatchMapping("/{id}/cancelar")
	public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable @NonNull Long id) {
	    Reserva reserva = reservaService.cancelarReserva(id);
	    return ResponseEntity.ok(toDTO(reserva));
	}
	
	@PatchMapping("/{id}/reservar")
	public ResponseEntity<ReservaResponseDTO> reservarReserva(@PathVariable @NonNull Long id) {
	    Reserva reserva = reservaService.reservarReserva(id);
	    return ResponseEntity.ok(toDTO(reserva));
	}
	
	
	@SuppressWarnings("null")
	@PutMapping("/{id}")
	public ResponseEntity<ReservaResponseDTO> updateAllInfoReserva(@PathVariable @NonNull Long id,@Valid @RequestBody ReservaRequestDTO dto){
			Reserva reservaAtualizado = reservaService.updateAllInfoReserva(id,
					dto.getUsuarioId(),
					dto.getMesaId(),
					dto.getDataReserva()
					//dto.getStatus()
					);

			ReservaResponseDTO response = new ReservaResponseDTO(reservaAtualizado.getId(),
					reservaAtualizado.getUsuario().getId(),
					reservaAtualizado.getUsuario().getNome(),
					reservaAtualizado.getMesa().getId(),
					reservaAtualizado.getMesa().getNumero(),
					reservaAtualizado.getDataReserva(),
					reservaAtualizado.getStatus());
			
			return ResponseEntity.ok(response);
			
	}
	
	private ReservaResponseDTO toDTO(Reserva reserva) {
	    return new ReservaResponseDTO(
	            reserva.getId(),
	            reserva.getUsuario().getId(),
	            reserva.getUsuario().getNome(),
	            reserva.getMesa().getId(),
	            reserva.getMesa().getNumero(),
	            reserva.getDataReserva(),
	            reserva.getStatus()
	    );
	}
	
	
}
