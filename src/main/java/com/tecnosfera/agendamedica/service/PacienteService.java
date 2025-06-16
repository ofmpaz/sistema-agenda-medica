package com.tecnosfera.agendamedica.service;

import com.tecnosfera.agendamedica.dto.pacientedto.PacienteAtualizarDTO;
import com.tecnosfera.agendamedica.dto.pacientedto.PacienteCadastroDTO;
import com.tecnosfera.agendamedica.dto.pacientedto.PacienteDetalhamentoDTO;
import com.tecnosfera.agendamedica.dto.pacientedto.PacienteListagemDTO;
import com.tecnosfera.agendamedica.infra.exception.RegraDeNegocioException;
import com.tecnosfera.agendamedica.model.Paciente;
import com.tecnosfera.agendamedica.repository.PacienteRepository;
import com.tecnosfera.agendamedica.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PacienteService {

    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor para injeção de dependências.
     * O Spring injeta as instâncias dos repositórios necessários.
     *
     * @param pacienteRepository Repositório para operações de persistência com a entidade Paciente.
     * @param usuarioRepository  Repositório para operações de consulta na entidade base Usuario.
     */
    public PacienteService(PacienteRepository pacienteRepository, UsuarioRepository usuarioRepository) {
        this.pacienteRepository = pacienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Realiza o cadastro de um novo paciente no sistema após validar as regras de negócio.
     *
     * @param dadosPacienteDTO DTO contendo as informações para o cadastro do novo paciente.
     * @return DTO com os dados detalhados do paciente recém-criado.
     * @throws RegraDeNegocioException Lançada se o CPF ou o e-mail informados já estiverem cadastrados.
     */

    @Transactional
    public PacienteDetalhamentoDTO cadastrarPaciente(PacienteCadastroDTO dadosPacienteDTO) {
        logger.info("Iniciando processo de cadastro de Paciente com CPF: {}", dadosPacienteDTO.cpf());

        if (usuarioRepository.findByCpf(dadosPacienteDTO.cpf()).isPresent()) {
            throw new RegraDeNegocioException("CPF já está cadastrado");
        }
        if (usuarioRepository.findByEmail(dadosPacienteDTO.email()).isPresent()) {
            throw new RegraDeNegocioException("Email já está cadastrado");
        }
        var paciente = Paciente.builder()
                .nomeCompleto(dadosPacienteDTO.nomeCompleto())
                .cpf(dadosPacienteDTO.cpf())
                .email(dadosPacienteDTO.email())
                .telefone(dadosPacienteDTO.telefone())
                .idade(dadosPacienteDTO.idade())
                .build();

        pacienteRepository.save(paciente);
        logger.info("Paciente com CPF: {} cadastrado com sucesso! ID: {}",
                dadosPacienteDTO.cpf(), paciente.getId());

        return new PacienteDetalhamentoDTO(paciente);
    }

    /**
     * Retorna uma lista com os dados resumidos de todos os pacientes cadastrados.
     *
     * @return Uma {@link List} de {@link PacienteListagemDTO}.
     */
    @Transactional
    public List<PacienteListagemDTO> listarTodos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        List<PacienteListagemDTO> dtos = pacientes.stream()
                .map(PacienteListagemDTO::new)
                .toList();
        logger.info("Finalizada listagem de pacientes. Total de {} registros encontrados.", dtos.size());
        return dtos;
    }

    /**
     * Busca e retorna os dados detalhados de um paciente específico a partir do seu CPF.
     *
     * @param cpf O CPF (11 dígitos, sem formatação) do paciente a ser buscado.
     * @return Um {@link PacienteDetalhamentoDTO} com os dados completos do paciente.
     * @throws EntityNotFoundException Lançada se nenhum usuário for encontrado com o CPF informado.
     * @throws RegraDeNegocioException Lançada se o CPF informado pertencer a um Médico e não a um Paciente.
     */
    @Transactional
    public PacienteDetalhamentoDTO buscarPorCpf(String cpf) {
        var usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o CPF informado."));

        if (!(usuario instanceof Paciente)) {
            throw new RegraDeNegocioException("O CPF informado pertence a um Médico.");
        }

        logger.info("Paciente com CPF {} encontrado.", cpf);
        return new PacienteDetalhamentoDTO((Paciente) usuario);
    }

    @Transactional
    public PacienteDetalhamentoDTO atualizarInformacoes(PacienteAtualizarDTO dados) {
        logger.info("Iniciando atualização para o paciente de ID: {}", dados.id());

        var paciente = pacienteRepository.getReferenceById(dados.id());

        if (dados.nomeCompleto() != null) {
            paciente.setNomeCompleto(dados.nomeCompleto());
        }
        if (dados.email() != null) {
            var usuarioComNovoEmail = usuarioRepository.findByEmail(dados.email());
            if (usuarioComNovoEmail.isPresent() && !usuarioComNovoEmail.get().getId().equals(paciente.getId())) {
                throw new RegraDeNegocioException("Este e-mail já está em uso por outro usuário.");
            }
            paciente.setEmail(dados.email());
        }
        if (dados.telefone() != null) {
            paciente.setTelefone(dados.telefone());
        }
        if (dados.idade() != null) {
            paciente.setIdade(dados.idade());
        }

        logger.info("Paciente de ID: {} atualizado com sucesso.", dados.id());
        return new PacienteDetalhamentoDTO(paciente);
    }

    @Transactional
    public void excluir(Long id) {
        var paciente = pacienteRepository.getReferenceById(id);
        pacienteRepository.delete(paciente);
        logger.info("Paciente de ID: {} marcado como inativo com sucesso.", id);
    }
}

