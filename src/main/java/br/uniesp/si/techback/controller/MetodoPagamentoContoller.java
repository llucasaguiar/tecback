package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.MetodoPagamento;
import br.uniesp.si.techback.service.MetodoPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodoPagamento")
@RequiredArgsConstructor

public class MetodoPagamentoContoller {

    private final MetodoPagamentoService metodoPagamentoService;

    @PostMapping
    public MetodoPagamento salvar(MetodoPagamento metodoPagamento){
        return metodoPagamentoService.Salvar(metodoPagamento);
    }

    @GetMapping
    public List<MetodoPagamento> listar(){
        return metodoPagamentoService.listar();
    }

    @PutMapping("/{id}")
    public MetodoPagamento atualizar(@PathVariable Long id,
                                     @RequestBody MetodoPagamento metodoPagamento){
        return metodoPagamentoService.atualizar(id, metodoPagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        metodoPagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
