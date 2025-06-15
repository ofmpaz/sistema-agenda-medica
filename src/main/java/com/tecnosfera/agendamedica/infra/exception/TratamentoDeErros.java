package com.tecnosfera.agendamedica.infra.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class TratamentoDeErros {

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }

        @ExceptionHandler(RegraDeNegocioException.class)
        public ResponseEntity<String> tratarErroRegraDeNegocio(RegraDeNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException e) {
            List<FieldError> erros = e.getFieldErrors();
            List<DadosErroValidacao> listaDeErrosDTO = erros.stream().map(DadosErroValidacao::new).toList();
            return ResponseEntity.badRequest().body(listaDeErrosDTO);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<Void> tratarErro404() {
            return ResponseEntity.notFound().build();
        }
    }
}
