package br.uniesp.si.techback.service;

import br.uniesp.si.techback.mapper.FavoritoMapper;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.repository.FavoritoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final FavoritoMapper favoritoMapper;

    @Transactional
    public Favorito salvar(Favorito favorito) {
        log.info("Salvando novo favorito: {}", favorito.getId());
        try {
            Favorito favoritoSalvo = favoritoRepository.save(favorito);
            log.info("Favorito salvo com sucesso. ID: {}, Titulo: {}", favoritoSalvo.getId(), favoritoSalvo.getFilme());
            return favoritoSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar favorito '{}': {}", favorito.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Favorito> listar() {
        log.info("Buscando todos os favoritos cadastradas");
        try {
            List<Favorito> favoritos = favoritoRepository.findAll();
            log.debug("Total de favoritos encontrados: {}", favoritos.size());
            return favoritos;
        } catch (Exception e) {
            log.error("Falha ao buscar favoritos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Favorito atualizar(Long id, Favorito favorito) {
        log.info("Atualizando favorito ID: {}", id);
        return favoritoRepository.findById(id)
                .map(favoritoExistente -> {
                    log.debug("Dados atuais do favorito: {}", favoritoExistente);
                    log.debug("Novos dados: {}", favorito);
                    favorito.setId(id);
                    Favorito favoritoAtualizado = favoritoRepository.save(favorito);
                    log.info("Favorito ID: {} atualizado com sucesso. Novo favorito: {}",
                            id, favoritoAtualizado.getId());
                    return favoritoAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: favorito não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo favorito ID: {}", id);
        if (!favoritoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: favorito não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            favoritoRepository.deleteById(id);
            log.info("Favorito ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir favorito ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
