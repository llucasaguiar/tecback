package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.GeneroDTO;
import br.uniesp.si.techback.mapper.GeneroMapper;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneroService {

    private final GeneroRepository generoRepository;
    private final GeneroMapper generoMapper;

    @Transactional
    public GeneroDTO salvar(GeneroDTO generoDTO) {
        log.info("Salvando novo gênero: {}", generoDTO.getId());
        try {
            Genero genero = generoMapper.toEntity(generoDTO);
            Genero generoSalvo = generoRepository.save(genero);
            log.info("Gênero salvo com sucesso. ID: {}", generoSalvo.getId());
            return generoMapper.toDTO(generoSalvo);
        } catch (Exception e) {
            log.error("Falha ao salvar gênero '{}': {}", generoDTO.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<GeneroDTO> listar() {
        log.info("Buscando todos os gêneros cadastrados");
        try {
            List<Genero> generos = generoRepository.findAll();
            List<GeneroDTO> generosDTO = generos.stream()
                    .map(generoMapper::toDTO)
                    .collect(Collectors.toList());
            log.debug("Total de gêneros encontrados: {}", generosDTO.size());
            return generosDTO;
        } catch (Exception e) {
            log.error("Falha ao buscar gêneros: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public GeneroDTO atualizar(Long id, GeneroDTO generoDTO) {
        log.info("Atualizando gênero ID: {}", id);
        Genero generoAtualizado = generoRepository.findById(id)
                .map(generoExistente -> {
                    log.debug("Dados atuais do gênero: {}", generoExistente);
                    log.debug("Novos dados: {}", generoDTO);
                    generoDTO.setId(id);
                    Genero generoParaAtualizar = generoMapper.toEntity(generoDTO);
                    Genero generoSalvo = generoRepository.save(generoParaAtualizar);
                    log.info("Gênero ID: {} atualizado com sucesso. Novo gênero: {}",
                            id, generoSalvo.getId());
                    return generoSalvo;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: gênero não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
        return generoMapper.toDTO(generoAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Excluindo gênero ID: {}", id);
        if (!generoRepository.existsById(id)) {
            String mensagem = String.format("Falha ao excluir: gênero não encontrado com o ID: %d", id);
            log.warn(mensagem);
            throw new RuntimeException(mensagem);
        }
        try {
            generoRepository.deleteById(id);
            log.info("Gênero ID: {} excluído com sucesso", id);
        } catch (Exception e) {
            log.error("Erro ao excluir gênero ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
