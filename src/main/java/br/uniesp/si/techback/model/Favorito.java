package br.uniesp.si.techback.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Favorito {
    private Usuarios usuarioId;
    private Filme filmeId;
    private LocalDateTime criadoEm;

}
