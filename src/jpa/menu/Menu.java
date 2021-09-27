package jpa.menu;

import java.util.InputMismatchException;
import java.util.Scanner;
import jpa.excepcion.MiExcepcion;
import jpa.servicio.AutorService;
import jpa.servicio.EditorialService;
import jpa.servicio.LibroService;


public class Menu {

    private Scanner entrada;
    private LibroService libroService;
    private AutorService autorService;
    private EditorialService editorialService;

    public Menu() {
        entrada = new Scanner(System.in, "ISO-8859-1").useDelimiter("\n");
        libroService= new LibroService();
        autorService = new AutorService();
        editorialService = new EditorialService();
    }

    public void interfazPrincipal() {
        int respuesta = 0;
        int salida = 4;

        do {
            try {
                System.out.println("ELIJA UNA OPCIÓN");
                System.out.println("1. MENÚ LIBRO");
                System.out.println("2. MENÚ AUTOR");
                System.out.println("3. MENÚ EDITORIAL");
                System.out.println("4. SALIR");

                respuesta = entrada.nextInt();

                switch (respuesta) {
                    case 1:
                        interfazLibro();
                        break;
                    case 2:
                        interfazAutor();
                        break;
                    case 3:
                        interfazEditorial();
                        break;
                    case 4:
                        System.out.println("*** SESIÓN FINALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN ELEGIDA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            }
        } while (respuesta != salida);
    }

    public void interfazLibro() {
        int respuesta = 0;
        int salida = 7;

        do {
            try {
                System.out.println("ELIJA UNA OPCIÓN");
                System.out.println("1. INGRESAR LIBRO");
                System.out.println("2. MOSTRAR LIBROS");
                System.out.println("3. BUSCAR LIBRO POR ISBN");
                System.out.println("4. BUSCAR LIBRO POR TÍTULO");
                System.out.println("5. BUSCAR LIBRO/S POR NOMBRE DE AUTOR");
                System.out.println("6. BUSCAR LIBRO/S POR NOMBRE DE EDITORIAL");
                System.out.println("7. SALIR");

                respuesta = entrada.nextInt();

                switch (respuesta) {
                    case 1:
                        libroService.lecturaDeDatos();
                        break;
                    case 2:
                        libroService.mostrarLibros();
                        break;
                    case 3:
                        libroService.obtenerLibroPorISBN();
                        break;
                    case 4:
                        libroService.mostrarLibrosPorTitulos();
                        break;
                    case 5:
                        libroService.mostrarLibrosPorX(libroService.obtenerLibrosPorAutor());
                        break;
                    case 6:
                        libroService.mostrarLibrosPorX(libroService.obtenerLibrosPorEditorial());
                        break;
                    case 7:
                        System.out.println("*** SESIÓN FINALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN ELEGIDA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            } catch (MiExcepcion e) {
                System.out.println(e.getMessage());
            }
        } while (respuesta != salida);
    }

    public void interfazAutor() {
        int respuesta = 0;
        int salida = 6;

        do {
            try {
                System.out.println("ELIJA UNA OPCIÓN");
                System.out.println("1. CREAR AUTOR");
                System.out.println("2. MOSTRAR AUTORES");
                System.out.println("3. MODIFICAR AUTOR");
                System.out.println("4. DAR DE ALTA A AUTOR");
                System.out.println("5. DAR DE BAJA A AUTOR");
                System.out.println("6. SALIR");

                respuesta = entrada.nextInt();

                switch (respuesta) {
                    case 1:
                        autorService.lecturaDeDatos();
                        break;
                    case 2:
                        autorService.mostrarInfoAutores();
                        break;
                    case 3:
                        
                        autorService.modificarAutor();
                        break;    
                    case 4:
                        autorService.darDeAltaAutor(autorService.obtenerAutorPorNombre());
                        break;
                    case 5:
                        autorService.eliminarAutor(autorService.obtenerAutorPorNombre());
                        break;
                    case 6:
                        System.out.println("*** SESIÓN FINALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN ELEGIDA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            } catch (MiExcepcion e) {
                System.out.println(e.getMessage());
            }
        } while (respuesta != salida);
    }

    public void interfazEditorial() {
        int respuesta = 0;
        int salida = 6;

        do {
            try {
                System.out.println("ELIJA UNA OPCIÓN");
                System.out.println("1. CREAR EDITORIAL");
                System.out.println("2. MOSTRAR EDITORIALES");
                System.out.println("3. MODIFICAR EDITORIAL");
                System.out.println("4. DAR DE ALTA A EDITORIAL");
                System.out.println("5. DAR DE BAJA A EDITORIAL");
                System.out.println("6. SALIR");

                respuesta = entrada.nextInt();

                switch (respuesta) {
                    case 1:
                        editorialService.lecturaDeDatos();
                        break;
                    case 2:
                        editorialService.mostrarInfoEditoriales();
                        break;
                    case 3:
                        editorialService.modificarEditorial();
                        break;    
                    case 4:
                        editorialService.darDeAltaAutor(editorialService.obtenerEditorialPorNombre());
                        break;
                    case 5:
                        editorialService.eliminarEditorial(editorialService.obtenerEditorialPorNombre());
                        break;
                    case 6:
                        System.out.println("*** SESIÓN FINALIZADA ***");
                        break;
                    default:
                        System.out.println("LA OPCIÓN ELEGIDA ES INVÁLIDA");
                }
            } catch (InputMismatchException e) {
                System.out.println("NO SE ADMITEN CARACTERES");
                entrada.next();
            } catch (MiExcepcion e) {
                System.out.println(e.getMessage());
            }
        } while (respuesta != salida);
    }
}
