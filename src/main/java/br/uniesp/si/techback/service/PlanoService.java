package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanoService {
    private final PlanoRepository planoRepository;

    public Plano Salvar(Plano assinatura) {
        return planoRepository.save(assinatura);
    }

    public List<Plano> listar() {
        return planoRepository.findAll();
    }

    public Plano atualizar(Long id, Plano plano) {
        Optional<Plano> plano1 = planoRepository.findById(id);
        if(plano1.isEmpty()) {
            throw new RuntimeException("Plano inexistente.");
        } else {
            return planoRepository.save(plano);
        }
    }

    public void excluir(Long id) {
        if(planoRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        planoRepository.deleteById(id);
    }
}
