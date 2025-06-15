package com.tecnosfera.agendamedica.dto.MedicoDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record MedicoCadastroDTO(

        @NotBlank
        String nomeCompleto,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank
        String crm,

        @NotBlank
        String especialidade,

        @NotBlank
        String telefone
) {

}
