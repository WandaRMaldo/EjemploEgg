/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.servicio;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import jpa.entidad.Autor;
import jpa.entidad.Editorial;
import jpa.entidad.Libro;
import jpa.excepcion.MiExcepcion;
import jpa.persistencia.LibroDAO;
import jpa.utilidad.Utilidad;

/**
 *
 * @author WANDA
 */
public class LibroService {
    
    private Scanner entrada;
    private LibroDAO libroDAO;
    private AutorService autorService;
    private EditorialService editorialService;

    public LibroService() {
        entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n");
        libroDAO = new LibroDAO();
        autorService = new AutorService();
        editorialService = new EditorialService();
    }

    public void crearLibro(Long isbn, String titulo, Integer ano, Integer ejemplares, Integer prestados, Integer restantes, Autor autor, Editorial editorial) throws MiExcepcion {
        try {
            Libro libro = new Libro();

            libro.setIsbn(isbn);
            libro.setTitulo(titulo);
            libro.setAno(ano);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(prestados);
            libro.setEjemplaresRestantes(restantes);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            libroDAO.guardarLibro(libro);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public void lecturaDeDatos() throws MiExcepcion { //creo un nuevo libro
        try {
            List<Autor> autores = autorService.obtenerAutores();
            List<Editorial> editoriales = editorialService.obtenerEditoriales();
            Autor autor = null;
            Editorial editorial = null;
            Long isbn;
            int cantidadDeAutores = autores.size(); //size: retorna el numero de elementos en la lista
            int cantidadDeEditoriales = editoriales.size();
            int respuestaAutor = 0;
            int respuestaEditorial = 0;

            do {
                isbn = Long.parseLong(Utilidad.generarIsbn());
            } while (libroDAO.buscarLibroPorIsbm(isbn) != null);

            System.out.println("INGRESE EL TÍTULO DEL LIBRO: ");
            String titulo = Utilidad.obtenerCampo();

            System.out.println("INGRESE EL AÑO: ");
            Integer ano = Integer.valueOf(Utilidad.obtenerAno());

            Integer ejemplares = (int) (Math.random() * 4 + 3);
            Integer prestados = (int) (Math.random() * 3 + 1);
            Integer restantes = ejemplares - prestados;

            if (!autores.isEmpty()) {
                respuestaAutor = lecturaDeDatosAutor(cantidadDeAutores);
            }

            if (respuestaAutor == (cantidadDeAutores + 1) || autores.isEmpty()) {
                /*si entra aca es xq elijio crear un nuevo autor o xq la lista esta vacia*/
                autor = autorService.lecturaDeDatos();
            } else {
                autor = autores.get(respuestaAutor - 1);
            }

            if (!editoriales.isEmpty()) {
                respuestaEditorial = lecturaDeDatosEditorial(cantidadDeEditoriales);
            }

            if (respuestaEditorial == (cantidadDeEditoriales + 1) || editoriales.isEmpty()) {
                editorial = editorialService.lecturaDeDatos();
            } else {
                editorial = editoriales.get(respuestaEditorial - 1);
            }

            crearLibro(isbn, titulo, ano, ejemplares, prestados, restantes, autor, editorial);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public int lecturaDeDatosAutor(int cantidadDeAutores) throws MiExcepcion {
        int respuesta = 0;

        do {
            try {
                System.out.println("\nOPCIONES");
                autorService.mostrarAutores();//muestro como opcion los autores existentes
                System.out.println((cantidadDeAutores + 1) + ") INGRESAR AUTOR\n"); //esta es una opcion
                
                System.out.println("SELECCIONE UN AUTOR O INGRESE UNO: ");
                respuesta = entrada.nextInt();

                if (respuesta < 1 || respuesta > (cantidadDeAutores + 1)) { //respuesta es 0/negativo o  es mayor a la ultima opcion
                    System.out.println("LA OPCIÓN SELECCIONADA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            } catch (MiExcepcion e) {
                throw e;
            }
        } while (respuesta < 1 || respuesta > (cantidadDeAutores + 1));

        return respuesta;
    }

    public int lecturaDeDatosEditorial(int cantidadDeEditoriales) throws MiExcepcion {
        int respuesta = 0;

        do {
            try {
                System.out.println("\nOPCIONES");
                editorialService.mostrarEditoriales();
                System.out.println((cantidadDeEditoriales + 1) + ") CREAR EDITORIAL\n");//esta es una opcion
                
                System.out.println("SELECCIONE UNA EDITORIAL O INGRESE UNA: ");
                respuesta = entrada.nextInt();

                if (respuesta < 1 || respuesta > (cantidadDeEditoriales + 1)) {
                    System.out.println("LA OPCIÓN SELECCIONADA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            } catch (MiExcepcion e) {
                throw e;
            }
        } while (respuesta < 1 || respuesta > (cantidadDeEditoriales + 1));

        return respuesta;
    }

    public void mostrarLibros() throws MiExcepcion {
        try {
            List<Libro> libros = libroDAO.buscarLibros();

            if (libros.isEmpty()) {
                System.out.println("NO EXISTEN LIBROS");
            } else {
                System.out.println("\nLIBROS");
                System.out.printf("%-16s%-40s%-25s%-15s%-8s%-12s%-12s%-12s%-12s%n", "ISBN", "TÍTULO",
                        "AUTOR", "EDITORIAL", "AÑO", "EJEMPLARES", "PRESTADOS", "RESTANTES","ALTA");
                libros.forEach(System.out::print);
                System.out.println();
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public void obtenerLibroPorISBN() throws MiExcepcion{
        int respuesta = 0;
        //do {            
            try {
            System.out.println("Ingrese ISBN a buscar:");
            Long isbn = Long.parseLong(Utilidad.obtenerIsbn());
            Libro libro = libroDAO.buscarLibroPorIsbm(isbn);
            if (libro == null) {
                System.out.println("NO EXISTE EL LIBRO");
            } else {
                System.out.println("\nLIBRO");
                System.out.printf("%-16s%-40s%-25s%-15s%-8s%-12s%-12s%-12s%-12s%n", "ISBN", "TÍTULO",
                        "AUTOR", "EDITORIAL", "AÑO", "EJEMPLARES", "PRESTADOS", "RESTANTES","ALTA");
                System.out.println(libro.toString());
                respuesta = 1;
            }

            } catch (MiExcepcion e) {
                throw e;
            }
        //} while (respuesta == 0);
        
    }
    
    public List<Libro> obtenerLibrosPorTitulo() throws MiExcepcion{
        try {
            System.out.println("Ingrese Titulo a buscar:");
            String titulo = Utilidad.obtenerCampo();
            List<Libro> libros = libroDAO.buscarLibroPorTitulo(titulo);
            return libros;
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public void mostrarLibrosPorTitulos() throws MiExcepcion {
        try {
            List<Libro> libros = obtenerLibrosPorTitulo();

            if (libros.isEmpty()) {
                System.out.println("NO EXISTEN LIBROS");
            } else {
                System.out.println("\nLIBROS");
                System.out.printf("%-16s%-40s%-25s%-15s%-8s%-12s%-12s%-12s%-12s%n", "ISBN", "TÍTULO",
                        "AUTOR", "EDITORIAL", "AÑO", "EJEMPLARES", "PRESTADOS", "RESTANTES","ALTA");
                libros.forEach(System.out::print);
                System.out.println();
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public List<Libro> obtenerLibrosPorAutor() throws MiExcepcion{
        try {
            System.out.println("Ingrese Autor a buscar:");
            String autor = Utilidad.obtenerCampo();
            List<Libro> libros = libroDAO.buscarLibroPorNombreDeAutor(autor);
            return libros;
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public List<Libro> obtenerLibrosPorEditorial() throws MiExcepcion{
        try {
            System.out.println("Ingrese Editorial a buscar:");
            String editorial = Utilidad.obtenerCampo();
            List<Libro> libros = libroDAO.buscarLibroPorEditorial(editorial);
            return libros;
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public void mostrarLibrosPorX(List libros) throws MiExcepcion {
        try {

            if (libros.isEmpty()) {
                System.out.println("NO EXISTEN LIBROS");
            } else {
                System.out.println("\nLIBROS");
                System.out.printf("%-16s%-40s%-25s%-15s%-8s%-12s%-12s%-12s%-12s%n", "ISBN", "TÍTULO",
                        "AUTOR", "EDITORIAL", "AÑO", "EJEMPLARES", "PRESTADOS", "RESTANTES","ALTA");
                libros.forEach(System.out::print);
                System.out.println();
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
