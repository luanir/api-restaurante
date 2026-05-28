package com.example.repository;

import java.util.List;
//import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.*;
import com.example.enums.StatusMesa;

public interface MesaRepository extends JpaRepository <Mesa, Long>{
	List<Mesa> findByNumero (Integer numero);
	List<Mesa> findByCapacidade (Integer capacidade);
	List<Mesa> findByStatus(StatusMesa status);
	
}
