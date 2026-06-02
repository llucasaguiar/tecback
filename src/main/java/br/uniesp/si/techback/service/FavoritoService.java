package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.FavoritoDTO;
import br.uniesp.si.techback.mapper.FavoritoMapper;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.repository.FavoritoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final FavoritoMapper favoritoMapper;

    @Transactional
    public FavoritoDTO salvar(FavoritoDTO favoritoDTO) {
        log.info("Salvando novo favorito: {}", favoritoDTO.getId());
        try {
            Favorito favorito = favoritoMapper.toEntity(favoritoDTO);
            Favorito favoritoSalvo = favoritoRepository.save(favorito);
            log.info("Favorito salvo com sucesso. ID: {}", favoritoSalvo.getId());
            return favoritoMapper.toDTO(favoritoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar favorito '{}': {}", favoritoDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<FavoritoDTO> listar() {
        log.info("Buscando todos os favoritos cadastrados");
        try {
            List<Favorito> favoritos = favoritoRepository.findAll();
            List<FavoritoDTO> favoritosDTO = favoritos.stream()
                    .map(favoritoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de favoritos encontrados: {}", favoritosDTO.size());
            return favoritosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar favoritos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public FavoritoDTO atualizar(Long id, FavoritoDTO favoritoDTO) {
        log.info("Atualizando favorito ID: {}", id);
        Favorito favoritoAtualizado = favoritoRepository.findById(id)
                .map(favoritoExistente -> {
                    log.debug("Dados atuais do favorito: {}", favoritoExistente);
                    log.debug("Novos dados: {}", favoritoDTO);
                    favoritoDTO.setId(id);
                    Favorito favoritoParaAtualizar = favoritoMapper.toEntity(favoritoDTO);
                    Favorito favoritoSalva = favoritoRepository.save(favoritoParaAtualizar);
                    log.info("Favorito ID: {} atualizado com sucesso. Novo favorito: {}",
                            id, favoritoSalva.getId());
                    return favoritoSalva;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: favorito não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return favoritoMapper.toDTO(favoritoAtualizado);
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
