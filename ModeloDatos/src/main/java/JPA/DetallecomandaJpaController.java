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
import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Producto;
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
public class DetallecomandaJpaController implements Serializable {

    public DetallecomandaJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Detallecomanda detallecomanda) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda idComanda = detallecomanda.getIdComanda();
            if (idComanda != null) {
                idComanda = em.getReference(idComanda.getClass(), idComanda.getIdComanda());
                detallecomanda.setIdComanda(idComanda);
            }
            Producto idProducto = detallecomanda.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                detallecomanda.setIdProducto(idProducto);
            }
            em.persist(detallecomanda);
            if (idComanda != null) {
                idComanda.getDetallecomandaList().add(detallecomanda);
                idComanda = em.merge(idComanda);
            }
            if (idProducto != null) {
                idProducto.getDetallecomandaList().add(detallecomanda);
                idProducto = em.merge(idProducto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetallecomanda(detallecomanda.getIdDetalleComanda()) != null) {
                throw new PreexistingEntityException("Detallecomanda " + detallecomanda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Detallecomanda detallecomanda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Detallecomanda persistentDetallecomanda = em.find(Detallecomanda.class, detallecomanda.getIdDetalleComanda());
            Comanda idComandaOld = persistentDetallecomanda.getIdComanda();
            Comanda idComandaNew = detallecomanda.getIdComanda();
            Producto idProductoOld = persistentDetallecomanda.getIdProducto();
            Producto idProductoNew = detallecomanda.getIdProducto();
            if (idComandaNew != null) {
                idComandaNew = em.getReference(idComandaNew.getClass(), idComandaNew.getIdComanda());
                detallecomanda.setIdComanda(idComandaNew);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                detallecomanda.setIdProducto(idProductoNew);
            }
            detallecomanda = em.merge(detallecomanda);
            if (idComandaOld != null && !idComandaOld.equals(idComandaNew)) {
                idComandaOld.getDetallecomandaList().remove(detallecomanda);
                idComandaOld = em.merge(idComandaOld);
            }
            if (idComandaNew != null && !idComandaNew.equals(idComandaOld)) {
                idComandaNew.getDetallecomandaList().add(detallecomanda);
                idComandaNew = em.merge(idComandaNew);
            }
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getDetallecomandaList().remove(detallecomanda);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getDetallecomandaList().add(detallecomanda);
                idProductoNew = em.merge(idProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallecomanda.getIdDetalleComanda();
                if (findDetallecomanda(id) == null) {
                    throw new NonexistentEntityException("The detallecomanda with id " + id + " no longer exists.");
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
            Detallecomanda detallecomanda;
            try {
                detallecomanda = em.getReference(Detallecomanda.class, id);
                detallecomanda.getIdDetalleComanda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallecomanda with id " + id + " no longer exists.", enfe);
            }
            Comanda idComanda = detallecomanda.getIdComanda();
            if (idComanda != null) {
                idComanda.getDetallecomandaList().remove(detallecomanda);
                idComanda = em.merge(idComanda);
            }
            Producto idProducto = detallecomanda.getIdProducto();
            if (idProducto != null) {
                idProducto.getDetallecomandaList().remove(detallecomanda);
                idProducto = em.merge(idProducto);
            }
            em.remove(detallecomanda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Detallecomanda> findDetallecomandaEntities() {
        return findDetallecomandaEntities(true, -1, -1);
    }
    
    
    public List<Detallecomanda> findByDetalleComandaByComanda(Comanda comanda) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Detallecomanda.findByDetalleComandaByComanda", Detallecomanda.class)
                     .setParameter("idComanda", comanda)
                     .getResultList();
        } finally {
            em.close();
        }

    }

    public List<Detallecomanda> findDetallecomandaEntities(int maxResults, int firstResult) {
        return findDetallecomandaEntities(false, maxResults, firstResult);
    }

    private List<Detallecomanda> findDetallecomandaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Detallecomanda.class));
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

    public Detallecomanda findDetallecomanda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Detallecomanda.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallecomandaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Detallecomanda> rt = cq.from(Detallecomanda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
