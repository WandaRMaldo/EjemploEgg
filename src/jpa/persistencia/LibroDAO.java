package jpa.persistencia;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import jpa.entidad.Editorial;
import jpa.entidad.Libro;
import jpa.excepcion.MiExcepcion;

public class LibroDAO {
    
    private EntityManager em;

    public LibroDAO() {
        em = Persistence
                .createEntityManagerFactory("LibreriaJPAPU")
                .createEntityManager();
    }
    
    public void guardarLibro(Libro libro) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL GUARDAR LIBRO");
        }
    }
    
    public void modificarLibro(Libro libro) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL MODIFICAR LIBRO");
        }
    }
    
    public void eliminarLibro(Libro libro) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL ELIMINAR LIBRO");
        }
    }
    
    public Libro buscarLibroPorIsbm(Long isbm) throws MiExcepcion{
        try {
            Libro libro = em.find(Libro.class, isbm); // find solo funciona con la primary key
            return libro;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR ISBN");
        }
    }
    
    public List<Libro> buscarLibros() throws MiExcepcion{
        try {
            //Tengo que hacer alucion a mi identidad, ya no a mi tabla
            List<Libro> libros = em.createQuery("SELECT li FROM Libro li", Libro.class)
                    .getResultList();
            
            return libros; //quiero traer todos los libros
        } catch (Exception e) {
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL BUSCAR LIBROS");
        }
    }
    
    public List<Libro> buscarLibroPorTitulo(String titulo) throws MiExcepcion {
        try {
            List<Libro> libros = em.createQuery("SELECT li FROM Libro li WHERE li.titulo LIKE :titulo", Libro.class)
                    .setParameter("titulo", titulo)
                    .getResultList();
            return libros;
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR TITULO");
        }
    }
    
    public List<Libro> buscarLibroPorEditorial(String editorial) throws MiExcepcion {
        try {
            List<Libro> libros = em.createQuery("SELECT li FROM Libro li WHERE li.editorial.nombre LIKE :editorial", Libro.class)
                    .setParameter("editorial", editorial)
                    .getResultList();
            return libros;
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR EDITORIAL");
        }
    }
    
    public List<Libro> buscarLibroPorNombreDeAutor(String autor) throws MiExcepcion {
        try {
            List<Libro> libros = em.createQuery("SELECT li FROM Libro li WHERE li.autor.nombre LIKE :autor", Libro.class)
                    .setParameter("autor", autor)
                    .getResultList();
            return libros;
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR AUTOR");
        }
    }
}
