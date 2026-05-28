package com.luan.repository;

import java.util.List;
//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luan.entity.*;
import com.luan.enums.StatusReserva;
import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository <Reserva, Long> {
	List<Reserva> findByDataReserva(LocalDateTime dataReserva);
	
	List<Reserva> findByMesa_Id( Long mesaId);
	List<Reserva> findByUsuario_Id(Long usuarioId);
	List<Reserva> findByStatus(StatusReserva status);
	
}
