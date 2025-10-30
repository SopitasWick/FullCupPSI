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
import Entidades.Opcionproducto;
import Entidades.Productoopcion;
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
public class ProductoopcionJpaController implements Serializable {

    public ProductoopcionJpaController() {
    }
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productoopcion productoopcion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcionproducto idOpcionProducto = productoopcion.getIdOpcionProducto();
            if (idOpcionProducto != null) {
                idOpcionProducto = em.getReference(idOpcionProducto.getClass(), idOpcionProducto.getIdOpcionProducto());
                productoopcion.setIdOpcionProducto(idOpcionProducto);
            }
            em.persist(productoopcion);
            if (idOpcionProducto != null) {
                idOpcionProducto.getProductoopcionList().add(productoopcion);
                idOpcionProducto = em.merge(idOpcionProducto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductoopcion(productoopcion.getIdProductoOpcion()) != null) {
                throw new PreexistingEntityException("Productoopcion " + productoopcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productoopcion productoopcion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productoopcion persistentProductoopcion = em.find(Productoopcion.class, productoopcion.getIdProductoOpcion());
            Opcionproducto idOpcionProductoOld = persistentProductoopcion.getIdOpcionProducto();
            Opcionproducto idOpcionProductoNew = productoopcion.getIdOpcionProducto();
            if (idOpcionProductoNew != null) {
                idOpcionProductoNew = em.getReference(idOpcionProductoNew.getClass(), idOpcionProductoNew.getIdOpcionProducto());
                productoopcion.setIdOpcionProducto(idOpcionProductoNew);
            }
            productoopcion = em.merge(productoopcion);
            if (idOpcionProductoOld != null && !idOpcionProductoOld.equals(idOpcionProductoNew)) {
                idOpcionProductoOld.getProductoopcionList().remove(productoopcion);
                idOpcionProductoOld = em.merge(idOpcionProductoOld);
            }
            if (idOpcionProductoNew != null && !idOpcionProductoNew.equals(idOpcionProductoOld)) {
                idOpcionProductoNew.getProductoopcionList().add(productoopcion);
                idOpcionProductoNew = em.merge(idOpcionProductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoopcion.getIdProductoOpcion();
                if (findProductoopcion(id) == null) {
                    throw new NonexistentEntityException("The productoopcion with id " + id + " no longer exists.");
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
            Productoopcion productoopcion;
            try {
                productoopcion = em.getReference(Productoopcion.class, id);
                productoopcion.getIdProductoOpcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoopcion with id " + id + " no longer exists.", enfe);
            }
            Opcionproducto idOpcionProducto = productoopcion.getIdOpcionProducto();
            if (idOpcionProducto != null) {
                idOpcionProducto.getProductoopcionList().remove(productoopcion);
                idOpcionProducto = em.merge(idOpcionProducto);
            }
            em.remove(productoopcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productoopcion> findProductoopcionEntities() {
        return findProductoopcionEntities(true, -1, -1);
    }

    public List<Productoopcion> findProductoopcionEntities(int maxResults, int firstResult) {
        return findProductoopcionEntities(false, maxResults, firstResult);
    }

    private List<Productoopcion> findProductoopcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productoopcion.class));
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

    public Productoopcion findProductoopcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productoopcion.class, id);
        } finally {
            em.close();
        }
    }

    public List<Productoopcion> findProductoopcionByProducto(Integer idProducto) {
    EntityManager em = getEntityManager();
    try {
        // Consulta JPQL para traer todas las opciones asociadas a un producto específico
        Query query = em.createQuery(
            "SELECT po FROM Productoopcion po WHERE po.idProducto.idProducto = :idProducto"
        );
        query.setParameter("idProducto", idProducto);

        // Devuelve la lista de resultados
        return query.getResultList();
    } catch (Exception e) {
        System.out.println("Error al obtener opciones del producto: " + e.getMessage());
        return null;
    } finally {
        em.close();
    }
}

    
    public int getProductoopcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productoopcion> rt = cq.from(Productoopcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
