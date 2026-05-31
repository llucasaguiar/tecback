package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.service.FavoritoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor

public class FavoritoController {

    private final FavoritoService favoritoService;

    @GetMapping("/ordenando")
    public List<Favorito> listarFavoritos() {
        log.info("Listando todos os filmes favoritos");
        return favoritoService.listar();
    }

    @PostMapping
    public Favorito salvar(Favorito favorito){
        return favoritoService.Salvar(favorito);
    }

    @GetMapping
    public List<Favorito> listar(){
        return favoritoService.listar();
    }

    @PutMapping("/{id}")
    public Favorito atualizar(@PathVariable Long id,
                              @RequestBody Favorito favorito){
        return favoritoService.atualizar(id, favorito);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        favoritoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
