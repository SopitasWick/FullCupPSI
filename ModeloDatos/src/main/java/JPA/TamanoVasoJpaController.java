/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.TamanoVaso;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Orlando Leyva
 */
public class TamanoVasoJpaController {
    public TamanoVasoJpaController() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TamanoVaso tamanoVaso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tamanoVaso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTamanoVaso(tamanoVaso.getIdTamanoVaso()) != null) {
                throw new PreexistingEntityException("TamanoVaso " + tamanoVaso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TamanoVaso tamanoVaso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            if (findTamanoVaso(tamanoVaso.getIdTamanoVaso()) == null) {
                throw new NonexistentEntityException("El tamaño de vaso con id " + tamanoVaso.getIdTamanoVaso() + " no existe.");
            }
            
            tamanoVaso = em.merge(tamanoVaso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
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
            
            TamanoVaso tamanoVaso;
            try {
                tamanoVaso = em.getReference(TamanoVaso.class, id);
                tamanoVaso.getIdTamanoVaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El tamaño de vaso con id " + id + " no existe.", enfe);
            }
            
            em.remove(tamanoVaso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TamanoVaso> findTamanoVasoEntities() {
        return findTamanoVasoEntities(true, -1, -1);
    }

    public List<TamanoVaso> findTamanoVasoEntities(int maxResults, int firstResult) {
        return findTamanoVasoEntities(false, maxResults, firstResult);
    }

    private List<TamanoVaso> findTamanoVasoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TamanoVaso.class));
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

    public TamanoVaso findTamanoVaso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TamanoVaso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTamanoVasoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TamanoVaso> rt = cq.from(TamanoVaso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
