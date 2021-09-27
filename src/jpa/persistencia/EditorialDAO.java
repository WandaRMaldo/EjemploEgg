
package jpa.persistencia;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import jpa.entidad.Editorial;
import jpa.excepcion.MiExcepcion;

public class EditorialDAO {
    
    private EntityManager em;

    public EditorialDAO() {
        em = Persistence
                .createEntityManagerFactory("LibreriaJPAPU")
                .createEntityManager();
    }
    
    public void guardarEditorial(Editorial editorial) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new MiExcepcion("ERROR AL GUARDAR EDITORIAL");
        }
    }

    public void modificarEditorial(Editorial editorial) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new MiExcepcion("ERROR AL MODIFICAR EDITORIAL");
        }
    }

    public void eliminarEditorial(Editorial editorial) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new MiExcepcion("ERROR AL ELIMINAR EDITORIAL");
        }
    }
    
    public Editorial buscarEditorialPorId(Integer id) throws MiExcepcion {
        try {
            return em.find(Editorial.class, id);
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR EDITORIAL POR ID");
        }
    }

    public Editorial buscarEditorialPorNombre(String nombre) throws MiExcepcion {
        try {
            return em.createQuery("SELECT e FROM Editorial e WHERE e.nombre = :nom", Editorial.class)
                    .setParameter("nom", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR EDITORIAL POR NOMBRE");
        }
    }

    public List<Editorial> buscarEditoriales() throws MiExcepcion {
        try {
            return em.createQuery("SELECT e FROM Editorial e", Editorial.class)
                    .getResultList();
        } catch (Exception e) {
            throw new MiExcepcion("ERROR AL BUSCAR EDITORIALES");
        }
    }
}
