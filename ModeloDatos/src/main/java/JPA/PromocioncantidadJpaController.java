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
import Entidades.Producto;
import Entidades.Promocioncantidad;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class PromocioncantidadJpaController implements Serializable {

    public PromocioncantidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocioncantidad promocioncantidad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = promocioncantidad.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                promocioncantidad.setIdProducto(idProducto);
            }
            em.persist(promocioncantidad);
            if (idProducto != null) {
                idProducto.getPromocioncantidadList().add(promocioncantidad);
                idProducto = em.merge(idProducto);
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
            Producto idProductoOld = persistentPromocioncantidad.getIdProducto();
            Producto idProductoNew = promocioncantidad.getIdProducto();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                promocioncantidad.setIdProducto(idProductoNew);
            }
            promocioncantidad = em.merge(promocioncantidad);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getPromocioncantidadList().remove(promocioncantidad);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getPromocioncantidadList().add(promocioncantidad);
                idProductoNew = em.merge(idProductoNew);
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
            Producto idProducto = promocioncantidad.getIdProducto();
            if (idProducto != null) {
                idProducto.getPromocioncantidadList().remove(promocioncantidad);
                idProducto = em.merge(idProducto);
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
