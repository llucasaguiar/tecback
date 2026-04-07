package br.uniesp.si.techback.controller;


import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.service.FuncionarioService;
import br.uniesp.si.techback.service.PlanoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planos")
@RequiredArgsConstructor

public class PlanoController {

    private final PlanoService planoService;

    @PostMapping
    public Plano salvar(Plano plano){
        return planoService.Salvar(plano);
    }

    @GetMapping
    public List<Plano> listar(){
        return planoService.listar();
    }

    @PutMapping("/{id}")
    public Plano atualizar(@PathVariable Long id,
                           @RequestBody Plano plano){
        return planoService.atualizar(id, plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        planoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
