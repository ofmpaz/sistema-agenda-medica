package com.tecnosfera.agendamedica.service;

import com.tecnosfera.agendamedica.dto.PacienteCadastroDTO;
import com.tecnosfera.agendamedica.infra.exception.RegraDeNegocioException;
import com.tecnosfera.agendamedica.model.Paciente;
import com.tecnosfera.agendamedica.repository.PacienteRepository;
import com.tecnosfera.agendamedica.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PacienteService {

    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    public PacienteService(PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void cadastrarPaciente(PacienteCadastroDTO dadosPacienteDTO) {
        logger.info("Iniciando cadastro de Paciente");

        if (usuarioRepository.findByCpf(dadosPacienteDTO.cpf()).isPresent()) {
            throw new RegraDeNegocioException("CPF já está cadastrado");
        }
        if (usuarioRepository.findByEmail(dadosPacienteDTO.email()).isPresent()) {
            throw new RegraDeNegocioException("Email já está cadastrado");
        } else {
            var paciente = Paciente.builder()
                    .nomeCompleto(dadosPacienteDTO.nomeCompleto())
                    .cpf(dadosPacienteDTO.cpf())
                    .email(dadosPacienteDTO.email())
                    .telefone(dadosPacienteDTO.telefone())
                    .idade(dadosPacienteDTO.idade())
                    .build();

            pacienteRepository.save(paciente);
            logger.info("Paciente com CPF: {} cadastrado com sucesso! ID: " +
                    "{}", dadosPacienteDTO.cpf(), paciente.getId());
        }
    }
}

