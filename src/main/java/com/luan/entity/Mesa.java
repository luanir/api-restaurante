package com.luan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import com.luan.enums.StatusMesa;

@Entity
@Table (name= "mesas")
public class Mesa {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Min(1)
		private Integer numero;
		
		@Min(1)
		private Integer capacidade;
		
		@Enumerated(EnumType.STRING)
		private StatusMesa status;
		
		public Mesa() {
			
		}
		
		public Long getId() {//getter
	    	return id;
	    }
	    
	    public void setId(Long id) {//setter
	    	this.id = id;
	    }
		
		public Integer getNumero() {
			return numero;
		}
		
		public void setNumero(Integer numero) {
			this.numero = numero;
		}
		
		public Integer getCapacidade() {
			return capacidade;
		}
		
		public void setCapacidade(Integer capacidade) {
			this.capacidade = capacidade;
		}
		
		public StatusMesa getStatus () {
			return status;
		}
		
		public void setStatus(StatusMesa status) {
			this.status = status;
		}
}
