package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.service.GeneroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    private static List<Genero> lista = new ArrayList<>();


    @PostMapping
    public Genero criar(Genero genero) {
            lista.add(genero);
        return genero;
    }

    @GetMapping
    public List<Genero> listar() {
        return lista;
    }

    @PutMapping
    public String atualizar() {
        return "Atulizar";
    }

    @DeleteMapping
    public String deletar() {
        return "Deletar";
    }

}
