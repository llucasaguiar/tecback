package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;

    @Transactional
    public MetodoPagamento salvar(MetodoPagamento metodoPagamento) {
        log.info("Salvando novo método de pagamento: {}", metodoPagamento.getId());
        try {
            MetodoPagamento metodoPagamentoSalvo = metodoPagamentoRepository.save(metodoPagamento);
            log.info("Método de pagamento salvo com sucesso. ID: {}, Descrição: {}", metodoPagamentoSalvo.getId(), metodoPagamentoSalvo.getDescricao());
            return metodoPagamentoSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar método de pagamento '{}': {}", metodoPagamento.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<MetodoPagamento> listar() {
        log.info("Buscando todos os métodos de pagamento cadastrados");
        try {
            List<MetodoPagamento> metodoPagamentos = metodoPagamentoRepository.findAll();
            log.debug("Total de gêneros encontrados: {}", metodoPagamentos.size());
            return metodoPagamentos;
        } catch (Exception e) {
            log.error("Falha ao buscar métodos de pagamentos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public MetodoPagamento atualizar(Long id, MetodoPagamento metodoPagamento) {
        log.info("Atualizando método de pagamento ID: {}", id);
        return metodoPagamentoRepository.findById(id)
                .map(metodoPagamentoExistente -> {
                    log.debug("Dados atuais de método de pagamento: {}", metodoPagamentoExistente);
                    log.debug("Novos dados: {}", metodoPagamento);
                    metodoPagamento.setId(id);
                    MetodoPagamento metodoPagamentoAtualizado = metodoPagamentoRepository.save(metodoPagamento);
                    log.info("Método de pagamento ID: {} atualizado com sucesso. Novo método de pagamento: {}",
                            id, metodoPagamentoAtualizado.getId());
                    return metodoPagamentoAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: método de pagamento não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo método de pagamento ID: {}", id);
        if (!metodoPagamentoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: método de pagamento não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            metodoPagamentoRepository.deleteById(id);
            log.info("Método de pagamento ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir método de pagamento ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
