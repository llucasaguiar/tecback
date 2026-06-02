package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.repository.FuncionarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Transactional
    public Funcionario salvar(Funcionario funcionario) {
        log.info("Salvando novo funcionário: {}", funcionario.getId());
        try {
            Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
            log.info("Funcionário salvo com sucesso. ID: {}, Nome: {}", funcionarioSalvo.getId(), funcionarioSalvo.getNome());
            return funcionarioSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar funcionário '{}': {}", funcionario.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Funcionario> listar() {
        log.info("Buscando todos os funcionários cadastrados");
        try {
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            log.debug("Total de funcionários encontrados: {}", funcionarios.size());
            return funcionarios;
        } catch (Exception e) {
            log.error("Falha ao buscar funcionários: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Funcionario atualizar(Long id, Funcionario funcionario) {
        log.info("Atualizando funcionário ID: {}", id);
        return funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {
                    log.debug("Dados atuais de funcionário: {}", funcionarioExistente);
                    log.debug("Novos dados: {}", funcionario);
                    funcionario.setId(id);
                    Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionario);
                    log.info("Funcionário ID: {} atualizado com sucesso. Novo funcionário: {}",
                            id, funcionarioAtualizado.getId());
                    return funcionarioAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: funcionário não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
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
