package com.luan.entity;

import com.luan.enums.StatusReserva;
import jakarta.persistence.*;
//import com.example.entity.*;
import java.time.LocalDateTime;

@Entity
@Table (name = "reservas")
public class Reserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="usuarios_id")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name="mesas_id")
	private Mesa mesa;
	
	private LocalDateTime dataReserva;
	
	@Enumerated(EnumType.STRING)
	private StatusReserva status;
	
	public Reserva() {
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Mesa getMesa() {
		return mesa;
	}
	
	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
	public LocalDateTime getDataReserva () { 
		return dataReserva;
	}
	
	public void setDataReserva (LocalDateTime dataReserva ) { 
		this.dataReserva = dataReserva;
	}
	
	public StatusReserva getStatus () {
		return status;
	}
	
	public void setStatus(StatusReserva status) {
		this.status = status;
	}
	
}
