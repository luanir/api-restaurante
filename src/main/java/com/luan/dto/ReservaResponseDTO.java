package com.luan.dto;

import com.luan.enums.StatusReserva;


import java.time.LocalDateTime;

//import com.luan.entity.*;

public class ReservaResponseDTO {

	private Long id;
	
	private Long usuarioid;
	
	private String usuarioNome;
	
	private Long mesaid;
	
	private Integer mesaNumero;
	
	private LocalDateTime dataReserva;
	
	private StatusReserva status;
	
	public ReservaResponseDTO (Long id, Long usuarioid, String usuarioNome, Long mesaid, Integer mesaNumero, LocalDateTime dataReserva, StatusReserva status) {
		this.id = id;
		this.usuarioid = usuarioid;
		this.usuarioNome = usuarioNome;
		this.mesaid = mesaid;
		this.mesaNumero = mesaNumero;
		this.dataReserva = dataReserva;
		this.status = status;
	}
	
	public ReservaResponseDTO (Long id, String usuarioNome, Integer mesaNumero, LocalDateTime dataReserva, StatusReserva status) {
		this.id = id;
		this.usuarioNome = usuarioNome;
		this.mesaNumero = mesaNumero;
		this.dataReserva = dataReserva;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getUsuarioId () {
		return usuarioid;
	}
	
	public String getUsuarioNome() {
		return usuarioNome;
	}
	
	public Long getMesaId() {
		return mesaid;
	}
	
	public Integer getMesaNumero() {
		return mesaNumero;
	}
	
	public LocalDateTime getDataReserva() {
		return dataReserva;
		}
	
	public StatusReserva getStatus () {
		return status;
	}
}
