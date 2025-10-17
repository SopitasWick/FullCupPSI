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
import Entidades.Promocionporcentaje;
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
public class PromocionporcentajeJpaController implements Serializable {

    public PromocionporcentajeJpaController() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocionporcentaje promocionporcentaje) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion idPromocion = promocionporcentaje.getIdPromocion();
            if (idPromocion != null) {
                idPromocion = em.getReference(idPromocion.getClass(), idPromocion.getIdPromocion());
                promocionporcentaje.setIdPromocion(idPromocion);
            }
            em.persist(promocionporcentaje);
            if (idPromocion != null) {
                idPromocion.getPromocionporcentajeList().add(promocionporcentaje);
                idPromocion = em.merge(idPromocion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromocionporcentaje(promocionporcentaje.getIdPromocionPorcentaje()) != null) {
                throw new PreexistingEntityException("Promocionporcentaje " + promocionporcentaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocionporcentaje promocionporcentaje) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocionporcentaje persistentPromocionporcentaje = em.find(Promocionporcentaje.class, promocionporcentaje.getIdPromocionPorcentaje());
            Promocion idPromocionOld = persistentPromocionporcentaje.getIdPromocion();
            Promocion idPromocionNew = promocionporcentaje.getIdPromocion();
            if (idPromocionNew != null) {
                idPromocionNew = em.getReference(idPromocionNew.getClass(), idPromocionNew.getIdPromocion());
                promocionporcentaje.setIdPromocion(idPromocionNew);
            }
            promocionporcentaje = em.merge(promocionporcentaje);
            if (idPromocionOld != null && !idPromocionOld.equals(idPromocionNew)) {
                idPromocionOld.getPromocionporcentajeList().remove(promocionporcentaje);
                idPromocionOld = em.merge(idPromocionOld);
            }
            if (idPromocionNew != null && !idPromocionNew.equals(idPromocionOld)) {
                idPromocionNew.getPromocionporcentajeList().add(promocionporcentaje);
                idPromocionNew = em.merge(idPromocionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocionporcentaje.getIdPromocionPorcentaje();
                if (findPromocionporcentaje(id) == null) {
                    throw new NonexistentEntityException("The promocionporcentaje with id " + id + " no longer exists.");
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
            Promocionporcentaje promocionporcentaje;
            try {
                promocionporcentaje = em.getReference(Promocionporcentaje.class, id);
                promocionporcentaje.getIdPromocionPorcentaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocionporcentaje with id " + id + " no longer exists.", enfe);
            }
            Promocion idPromocion = promocionporcentaje.getIdPromocion();
            if (idPromocion != null) {
                idPromocion.getPromocionporcentajeList().remove(promocionporcentaje);
                idPromocion = em.merge(idPromocion);
            }
            em.remove(promocionporcentaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocionporcentaje> findPromocionporcentajeEntities() {
        return findPromocionporcentajeEntities(true, -1, -1);
    }

    public List<Promocionporcentaje> findPromocionporcentajeEntities(int maxResults, int firstResult) {
        return findPromocionporcentajeEntities(false, maxResults, firstResult);
    }

    private List<Promocionporcentaje> findPromocionporcentajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocionporcentaje.class));
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

    public Promocionporcentaje findPromocionporcentaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocionporcentaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionporcentajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocionporcentaje> rt = cq.from(Promocionporcentaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
