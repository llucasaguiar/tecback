package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.MetodoPagamentoDTO;
import br.uniesp.si.techback.mapper.MetodoPagamentoMapper;
import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final MetodoPagamentoMapper metodoPagamentoMapper;

    @Transactional
    public MetodoPagamentoDTO salvar(MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Salvando novo método de pagamento: {}", metodoPagamentoDTO.getId());
        try {
            MetodoPagamento metodoPagamento = metodoPagamentoMapper.toEntity(metodoPagamentoDTO);
            MetodoPagamento metodoPagamentoSalvo = metodoPagamentoRepository.save(metodoPagamento);
            log.info("Método de pagamento salvo com sucesso. ID: {}", metodoPagamentoSalvo.getId());
            return metodoPagamentoMapper.toDTO(metodoPagamentoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar método de pagamento '{}': {}", metodoPagamentoDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<MetodoPagamentoDTO> listar() {
        log.info("Buscando todos os métodos de pagamento cadastrados");
        try {
            List<MetodoPagamento> metodosPagamento = metodoPagamentoRepository.findAll();
            List<MetodoPagamentoDTO> metodosPagamentoDTO = metodosPagamento.stream()
                    .map(metodoPagamentoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de métodos de pagamento encontrados: {}", metodosPagamentoDTO.size());
            return metodosPagamentoDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar métodos de pagamento: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public MetodoPagamentoDTO atualizar(Long id, MetodoPagamentoDTO metodoPagamentoDTO) {
        log.info("Atualizando método de pagamento ID: {}", id);
        MetodoPagamento metodoPagamentoAtualizado = metodoPagamentoRepository.findById(id)
                .map(metodoPagamentoExistente -> {
                    log.debug("Dados atuais do método de pagamento: {}", metodoPagamentoExistente);
                    log.debug("Novos dados: {}", metodoPagamentoDTO);
                    metodoPagamentoDTO.setId(id);
                    MetodoPagamento metodoPagamentoParaAtualizar = metodoPagamentoMapper.toEntity(metodoPagamentoDTO);
                    MetodoPagamento metodoPagamentoSalvo = metodoPagamentoRepository.save(metodoPagamentoParaAtualizar);
                    log.info("Método de pagamento ID: {} atualizado com sucesso. Novo método: {}",
                            id, metodoPagamentoSalvo.getId());
                    return metodoPagamentoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: método de pagamento não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return metodoPagamentoMapper.toDTO(metodoPagamentoAtualizado);
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
