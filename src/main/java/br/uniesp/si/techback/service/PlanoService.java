package br.uniesp.si.techback.service;

import br.uniesp.si.techback.mapper.PlanoMapper;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoService {

    private final PlanoRepository planoRepository;
    private final PlanoMapper planoMapper;

    @Transactional
    public Plano salvar(Plano plano) {
        log.info("Salvando novo plano: {}", plano.getId());
        try {
            Plano planoSalvo = planoRepository.save(plano);
            log.info("Plano salvo com sucesso. ID: {}, Nome: {}", planoSalvo.getId(), planoSalvo.getNome());
            return planoSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar plano '{}': {}", plano.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Plano> listar() {
        log.info("Buscando todos os planos cadastrados");
        try {
            List<Plano> planos = planoRepository.findAll();
            log.debug("Total de planos encontrados: {}", planos.size());
            return planos;
        } catch (Exception e) {
            log.error("Falha ao buscar planos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Plano atualizar(Long id, Plano plano) {
        log.info("Atualizando plano ID: {}", id);
        return planoRepository.findById(id)
                .map(planoExistente -> {
                    log.debug("Dados atuais de plano: {}", planoExistente);
                    log.debug("Novos dados: {}", plano);
                    plano.setId(id);
                    Plano planoAtualizado = planoRepository.save(plano);
                    log.info("Plano ID: {} atualizado com sucesso. Novo plano: {}",
                            id, planoAtualizado.getId());
                    return planoAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: plano não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
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
