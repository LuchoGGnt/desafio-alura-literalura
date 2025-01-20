package desafio_final.literalura.entidades.libro;

import desafio_final.literalura.entidades.autor.Autor;
import jakarta.persistence.*;

@Entity(name = "Libro")
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne(optional = true)
    @JoinColumn(name = "autor_id", referencedColumnName = "id", nullable = true)
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Boolean copyright;
    private int numeroDescargas;

    public Libro() { }

    public Libro(Long id, String titulo, Autor autor, Idioma idioma, Boolean copyright, int numeroDescargas) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.copyright = copyright;
        this.numeroDescargas = numeroDescargas;
    }

    public Libro(LibroDTO libroDTO) {
        this.titulo = libroDTO.titulo();
        if (libroDTO.autor() != null && !libroDTO.autor().isEmpty()) {
            this.autor = new Autor(libroDTO.autor().get(0));
        }
        this.idioma = Idioma.codigoDeIdioma(libroDTO.idioma().get(0));
        this.copyright = libroDTO.copyright();
        this.numeroDescargas = libroDTO.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }
}
