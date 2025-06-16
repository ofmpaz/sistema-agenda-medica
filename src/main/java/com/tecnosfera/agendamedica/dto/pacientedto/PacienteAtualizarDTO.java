package com.tecnosfera.agendamedica.dto.pacientedto;

import com.tecnosfera.agendamedica.model.Paciente;

public record PacienteAtualizarDTO(

        Long id,
        String nomeCompleto,
        String telefone,
        String email,
        Integer idade


) {

}
