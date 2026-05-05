package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    @Query("select f from Favorito f order by f.titulo")
    public List<Favorito> listarFilmesFavoritos();

}
