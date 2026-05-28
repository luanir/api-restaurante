package com.example.service;

import static com.example.exception.Exceptions.lanca;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.example.repository.*;
import java.util.List;
import java.util.Optional;

import com.example.dto.ReservaRequestDTO;
import com.example.entity.*;
import java.time.LocalDateTime;

import com.example.enums.StatusMesa;
import com.example.enums.StatusReserva;

@Service
public class ReservaService {
	private final ReservaRepository reservaRepo;
	private final MesaRepository mesaRepo;
	private final UsuarioRepository userRepo;
	
	public ReservaService (ReservaRepository reservaRepo,MesaRepository mesaRepo,UsuarioRepository userRepo) {
		this.reservaRepo = reservaRepo;
		this.mesaRepo = mesaRepo;
		this.userRepo = userRepo;
	}
	
	public Reserva create(@NonNull ReservaRequestDTO dto) {
	    Usuario usuario = userRepo.findById(dto.getUsuarioId())
	            .orElseThrow(lanca("Usuário não encontrado"));

	    Mesa mesa = mesaRepo.findById(dto.getMesaId())
	            .orElseThrow(lanca("Mesa não encontrada"));

	    if (mesa.getStatus() != StatusMesa.disponivel) {
	        throw new RuntimeException("Essa mesa não está disponível para reserva!");
	    }

	    mesa.setStatus(StatusMesa.reservada);

	    Reserva reserva = new Reserva();
	    reserva.setUsuario(usuario);
	    reserva.setMesa(mesa);
	    reserva.setDataReserva(dto.getDataReserva());
	    reserva.setStatus(StatusReserva.ativo);
	    
	    
	    
	    return reservaRepo.save(reserva);
	}
	
	public Reserva cancelarReserva(@NonNull Long id) {
		Reserva reserva = reservaRepo.findById(id)
				.orElseThrow(lanca("Reserva não encontrada!"));
		Mesa mesa = reserva.getMesa();
		mesa.setStatus(StatusMesa.disponivel);
		
		reserva.setStatus(StatusReserva.cancelado);
		
		return reservaRepo.save(reserva);
	}
	
	public Reserva reservarReserva(@NonNull Long id) {
	    Reserva reserva = reservaRepo.findById(id)
	            .orElseThrow(lanca("Reserva não encontrada!"));

	    Mesa mesa = reserva.getMesa();

	    if (mesa.getStatus() != StatusMesa.disponivel) {
	        throw new RuntimeException("Essa mesa não está disponível para reserva!");
	    }

	    mesa.setStatus(StatusMesa.reservada);
	    reserva.setStatus(StatusReserva.ativo);

	    return reservaRepo.save(reserva);
	}
	
	public List<Reserva> findAll (){
		return reservaRepo.findAll();
	}
	
	public Optional<Reserva> findById(@NonNull Long id){
		return reservaRepo.findById(id);
	}
	
	public void deleteById(@NonNull Long id) {
		Reserva reserva = reservaRepo.findById(id)
				.orElseThrow(lanca("Reserva não encontrada!"));
		
		reserva.getMesa().setStatus(StatusMesa.disponivel);
		
		reservaRepo.delete(reserva);
	}
	
	public List<Reserva> findByDataReserva(LocalDateTime dataReserva){
		return reservaRepo.findByDataReserva(dataReserva);
	}
	
	public List<Reserva> findByMesaId(Long mesaId){
		return reservaRepo.findByMesa_Id(mesaId);
	}
	
	public List<Reserva> findByUsuarioId(Long usuarioId) {
		return reservaRepo.findByUsuario_Id(usuarioId);
	}
	
	public List<Reserva> findByStatus(StatusReserva status){
		return reservaRepo.findByStatus(status);
	}
	
	public Reserva updateDataReserva(@NonNull Long id, LocalDateTime dataReserva) {
		Reserva reserva = reservaRepo.findById(id)
				.orElseThrow(lanca("Reserva não encontrada!"));
		reserva.setDataReserva(dataReserva);
		return reservaRepo.save(reserva);
	}
	
	public Reserva updateStatus(@NonNull Long id, StatusReserva status) {
		Reserva reserva = reservaRepo.findById(id)
				.orElseThrow(lanca("Reserva não encontrada!"));
		reserva.setStatus(status);
		return reservaRepo.save(reserva);
	}
	
	public Reserva updateAllInfoReserva(
	        @NonNull Long id,
	        @NonNull Long usuarioId,
	        @NonNull Long mesaId,
	        LocalDateTime dataReserva
	) {
	    Reserva reserva = reservaRepo.findById(id)
	            .orElseThrow(lanca("Reserva não encontrada!"));

	    Usuario usuario = userRepo.findById(usuarioId)
	            .orElseThrow(lanca("Usuário não encontrado!"));

	    Mesa mesaNova = mesaRepo.findById(mesaId)
	            .orElseThrow(lanca("Mesa não encontrada!"));

	    Mesa mesaAntiga = reserva.getMesa();

	    if (!mesaNova.getId().equals(mesaAntiga.getId())) {
	        if (mesaNova.getStatus() != StatusMesa.disponivel) {
	            throw new RuntimeException("Essa mesa não está disponível para reserva!");
	        }

	        mesaAntiga.setStatus(StatusMesa.disponivel);
	        mesaNova.setStatus(StatusMesa.reservada);
	    }

	    reserva.setUsuario(usuario);
	    reserva.setMesa(mesaNova);
	    reserva.setDataReserva(dataReserva);

	    return reservaRepo.save(reserva);
	}
}
