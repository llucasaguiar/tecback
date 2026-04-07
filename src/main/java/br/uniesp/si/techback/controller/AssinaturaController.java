package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinaturas")
@RequiredArgsConstructor

public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @PostMapping
    public Assinatura salvar(Assinatura assinatura){
        return assinaturaService.Salvar(assinatura);
    }

    @GetMapping
    public List<Assinatura> listar(){
        return assinaturaService.listar();
    }

    @PutMapping("/{id}")
    public Assinatura atualizar(@PathVariable Long id,
                                @RequestBody Assinatura assinatura){
        return assinaturaService.atualizar(id, assinatura);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        assinaturaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
