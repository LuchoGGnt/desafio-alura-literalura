package desafio_final.literalura.entidades.libro;

public record ListarLibrosDTO(
        String titulo,
        String autor,
        String idioma,
        Boolean copyrigth,
        int numDescargas
) {
    public ListarLibrosDTO(Libro libro){
        this(
                libro.getTitulo(),
                libro.getAutor().getNombre(),
                libro.getIdioma().toString(),
                libro.getCopyright(),
                libro.getNumeroDescargas());
    }
    public String toString(){
        return """
                Titulo: %s
                Autor: %s
                Idioma: %s
                Copyrigth: %s
                Numero de descargas: %d
                """.formatted(titulo, autor, idioma, copyrigth, numDescargas);
    }
}
