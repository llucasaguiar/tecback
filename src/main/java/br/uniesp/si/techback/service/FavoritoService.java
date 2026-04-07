package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public Favorito Salvar(Favorito assinatura) {
        return favoritoRepository.save(assinatura);
    }

    public List<Favorito> listar() {
        return favoritoRepository.findAll();
    }

    public Favorito atualizar(Long id, Favorito assinatura) {
        Optional<Favorito> fav = favoritoRepository.findById(id);
        if(fav.isEmpty()) {
            throw new RuntimeException("Favorito inexistente.");
        } else {
            return favoritoRepository.save(assinatura);
        }
    }

    public void excluir(Long id) {
        if(favoritoRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        favoritoRepository.deleteById(id);
    }
}
