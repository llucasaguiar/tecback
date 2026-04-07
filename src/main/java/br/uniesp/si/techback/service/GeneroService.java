package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }

    public List<Genero> listar() {
        return generoRepository.findAll();
    }

    public Genero atualizar(Long id, Genero genero) {
        Optional<Genero> genero1 = generoRepository.findById(id);
        if (genero1.isEmpty()){
            throw new RuntimeException("Gênero inexistente");
        } else {
            return generoRepository.save(genero);
        }
    }

    public void excluir(Long id) {
        if(generoRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        generoRepository.deleteById(id);
    }

}
