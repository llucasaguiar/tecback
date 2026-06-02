package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Transactional
    public Genero salvar(Genero genero) {
        log.info("Salvando novo gênero: {}", genero.getId());
        try {
            Genero generoSalvo = generoRepository.save(genero);
            log.info("Gênero salvo com sucesso. ID: {}, Nome: {}", generoSalvo.getId(), generoSalvo.getNome());
            return generoSalvo;
        } catch (Exception e) {
            log.error("Falha ao salvar assinatura '{}': {}", genero.getId(), e.getMessage(), e);
            throw e;
        }
    }

    public List<Genero> listar() {
        log.info("Buscando todos os gêneros cadastrados");
        try {
            List<Genero> generos = generoRepository.findAll();
            log.debug("Total de gêneros encontrados: {}", generos.size());
            return generos;
        } catch (Exception e) {
            log.error("Falha ao buscar gêneros: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Genero atualizar(Long id, Genero genero) {
        log.info("Atualizando gênero ID: {}", id);
        return generoRepository.findById(id)
                .map(generoExistente -> {
                    log.debug("Dados atuais de gênero: {}", generoExistente);
                    log.debug("Novos dados: {}", genero);
                    genero.setId(id);
                    Genero generoAtualizado = generoRepository.save(genero);
                    log.info("Gênero ID: {} atualizado com sucesso. Novo gênero: {}",
                            id, generoAtualizado.getId());
                    return generoAtualizado;
                })
                .orElseThrow(() -> {
                    String mensagem = String.format("Falha ao atualizar: gênero não encontrado com o ID: %d", id);
                    log.warn(mensagem);
                    return new RuntimeException(mensagem);
                });
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
