package desafio_final.literalura.entidades.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ILibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE l.idioma = :idiomaEnum")
    Optional<Libro> findAllByIdioma(Idioma idiomaEnum);

    Optional<Libro> findByTitulo(String titulo);

    @Query(value = "SELECT * FROM Libro l ORDER BY l.numDescargas DESC LIMIT :i", nativeQuery = true)
    List<Libro> topLibrosMasDescargados(@Param("i") int i);
}
