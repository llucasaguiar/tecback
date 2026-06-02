package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;

    @Transactional
    public Assinatura salvar(Assinatura assinatura) {
        log.info("Salvando nova assinatura: {}", assinatura.getId());
        try {
            Assinatura assinaturaSalva = assinaturaRepository.save(assinatura);
            log.info("Assinatura salva com sucesso. ID: {}", assinaturaSalva.getId());
            return assinaturaSalva;
        } catch (Exception e) {
            log.error("Falha ao salvar assinatura '{}': {}", assinatura.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Assinatura> listar() {
        log.info("Buscando todas as assinaturas cadastradas");
        try {
            List<Assinatura> assinaturas = assinaturaRepository.findAll();
            log.debug("Total de assinaturas encontradas: {}", assinaturas.size());
            return assinaturas;
        } catch (Exception e) {
            log.error("Falha ao buscar assinaturas: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Assinatura atualizar(Long id, Assinatura assinatura) {
        log.info("Atualizando assinatura ID: {}", id);
        return assinaturaRepository.findById(id)
                .map(assinaturaExistente -> {
                    log.debug("Dados atuais da assinatura: {}", assinaturaExistente);
                    log.debug("Novos dados: {}", assinatura);
                    assinatura.setId(id);
                    Assinatura assinaturaAtualizada = assinaturaRepository.save(assinatura);
                    log.info("Assinatura ID: {} atualizada com sucesso. Nova assinatura: {}",
                            id, assinaturaAtualizada.getId());
                    return assinaturaAtualizada;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: assinatura não encontrada com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo assinatura ID: {}", id);
        if (!assinaturaRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: assinatura não encontrada com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            assinaturaRepository.deleteById(id);
            log.info("Assinatura ID: {} excluída com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir assinatura ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
