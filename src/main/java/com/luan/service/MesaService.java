package com.luan.service;

import static com.luan.exception.Exceptions.lanca;
import com.luan.repository.MesaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.luan.entity.Mesa;
import com.luan.enums.StatusMesa;

@Service
public class MesaService {
	
	private final MesaRepository mesaRepo;
	
	
	public MesaService (MesaRepository mesaRepo) {
		this.mesaRepo = mesaRepo;
	}
	
	
	public Mesa create (@NonNull Mesa mesa) {
		return mesaRepo.save(mesa);
	}
	
	
	public List<Mesa> findAll(){
		return mesaRepo.findAll();
	}
	
	
	public Optional<Mesa> findById(@NonNull Long id){
		return mesaRepo.findById(id);
	}
	
	
	public void deleteById(@NonNull Long id) {
		 mesaRepo.deleteById(id);
	}
	
	
	public List<Mesa> findByCapacidade(Integer capacidade){
		return mesaRepo.findByCapacidade(capacidade);
	}
	
	
	public List<Mesa> findByStatus(StatusMesa status){
		return mesaRepo.findByStatus(status);
	}
	
	
	public Mesa updateNumero(@NonNull Long id, Integer numero) {
		Mesa mesa = mesaRepo.findById(id)
				.orElseThrow(lanca("Mesa não encontrada!"));
		mesa.setNumero(numero);
		return mesaRepo.save(mesa);
	}
	
	
	public Mesa updateCapacidade(@NonNull Long id, Integer capacidade) {
		Mesa mesa = mesaRepo.findById(id)
				.orElseThrow(lanca("Mesa não encontrada!"));
		mesa.setCapacidade(capacidade);
		return mesaRepo.save(mesa);
	}
	
	
	public Mesa updateStatus(@NonNull Long id, StatusMesa status) {
		Mesa mesa = mesaRepo.findById(id)
				.orElseThrow(lanca("Mesa não encontrada!"));
		mesa.setStatus(status);
		return mesaRepo.save(mesa);
	}
	
	
	public Mesa updateAllInfoMesa(@NonNull Long id, Integer numero, Integer capacidade, StatusMesa status) {
		Mesa mesa = mesaRepo.findById(id)
				.orElseThrow(lanca("Mesa não encontrada!"));
		mesa.setNumero(numero);
		mesa.setCapacidade(capacidade);
		mesa.setStatus(status);
		
		return mesaRepo.save(mesa);
	}
	
}
