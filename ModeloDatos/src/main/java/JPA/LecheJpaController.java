/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Leche;
import Entidades.TamanoVaso;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.io.Serializable;
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
public class LecheJpaController implements Serializable {
   public LecheJpaController() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Leche leche) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(leche);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLeche(leche.getIdLeche()) != null) {
                throw new PreexistingEntityException("Leche " + leche + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Leche leche) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            if (findLeche(leche.getIdLeche()) == null) {
                throw new NonexistentEntityException("La leche con id " + leche.getIdLeche() + " no existe.");
            }
            
            leche = em.merge(leche);
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
            
            Leche leche;
            try {
                leche = em.getReference(Leche.class, id);
                leche.getIdLeche();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La leche con id " + id + " no existe.", enfe);
            }
            
            em.remove(leche);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Leche> findLecheEntities() {
        return findLecheEntities(true, -1, -1);
    }

    public List<Leche> findLecheEntities(int maxResults, int firstResult) {
        return findLecheEntities(false, maxResults, firstResult);
    }

    private List<Leche> findLecheEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Leche.class));
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

    public Leche findLeche(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Leche.class, id);
        } finally {
            em.close();
        }
    }

    public int getLecheCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Leche> rt = cq.from(Leche.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    
}
}
