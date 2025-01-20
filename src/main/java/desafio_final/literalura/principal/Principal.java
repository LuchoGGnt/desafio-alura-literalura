package desafio_final.literalura.principal;

import desafio_final.literalura.entidades.autor.Autor;
import desafio_final.literalura.entidades.autor.IAutorRepository;
import desafio_final.literalura.entidades.autor.ListarAutorDTO;
import desafio_final.literalura.entidades.libro.*;
import desafio_final.literalura.services.API;
import desafio_final.literalura.services.ConsumoAPI;
import desafio_final.literalura.services.Conversor;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner input = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URLBase = "https://gutendex.com/books/?search=";
    private Conversor conversorDatos = new Conversor();
    private ILibroRepository libroRepository;

    private IAutorRepository autorRepository;
    private int anio;

    public Principal(ILibroRepository libroRepository, IAutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private void limpiarBuffer() {
        if (input.hasNextLine()) {
            input.nextLine();
        }
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ---------------------------------
                    \tLITERALURA
                    ---------------------------------
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos por año
                    5 - Listar libros por idioma
                    6 - Top libros más descargados
                    
                    0 - Salir
                    ---------------------------------
                    """;
            System.out.println(menu);
            System.out.println("Ingrese una opcion: ");
            var opcion2 = input.next();
            if (!validarOpcion(opcion2)) {
                continue;
            }
            opcion = Integer.parseInt(opcion2);
            System.out.println("============================");
            switch (opcion) {
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    topLibrosMasDescargados();
                    break;
                case 0:
                    System.out.println("Aplicación finalizada, que tenga un buen día.");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private boolean validarOpcion(String opcion) {
        try {
            Integer.parseInt(opcion);
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida");
            return false;
        }
        return true;
    }

    private List<LibroDTO> obtenerLibros() {
        while (true) {
            System.out.println("Ingrese el título del libro: ");
            var titulo = input.nextLine().trim(); // Captura el texto y elimina espacios innecesarios

            if (titulo.isEmpty()) {
                System.out.println("No ingresó un título. Por favor, intente nuevamente.");
                continue;
            }

            var url = URLBase + titulo.toLowerCase().replace(" ", "+");
            System.out.println("Buscando en URL: " + url);
            var json = consumoAPI.obtenerDatos(url);

            API respuesta = conversorDatos.obtenerDatos(json, API.class);
            List<LibroDTO> libros = respuesta.getResults();

            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros con el título proporcionado. Intente nuevamente.");
                continue;
            }

            return libros; // Devuelve la lista de libros encontrados
        }
    }


    private void registrarLibro(List<LibroDTO> libros) {
        while (true) {
            System.out.println("Libros encontrados:");

            for (int i = 0; i < libros.size(); i++) {
                System.out.println((i + 1) + " - " + libros.get(i).titulo());
            }

            System.out.println("Ingrese el número del libro que desea registrar (0 para salir):");
            var opcion1 = input.nextLine().trim();

            if (!validarOpcion(opcion1)) {
                continue;
            }

            var opcion2 = Integer.parseInt(opcion1);

            if (opcion2 == 0) {
                return;
            }

            if (opcion2 < 0 || opcion2 > libros.size()) {
                System.out.println("Opción inválida.");
                continue;
            }

            var libroDTO = libros.get(opcion2 - 1);

            var libroRegistrado = libroRepository.findByTitulo(libroDTO.titulo()).orElse(null);
            if(libroRegistrado!=null){
                System.out.println("El libro ya está registrado");
                continue;
            }

            Autor autor = null;
            if (libroDTO.autor() != null && !libroDTO.autor().isEmpty()) {
                var nombreAutor = libroDTO.autor().get(0).nombre();
                autor = autorRepository.findByNombre(nombreAutor).orElse(null);

                if (autor == null) {
                    autor = new Autor(libroDTO.autor().get(0));
                    autor = autorRepository.save(autor);
                }
            }

            var libro = new Libro(libroDTO);
            if (autor != null) {
                libro.setAutor(autor);
            }

            libroRepository.save(libro);
            System.out.println("Libro registrado con éxito.");
            return;
        }
    }

    private void buscarLibro() {
        var libros = obtenerLibros();
        registrarLibro(libros);
    }

    private void listarLibros() {
        var libros = libroRepository.findAll();

        System.out.println("Libros registrados:");
        var librosListar = libros.stream().map(ListarLibrosDTO::new).toList();

        librosListar.stream().forEach(System.out::println);
    }

    private void listarAutores() {
        var autores = autorRepository.findAll();

        System.out.println("Autores registrados:");
        var autoresListar = autores.stream().map(ListarAutorDTO::new).toList();

        autoresListar.stream().forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        while (true) {
            System.out.println("Ingrese el año (Digite 0 para cancelar):");
            var anio = input.nextLine().trim();

            if (anio.isEmpty() || !validarOpcion(anio)) {
                System.out.println("Digite un año válido.");
                continue;
            }

            var anioInt = Integer.parseInt(anio);

            if (anioInt == 0) {
                return;
            }
            if (anioInt < 0 || anioInt > LocalDate.now().getYear()) {
                System.out.println("Año inválido");
                continue;
            }
            var autores = autorRepository.buscarPorTiempo(anioInt);

            System.out.println("Autores vivos en " + anioInt + ":");
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el " + anioInt);
                continue;
            } else {
                var autoresListar = autores.stream()
                        .map(ListarAutorDTO::new)
                        .toList();
                autoresListar.forEach(System.out::println);
            }
        }
    }

    private void listarLibrosPorIdioma(){
        while(true){
            System.out.println("Idiomas disponibles:");
            for (var idioma : Idioma.values()) {
                System.out.println(idioma.getCodigo() + ".- " + idioma.name());
            }
            System.out.println("Ingrese el código del idioma (X para salir):");
            var idioma = input.nextLine().trim();
            if(idioma.equalsIgnoreCase("X")){
                return;
            }
            var idiomaEnum = Idioma.codigoDeIdioma(idioma);
            if(idiomaEnum == null){
                System.out.println("Idioma inválido");
                continue;
            }
            var libros = libroRepository.findAllByIdioma(idiomaEnum);
            System.out.println("Libros en " + idiomaEnum.name() + ":");
            var librosListar = libros.stream().map(ListarLibrosDTO::new).toList();
            librosListar.stream().forEach(System.out::println);
        }
    }

    private void topLibrosMasDescargados() {
        System.out.println("Ingrese el número de libros que desea ver:");
        limpiarBuffer();
        var numLibros = input.nextLine();

        if (!validarOpcion(numLibros)) {
            return;
        }

        if (Integer.parseInt(numLibros) <= 0) {
            System.out.println("Número inválido");
            return;
        }

        var libros = libroRepository.topLibrosMasDescargados(Integer.parseInt(numLibros));

        System.out.println("Top " + numLibros + " libros más descargados:");
        var librosListar = libros.stream()
                .map(ListarLibrosDTO::new)
                .toList();
        librosListar.forEach(System.out::println);
    }
}
