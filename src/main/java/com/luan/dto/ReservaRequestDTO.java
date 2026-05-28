package com.luan.dto;

//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//import jakarta.validation.constraints.Size;
//import com.example.enums.StatusReserva;

import java.time.LocalDateTime;

//import com.luan.entity.*;

public class ReservaRequestDTO {

    @NotNull(message = "O id do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O id da mesa é obrigatório")
    private Long mesaId;

    @NotNull(message = "Data e horário são obrigatórios")
    private LocalDateTime dataReserva;

  /*@NotNull(message = "O status é obrigatório")
    private StatusReserva status; */

    public ReservaRequestDTO() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getMesaId() {
        return mesaId;
    }

    public void setMesaId(Long mesaId) {
        this.mesaId = mesaId;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDateTime dataReserva) {
        this.dataReserva = dataReserva;
    }

   /* public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }*/
}