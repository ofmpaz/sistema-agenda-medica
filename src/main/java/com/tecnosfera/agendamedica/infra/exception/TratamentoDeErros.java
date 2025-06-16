package com.tecnosfera.agendamedica.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Classe global para tratamento de exceções da API.
 * Captura exceções específicas e retorna respostas HTTP padronizadas e informativas.
 */
@RestControllerAdvice
public class TratamentoDeErros {

    /**
     * Trata a nossa exceção customizada de regras de negócio.
     * Este é o método que vai resolver o seu problema atual.
     *
     * @param ex A exceção capturada.
     * @return Uma resposta HTTP 400 (Bad Request) com a mensagem de erro específica.
     */
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> tratarErroRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    /**
     * Trata exceções quando a busca por um recurso não o encontra.
     * Ex: getReferenceById() para um ID que não existe.
     *
     * @return Uma resposta HTTP 404 (Not Found) sem corpo.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Trata os erros de validação do Bean Validation (acionados pelo @Valid).
     *
     * @param ex A exceção que contém todos os erros de validação.
     * @return Uma resposta HTTP 400 (Bad Request) com uma lista detalhada dos campos e erros.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        var listaDeErrosDTO = erros.stream().map(DadosErroValidacao::new).toList();

        return ResponseEntity.badRequest().body(listaDeErrosDTO);
    }

    /**
     * DTO auxiliar privado para formatar a resposta do erro de validação.
     * Fica no final da classe, fora dos outros métodos.
     */
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
