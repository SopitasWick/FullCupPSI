/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Promocion;
import Entidades.Promocioncantidad;
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
public class PromocioncantidadJpaController implements Serializable {

    public PromocioncantidadJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocioncantidad promocioncantidad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion idPromocion = promocioncantidad.getIdPromocion();
            if (idPromocion != null) {
                idPromocion = em.getReference(idPromocion.getClass(), idPromocion.getIdPromocion());
                promocioncantidad.setIdPromocion(idPromocion);
            }
            em.persist(promocioncantidad);
            if (idPromocion != null) {
                idPromocion.getPromocioncantidadList().add(promocioncantidad);
                idPromocion = em.merge(idPromocion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromocioncantidad(promocioncantidad.getIdPromocionCantidad()) != null) {
                throw new PreexistingEntityException("Promocioncantidad " + promocioncantidad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocioncantidad promocioncantidad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocioncantidad persistentPromocioncantidad = em.find(Promocioncantidad.class, promocioncantidad.getIdPromocionCantidad());
            Promocion idPromocionOld = persistentPromocioncantidad.getIdPromocion();
            Promocion idPromocionNew = promocioncantidad.getIdPromocion();
            if (idPromocionNew != null) {
                idPromocionNew = em.getReference(idPromocionNew.getClass(), idPromocionNew.getIdPromocion());
                promocioncantidad.setIdPromocion(idPromocionNew);
            }
            promocioncantidad = em.merge(promocioncantidad);
            if (idPromocionOld != null && !idPromocionOld.equals(idPromocionNew)) {
                idPromocionOld.getPromocioncantidadList().remove(promocioncantidad);
                idPromocionOld = em.merge(idPromocionOld);
            }
            if (idPromocionNew != null && !idPromocionNew.equals(idPromocionOld)) {
                idPromocionNew.getPromocioncantidadList().add(promocioncantidad);
                idPromocionNew = em.merge(idPromocionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocioncantidad.getIdPromocionCantidad();
                if (findPromocioncantidad(id) == null) {
                    throw new NonexistentEntityException("The promocioncantidad with id " + id + " no longer exists.");
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
            Promocioncantidad promocioncantidad;
            try {
                promocioncantidad = em.getReference(Promocioncantidad.class, id);
                promocioncantidad.getIdPromocionCantidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocioncantidad with id " + id + " no longer exists.", enfe);
            }
            Promocion idPromocion = promocioncantidad.getIdPromocion();
            if (idPromocion != null) {
                idPromocion.getPromocioncantidadList().remove(promocioncantidad);
                idPromocion = em.merge(idPromocion);
            }
            em.remove(promocioncantidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocioncantidad> findPromocioncantidadEntities() {
        return findPromocioncantidadEntities(true, -1, -1);
    }

    public List<Promocioncantidad> findPromocioncantidadEntities(int maxResults, int firstResult) {
        return findPromocioncantidadEntities(false, maxResults, firstResult);
    }

    private List<Promocioncantidad> findPromocioncantidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocioncantidad.class));
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

    public Promocioncantidad findPromocioncantidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocioncantidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocioncantidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocioncantidad> rt = cq.from(Promocioncantidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
