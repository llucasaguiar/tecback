package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {


    @Query("select f from Filme f order by f.titulo asc")
    public List<Filme> listarFilmeAsc();

    public List<Filme> findAllByOrderByTituloAsc();

    Optional<Filme> findByGenero(Genero genero);

    @Query("select f from Filme f where f.genero = :genero ")
    public List<Filme> buscarPorGenero(@Param("genero") String genero);

}
