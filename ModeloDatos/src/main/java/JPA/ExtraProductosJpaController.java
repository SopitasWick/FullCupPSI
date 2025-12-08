/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Categoria;
import Entidades.Detallecomanda;
import Entidades.ExtrasProductos;
import Entidades.Leche;
import Entidades.Producto;
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
public class ExtraProductosJpaController implements Serializable {
       public ExtraProductosJpaController() {
    }
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExtrasProductos extraProducto) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            Producto idProducto = extraProducto.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                extraProducto.setIdProducto(idProducto);
            }
            
            Categoria idCategoria = extraProducto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                extraProducto.setIdCategoria(idCategoria);
            }
            
            TamanoVaso idTamanoVaso = extraProducto.getIdTamanoVaso();
            if (idTamanoVaso != null) {
                idTamanoVaso = em.getReference(idTamanoVaso.getClass(), idTamanoVaso.getIdTamanoVaso());
                extraProducto.setIdTamanoVaso(idTamanoVaso);
            }
            
            Leche idLeche = extraProducto.getIdLeche();
            if (idLeche != null) {
                idLeche = em.getReference(idLeche.getClass(), idLeche.getIdLeche());
                extraProducto.setIdLeche(idLeche);
            }
            
            Detallecomanda idDetalleComanda = extraProducto.getIdDetalleComanda();
            if (idDetalleComanda != null) {
                idDetalleComanda = em.getReference(idDetalleComanda.getClass(), idDetalleComanda.getIdDetalleComanda());
                extraProducto.setIdDetalleComanda(idDetalleComanda);
            }
            
            em.persist(extraProducto);
            
            if (idProducto != null) {
              //  idProducto.getExtrasProductosList().add(extraProducto);
                idProducto = em.merge(idProducto);
            }
            
            if (idCategoria != null) {
                idCategoria.getExtrasProductosList().add(extraProducto);
                idCategoria = em.merge(idCategoria);
            }
            
            if (idDetalleComanda != null) {
                idDetalleComanda.getExtrasProductosList().add(extraProducto);
                idDetalleComanda = em.merge(idDetalleComanda);
            }
            
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findExtraProducto(extraProducto.getIdExtraProducto()) != null) {
                throw new PreexistingEntityException("ExtrasProductos " + extraProducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ExtrasProductos extraProducto) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            if (findExtraProducto(extraProducto.getIdExtraProducto()) == null) {
                Integer id = extraProducto.getIdExtraProducto();
                throw new NonexistentEntityException("El extra producto con el id: " + id + " no existe");
            }
            
            em.merge(extraProducto);
            
            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
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
            
            ExtrasProductos extraProducto;
            try {
                extraProducto = em.getReference(ExtrasProductos.class, id);
                extraProducto.getIdExtraProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The extra producto with id " + id + " no longer exists.", enfe);
            }
            
            Producto idProducto = extraProducto.getIdProducto();
            if (idProducto != null) {
          //      idProducto.getExtrasProductosList().remove(extraProducto);
                idProducto = em.merge(idProducto);
            }
            
            Categoria idCategoria = extraProducto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getExtrasProductosList().remove(extraProducto);
                idCategoria = em.merge(idCategoria);
            }
            
            // Actualizar la lista de DetalleComanda
            Detallecomanda idDetalleComanda = extraProducto.getIdDetalleComanda();
            if (idDetalleComanda != null) {
                idDetalleComanda.getExtrasProductosList().remove(extraProducto);
                idDetalleComanda = em.merge(idDetalleComanda);
            }
            
            
            em.remove(extraProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ExtrasProductos> findExtraProductoEntities() {
        return findExtraProductoEntities(true, -1, -1);
    }

    public List<ExtrasProductos> findExtraProductoEntities(int maxResults, int firstResult) {
        return findExtraProductoEntities(false, maxResults, firstResult);
    }

    private List<ExtrasProductos> findExtraProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ExtrasProductos.class));
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

    public ExtrasProductos findExtraProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExtrasProductos.class, id);
        } finally {
            em.close();
        }
    }

    public int getExtraProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ExtrasProductos> rt = cq.from(ExtrasProductos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<ExtrasProductos> findExtrasByProducto(Integer idProducto) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM ExtrasProductos e WHERE e.idProducto.idProducto = :idProducto");
            q.setParameter("idProducto", idProducto);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<ExtrasProductos> findExtrasByCategoria(Integer idCategoria) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM ExtrasProductos e WHERE e.idCategoria.idCategoria = :idCategoria");
            q.setParameter("idCategoria", idCategoria);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<ExtrasProductos> findExtrasByTamanoVaso(Integer idTamanoVaso) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM ExtrasProductos e WHERE e.idTamanoVaso.idTamanoVaso = :idTamanoVaso");
            q.setParameter("idTamanoVaso", idTamanoVaso);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<ExtrasProductos> findExtrasByLeche(Integer idLeche) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM ExtrasProductos e WHERE e.idLeche.idLeche = :idLeche");
            q.setParameter("idLeche", idLeche);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<ExtrasProductos> findExtrasByDetalleComanda(Integer idDetalleComanda) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e FROM ExtrasProductos e WHERE e.idDetalleComanda.idDetalleComanda = :idDetalleComanda");
            q.setParameter("idDetalleComanda", idDetalleComanda);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}




