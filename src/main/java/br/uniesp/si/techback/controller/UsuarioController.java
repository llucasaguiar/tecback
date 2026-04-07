package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuarios;
import br.uniesp.si.techback.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public Usuarios salvar(Usuarios usuarios){
        return usuarioService.Salvar(usuarios);
    }

    @GetMapping
    public List<Usuarios> listar(){
        return usuarioService.listar();
    }

    @PutMapping("/{id}")
    public Usuarios atualizar(@PathVariable Long id,
                                 @RequestBody Usuarios usuarios){
        return usuarioService.atualizar(id, usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
