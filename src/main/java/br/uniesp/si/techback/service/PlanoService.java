package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.PlanoDTO;
import br.uniesp.si.techback.mapper.PlanoMapper;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoService {

    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    @Transactional
    public PlanoDTO salvar(PlanoDTO planoDTO) {
        log.info("Salvando novo plano: {}", planoDTO.getId());
        try {
            Plano plano = planoMapper.toEntity(planoDTO);
            Plano planoSalvo = planoRepository.save(plano);
            log.info("Plano salvo com sucesso. ID: {}", planoSalvo.getId());
            return planoMapper.toDTO(planoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar plano '{}': {}", planoDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<PlanoDTO> listar() {
        log.info("Buscando todos os planos cadastrados");
        try {
            List<Plano> planos = planoRepository.findAll();
            List<PlanoDTO> planosDTO = planos.stream()
                    .map(planoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de planos encontrados: {}", planosDTO.size());
            return planosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar planos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public PlanoDTO atualizar(Long id, PlanoDTO planoDTO) {
        log.info("Atualizando plano ID: {}", id);
        Plano planoAtualizado = planoRepository.findById(id)
                .map(planoExistente -> {
                    log.debug("Dados atuais do plano: {}", planoExistente);
                    log.debug("Novos dados: {}", planoDTO);
                    planoDTO.setId(id);
                    Plano planoParaAtualizar = planoMapper.toEntity(planoDTO);
                    Plano planoSalvo = planoRepository.save(planoParaAtualizar);
                    log.info("Plano ID: {} updated com sucesso. Novo plano: {}",
                            id, planoSalvo.getId());
                    return planoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: plano não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return planoMapper.toDTO(planoAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo plano ID: {}", id);
        if (!planoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: plano não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            planoRepository.deleteById(id);
            log.info("Plano ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir plano ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
