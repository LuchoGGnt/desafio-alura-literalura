package desafio_final.literalura.entidades.autor;

public record ListarAutorDTO(
        String nombre,
        String fechaNacimiento,
        String fechaDeMuerte
) {
    public ListarAutorDTO(Autor autor) {
        this(
                autor.getNombre(),
                autor.getFechaNacimiento(),
                autor.getFechaDeMuerte()
        );
    }
    public String toString() {
        return """
                Nombre: %s
                Fecha de nacimiento: %s
                Fecha de muerte: %s
                """.formatted(nombre, fechaNacimiento, fechaDeMuerte);
    }
}
