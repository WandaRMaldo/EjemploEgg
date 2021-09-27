package jpa.servicio;

import java.util.List;
import jpa.entidad.Editorial;
import jpa.excepcion.MiExcepcion;
import jpa.persistencia.EditorialDAO;
import jpa.utilidad.Utilidad;

public class EditorialService {
    
    private EditorialDAO editorialDAO;

    public EditorialService() {
        editorialDAO = new EditorialDAO();
    }

    public Editorial crearEditorial(String nombre) throws MiExcepcion {
        try {
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new MiExcepcion("EL NOMBRE DE LA EDITORIAL ES OBLIGATORIO");
            }

            Editorial editorial = new Editorial();
            editorial.setNombre(nombre);
            editorialDAO.guardarEditorial(editorial);

            return editorial;
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public void modificarEditorial() throws MiExcepcion {
        try {
            Editorial editorial = obtenerEditorialPorNombre();
            if (editorial == null) {
                throw new MiExcepcion("LA EDITORIAL NO PUEDE SER NULA");
            }
            
            System.out.println("Ingrese nuevo nombre Editorial:");
            String nombre = Utilidad.obtenerCampo();

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new MiExcepcion("EL NOMBRE DE LA EDITORIAL ES OBLIGATORIO");
            }

            editorial.setNombre(nombre);
            editorialDAO.modificarEditorial(editorial);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public void eliminarEditorial(Editorial editorial) throws MiExcepcion {
        try {
            if (editorial == null) {
                throw new MiExcepcion("LA EDITORIAL NO PUEDE SER NULA");
            }

            editorial.setAlta(false);
            editorialDAO.modificarEditorial(editorial);
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public List<Editorial> obtenerEditoriales() throws MiExcepcion {
        try {
            List<Editorial> editoriales = editorialDAO.buscarEditoriales();
            return editoriales;
        } catch (MiExcepcion e) {
            System.out.println(e.getMessage());
            throw new MiExcepcion("ERROR AL OBTENER EDITORIALES");
        }
    }

    public void mostrarEditoriales() throws MiExcepcion {
        try {
            List<Editorial> editoriales = editorialDAO.buscarEditoriales();

            if (editoriales.isEmpty()) {
                System.out.println("NO EXISTEN EDITORIALES");
            } else {
                for (int i = 0; i < editoriales.size(); i++) {
                    System.out.printf("%s) %s\n", (i + 1), editoriales.get(i).getNombre());
                }
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public void mostrarInfoEditoriales() throws MiExcepcion {
        try {
            List<Editorial> autores = editorialDAO.buscarEditoriales();

            if (autores.isEmpty()) {
                System.out.println("NO EXISTEN AUTORES");
            } else {
                System.out.println("\nEditoriales");
                System.out.printf("%-16s%-40s%-25s%n", "ID", "NOMBRE",
                        "ALTA");
                autores.forEach(System.out::print);
                System.out.println();
            }
        } catch (MiExcepcion e) {
            throw e;
        }
    }

    public Editorial lecturaDeDatos() throws MiExcepcion { //metodo para ingresar una nueva editorial
        Editorial editorial = null;
        String nombre = null;

        try {
            do {
                System.out.println("INGRESE EL NOMBRE DE LA EDITORIAL: ");
                nombre = Utilidad.obtenerCampo();
            } while (editorialDAO.buscarEditorialPorNombre(nombre) != null);

            editorial = crearEditorial(nombre);
        } catch (MiExcepcion e) {
            throw e;
        }

        return editorial;
    }
    
    public void darDeAltaAutor(Editorial editorial) throws MiExcepcion {
        try {
            if (editorial == null) {
                throw new MiExcepcion("LA EDITORIAL NO PUEDE SER NULO");
            }

            editorial.setAlta(true); 
            editorialDAO.modificarEditorial(editorial);
        } catch (MiExcepcion e) {
            throw e;
        }
    }
    
    public Editorial obtenerEditorialPorNombre() throws MiExcepcion {
        try {
            System.out.println("Ingrese nombre Editorial:");
            String nombre = Utilidad.obtenerCampo();
            Editorial editorial = editorialDAO.buscarEditorialPorNombre(nombre);
            return editorial;
        } catch (MiExcepcion e) {
            System.out.println(e.getMessage());
            throw new MiExcepcion("ERROR AL OBTENER EDITORIAL");
        }
    }
}
