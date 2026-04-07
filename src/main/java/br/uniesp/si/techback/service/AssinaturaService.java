package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;

    public Assinatura Salvar(Assinatura assinatura) {
        return assinaturaRepository.save(assinatura);
    }

    public List<Assinatura> listar() {
        return assinaturaRepository.findAll();
    }

    public Assinatura atualizar(Long id, Assinatura assinatura) {
        Optional<Assinatura> assin = assinaturaRepository.findById(id);
        if(assin.isEmpty()) {
            throw new RuntimeException("Assinatura inexistente.");
        } else {
            return assinaturaRepository.save(assinatura);
        }
    }

    public void excluir(Long id) {
        if(assinaturaRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        assinaturaRepository.deleteById(id);
    }
}
