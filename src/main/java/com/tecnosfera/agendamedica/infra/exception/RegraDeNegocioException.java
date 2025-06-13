package com.tecnosfera.agendamedica.infra.exception;

public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super (mensagem);
    }
}
