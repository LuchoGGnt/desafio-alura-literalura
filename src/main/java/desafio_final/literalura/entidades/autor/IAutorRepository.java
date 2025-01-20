package desafio_final.literalura.entidades.autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anioInt AND (a.fechaDeMuerte IS NULL OR a.fechaDeMuerte >= :anioInt)")
    List<Autor> buscarPorTiempo(int anioInt);
}
