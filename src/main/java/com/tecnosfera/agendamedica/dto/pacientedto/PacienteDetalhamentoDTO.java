package com.tecnosfera.agendamedica.dto.pacientedto;

import com.tecnosfera.agendamedica.model.Paciente;

public record PacienteDetalhamentoDTO(
        Long id,
        String nomeCompleto,
        String telefone,
        String email,
        String cpf,
        int idade
) {

    public PacienteDetalhamentoDTO(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNomeCompleto(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getCpf(),
                paciente.getIdade());
    }
}
