package com.luan.repository;

import java.util.List;
//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.luan.entity.*;
import com.luan.enums.StatusMesa;

public interface MesaRepository extends JpaRepository <Mesa, Long>{
	List<Mesa> findByNumero (Integer numero);
	List<Mesa> findByCapacidade (Integer capacidade);
	List<Mesa> findByStatus(StatusMesa status);
	
}
