package jpa.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import jpa.entidad.Autor;
import jpa.excepcion.MiExcepcion;

public class AutorDAO {
    
    private EntityManager em = Persistence.
            createEntityManagerFactory("LibreriaJPAPU").
            createEntityManager();
    
    public void guardarAutor(Autor autor) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
            
        } catch(Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL GUARDAR AUTOR");
        }
    }
    
    public void modificarAutor(Autor autor) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERROR AL MODIFICAR AUTOR");
        }
    }
    
    public void eliminarAutor(Autor autor) throws MiExcepcion{
        try {
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw new MiExcepcion("ERORR AL ELIMINAR AUTOR");
        }
    }
    
    public Autor buscarAutorPorId(Integer id) throws MiExcepcion {
        try {
            return em.find(Autor.class, id);
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR ID");
        }
    }
    
    public Autor buscarAutorPorNombre(String nombre) throws MiExcepcion {
        try {
            return em.createQuery("SELECT a FROM Autor a WHERE a.nombre = :nom", Autor.class)
                    .setParameter("nom", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTOR POR ID");
        }
    }
    
    public List<Autor> buscarAutores() throws MiExcepcion {
        try {
            return em.createQuery("SELECT a FROM Autor a", Autor.class)
                    .getResultList();
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR AUTORES");
        }
    }
}
