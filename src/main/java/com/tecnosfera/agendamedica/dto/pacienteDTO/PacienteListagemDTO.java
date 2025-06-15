package com.tecnosfera.agendamedica.dto.pacienteDTO;

import com.tecnosfera.agendamedica.model.Paciente;

public record PacienteListagemDTO(
        Long id,
        String nomeCompleto,
        String email,
        String telefone
) {

    public PacienteListagemDTO(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNomeCompleto(),
                paciente.getEmail(),
                paciente.getTelefone());
    }
}

