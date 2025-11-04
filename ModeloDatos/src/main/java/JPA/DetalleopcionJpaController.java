/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Detalleopcion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Valoropcion;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class DetalleopcionJpaController implements Serializable {

    public DetalleopcionJpaController() {
    }
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detalleopcion detalleopcion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoropcion idValorOpcion = detalleopcion.getIdValorOpcion();
            if (idValorOpcion != null) {
                idValorOpcion = em.getReference(idValorOpcion.getClass(), idValorOpcion.getIdValorOpcion());
                detalleopcion.setIdValorOpcion(idValorOpcion);
            }
            em.persist(detalleopcion);
            if (idValorOpcion != null) {
                idValorOpcion.getDetalleopcionList().add(detalleopcion);
                idValorOpcion = em.merge(idValorOpcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleopcion(detalleopcion.getIdDetalleOpcion()) != null) {
                throw new PreexistingEntityException("Detalleopcion " + detalleopcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detalleopcion detalleopcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleopcion persistentDetalleopcion = em.find(Detalleopcion.class, detalleopcion.getIdDetalleOpcion());
            Valoropcion idValorOpcionOld = persistentDetalleopcion.getIdValorOpcion();
            Valoropcion idValorOpcionNew = detalleopcion.getIdValorOpcion();
            if (idValorOpcionNew != null) {
                idValorOpcionNew = em.getReference(idValorOpcionNew.getClass(), idValorOpcionNew.getIdValorOpcion());
                detalleopcion.setIdValorOpcion(idValorOpcionNew);
            }
            detalleopcion = em.merge(detalleopcion);
            if (idValorOpcionOld != null && !idValorOpcionOld.equals(idValorOpcionNew)) {
                idValorOpcionOld.getDetalleopcionList().remove(detalleopcion);
                idValorOpcionOld = em.merge(idValorOpcionOld);
            }
            if (idValorOpcionNew != null && !idValorOpcionNew.equals(idValorOpcionOld)) {
                idValorOpcionNew.getDetalleopcionList().add(detalleopcion);
                idValorOpcionNew = em.merge(idValorOpcionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleopcion.getIdDetalleOpcion();
                if (findDetalleopcion(id) == null) {
                    throw new NonexistentEntityException("The detalleopcion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detalleopcion detalleopcion;
            try {
                detalleopcion = em.getReference(Detalleopcion.class, id);
                detalleopcion.getIdDetalleOpcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleopcion with id " + id + " no longer exists.", enfe);
            }
            Valoropcion idValorOpcion = detalleopcion.getIdValorOpcion();
            if (idValorOpcion != null) {
                idValorOpcion.getDetalleopcionList().remove(detalleopcion);
                idValorOpcion = em.merge(idValorOpcion);
            }
            em.remove(detalleopcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detalleopcion> findDetalleopcionEntities() {
        return findDetalleopcionEntities(true, -1, -1);
    }

    public List<Detalleopcion> findDetalleopcionEntities(int maxResults, int firstResult) {
        return findDetalleopcionEntities(false, maxResults, firstResult);
    }

    private List<Detalleopcion> findDetalleopcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detalleopcion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Detalleopcion findDetalleopcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detalleopcion.class, id);
        } finally {
            em.close();
        }
    }

  

    
    public int getDetalleopcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detalleopcion> rt = cq.from(Detalleopcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
