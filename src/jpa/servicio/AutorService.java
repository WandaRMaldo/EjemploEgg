package jpa.servicio;

import java.util.List;
import jpa.entidad.Autor;
import jpa.excepcion.MiExcepcion;
import jpa.persistencia.AutorDAO;
import jpa.utilidad.Utilidad;

public class AutorService {
    
    private AutorDAO autorDAO;

    public AutorService() {
        autorDAO = new AutorDAO();
    }
    
    public Autor crearAutor(String nombre) throws MiExcepcion {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new MiExcepcion("EL NOMBRE DEL AUTOR ES OBLIGATORIO");
            }

            Autor autor = new Autor();
            autor.setNombre(nombre);
            autorDAO.guardarAutor(autor);

            return autor;
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public void modificarAutor() throws MiExcepcion {
        try {
            Autor autor = obtenerAutorPorNombre();
            if (autor == null) {
                throw new MiExcepcion("EL AUTOR NO PUEDE SER NULO\n");
            }
            
            System.out.println("Ingrese nuevo nombre Autor:");
            String nombre = Utilidad.obtenerCampo();

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new MiExcepcion("EL NOMBRE DEL AUTOR ES OBLIGATORIO\n");
            }

            autor.setNombre(nombre);
            autorDAO.modificarAutor(autor);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public void eliminarAutor(Autor autor) throws MiExcepcion {
        try {
            if (autor == null) {
                throw new MiExcepcion("EL AUTOR NO PUEDE SER NULO");
            }

            autor.setAlta(false); //en si no elimino al autor sino que lo doy de baja
            autorDAO.modificarAutor(autor);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public List<Autor> obtenerAutores() throws MiExcepcion {
        try {
            List<Autor> autores = autorDAO.buscarAutores();
            return autores;
        } catch (MiExcepcion e) {
            System.out.println(e.getMessage());
            throw new MiExcepcion("ERROR AL OBTENER AUTORES");
        }
    }

    public void mostrarAutores() throws MiExcepcion {
        try {
            List<Autor> autores = autorDAO.buscarAutores();

            if (autores.isEmpty()) {
                System.out.println("NO EXISTEN AUTORES");
            } else {
                for (int i = 0; i < autores.size(); i++) {
                    System.out.printf("%s) %s\n", (i + 1), autores.get(i).getNombre());
                }
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public void mostrarInfoAutores() throws MiExcepcion {
        try {
            List<Autor> autores = autorDAO.buscarAutores();

            if (autores.isEmpty()) {
                System.out.println("NO EXISTEN AUTORES");
            } else {
                System.out.println("\nAutores");
                System.out.printf("%-16s%-40s%-25s%n", "ID", "NOMBRE",
                        "ALTA");
                autores.forEach(System.out::print);
                System.out.println();
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public Autor lecturaDeDatos() throws MiExcepcion { //metodo para crear un nuevo autor
        Autor autor = null;
        String nombre = null;

        try {
            do {
                System.out.println("INGRESE EL NOMBRE DEL AUTOR: ");
                nombre = Utilidad.obtenerCampo(); //ingreso un nuevo autor
            } while (autorDAO.buscarAutorPorNombre(nombre) != null); //voy a seguir en el bucle mientras el autor que ingrese ya exista en la lista

            autor = crearAutor(nombre);
        } catch (MiExcepcion e) {
            throw e;
        }

        return autor;
    }
    
    public Autor obtenerAutorPorNombre() throws MiExcepcion {
        Autor autor = null;
        try {
            //do {                
                System.out.println("Ingrese nombre Autor:");
                String nombre = Utilidad.obtenerCampo();
                autor = autorDAO.buscarAutorPorNombre(nombre);
            //} while (autor == null);
            
            return autor;
        } catch (MiExcepcion e) {
            System.out.println(e.getMessage());
            throw new MiExcepcion("ERROR AL OBTENER AUTOR");
        }
    }
    
    public void darDeAltaAutor(Autor autor) throws MiExcepcion {
        try {
            if (autor == null) {
                throw new MiExcepcion("EL AUTOR NO PUEDE SER NULO");
            }

            autor.setAlta(true); 
            autorDAO.modificarAutor(autor);
        } catch (MiExcepcion e) {
            throw e;
        }
    }
}
