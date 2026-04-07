package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.repository.MetodoPagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetodoPagamentoService {

    private final MetodoPagamentoRepository metodoPagamentoRepository;

    public MetodoPagamento Salvar(MetodoPagamento metodoPagamento) {
        return metodoPagamentoRepository.save(metodoPagamento);
    }

    public List<MetodoPagamento> listar() {
        return metodoPagamentoRepository.findAll();
    }

    public MetodoPagamento atualizar(Long id, MetodoPagamento metodoPagamento) {
        Optional<MetodoPagamento> metPag = metodoPagamentoRepository.findById(id);
        if(metPag.isEmpty()) {
            throw new RuntimeException("Método de pagamento inexistente.");
        } else {
            return metodoPagamentoRepository.save(metodoPagamento);
        }
    }

    public void excluir(Long id) {
        if(metodoPagamentoRepository.existsById(id)){
            throw new RuntimeException("Id não encontrado.");
        }
        metodoPagamentoRepository.deleteById(id);
    }
}
