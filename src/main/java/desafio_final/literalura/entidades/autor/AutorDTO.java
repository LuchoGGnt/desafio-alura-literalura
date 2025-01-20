package desafio_final.literalura.entidades.autor;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
        @JsonAlias("name")
        String nombre,
        @JsonAlias("birth_year")
        String fechaNacimiento,
        @JsonAlias("death_year")
        String fechaDeMuerte
) {
}
