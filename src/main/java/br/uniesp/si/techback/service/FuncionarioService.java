package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FuncionarioDTO;
import br.uniesp.si.techback.mapper.FuncionarioMapper;
import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;


    @Transactional
    public FuncionarioDTO salvar(FuncionarioDTO funcionarioDTO) {
        log.info("Salvando novo funcionário: {}", funcionarioDTO.getId());
        try {
            Funcionario funcionario = funcionarioMapper.toEntity(funcionarioDTO);
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
            log.info("Funcionário salvo com sucesso. ID: {}", funcionarioSalvo.getId());
            return funcionarioMapper.toDTO(funcionarioSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar funcionário '{}': {}", funcionarioDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<FuncionarioDTO> listar() {
        log.info("Buscando todos os funcionários cadastrados");
        try {
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            List<FuncionarioDTO> funcionariosDTO = funcionarios.stream()
                    .map(funcionarioMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de funcionários encontrados: {}", funcionariosDTO.size());
            return funcionariosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar funcionários: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public FuncionarioDTO atualizar(Long id, FuncionarioDTO funcionarioDTO) {
        log.info("Atualizando funcionário ID: {}", id);
        Funcionario funcionarioAtualizado = funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {
                    log.debug("Dados atuais do funcionário: {}", funcionarioExistente);
                    log.debug("Novos dados: {}", funcionarioDTO);
                    funcionarioDTO.setId(id);
                    Funcionario funcionarioParaAtualizar = funcionarioMapper.toEntity(funcionarioDTO);
                    Funcionario funcionarioSalvo = funcionarioRepository.save(funcionarioParaAtualizar);
                    log.info("Funcionário ID: {} atualizado com sucesso. Novo funcionário: {}",
                            id, funcionarioSalvo.getId());
                    return funcionarioSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: funcionário não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return funcionarioMapper.toDTO(funcionarioAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo funcionário ID: {}", id);
        if (!funcionarioRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: funcionário não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            funcionarioRepository.deleteById(id);
            log.info("Funcionário ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir funcionário ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
