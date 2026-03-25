package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Funcionario;
import br.uniesp.si.techback.service.FuncionarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/funcionarios")

public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public Funcionario salvar(Funcionario funcionario){
        return funcionarioService.salvar(funcionario);
    }

    @GetMapping
    public List<Funcionario> listar(){
        return funcionarioService.listar();
    }

    @PutMapping("/{id}")
    public Funcionario atualizar(@PathVariable Long id,
                                 @RequestBody Funcionario funcionario){
        return funcionarioService.atualizar(id, funcionario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        funcionarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
