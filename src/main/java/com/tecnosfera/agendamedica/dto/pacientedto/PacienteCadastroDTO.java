package com.tecnosfera.agendamedica.dto.pacientedto;

import jakarta.validation.constraints.*;

public record PacienteCadastroDTO(

        @NotBlank
        String nomeCompleto,

        @Min(0)
        int idade,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
        String cpf,

        @NotBlank
        String telefone
) {
}
