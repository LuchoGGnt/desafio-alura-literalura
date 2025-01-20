package desafio_final.literalura.entidades.libro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import desafio_final.literalura.entidades.autor.AutorDTO;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO(
        @JsonAlias("title")
        String titulo,
        @JsonAlias("authors")
        List<AutorDTO> autor,
        @JsonAlias("languages")
        List<String> idioma,
        @JsonAlias("copyright")
        Boolean copyright,
        @JsonAlias("download_count")
        int numeroDescargas
) {
}
