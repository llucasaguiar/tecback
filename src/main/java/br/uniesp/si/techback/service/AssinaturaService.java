package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.AssinaturaDTO;
import br.uniesp.si.techback.mapper.AssinaturaMapper;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final AssinaturaMapper assinaturaMapper;

    @Transactional
    public AssinaturaDTO salvar(AssinaturaDTO assinaturaDTO) {
        log.info("Salvando nova assinatura: {}", assinaturaDTO.getId());
        try {
            Assinatura assinatura = assinaturaMapper.toEntity(assinaturaDTO);
            Assinatura assinaturaSalva = assinaturaRepository.save(assinatura);
            log.info("Assinatura salva com sucesso. ID: {}", assinaturaSalva.getId());
            return assinaturaMapper.toDTO(assinaturaSalva);
        } catch (Exception e) {
            log.error("Falha ao salvar assinatura '{}': {}", assinaturaDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<AssinaturaDTO> listar() {
        log.info("Buscando todas as assinaturas cadastradas");
        try {
            List<Assinatura> assinaturas = assinaturaRepository.findAll();
            List<AssinaturaDTO> assinaturasDTO = assinaturas.stream()
                            .map(assinaturaMapper::toDTO)
                            .collect(Collectors.toList());
            log.debug("Total de assinaturas encontradas: {}", assinaturasDTO.size());
            return assinaturasDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar assinaturas: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public AssinaturaDTO atualizar(Long id, AssinaturaDTO assinaturaDTO) {
        log.info("Atualizando assinatura ID: {}", id);
        Assinatura assinaturaAtualizada = assinaturaRepository.findById(id)
                .map(assinaturaExistente -> {
                    log.debug("Dados atuais da assinatura: {}", assinaturaExistente);
                    log.debug("Novos dados: {}", assinaturaDTO);
                    assinaturaDTO.setId(id);
                    Assinatura assinaturaParaAtualizar = assinaturaMapper.toEntity(assinaturaDTO);
                    Assinatura assinaturaSalva = assinaturaRepository.save(assinaturaParaAtualizar);
                    log.info("Assinatura ID: {} atualizada com sucesso. Nova assinatura: {}",
                            id, assinaturaSalva.getId());
                    return assinaturaSalva;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: assinatura não encontrada com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return assinaturaMapper.toDTO(assinaturaAtualizada);
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
