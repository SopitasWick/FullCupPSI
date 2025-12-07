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
import Entidades.Categoria;
import Entidades.Promocion;
import java.util.ArrayList;
import java.util.List;
import Entidades.Detallecomanda;
import Entidades.Producto;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController() {
        
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Producto create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getPromocionList() == null) {
            producto.setPromocionList(new ArrayList<Promocion>());
        }
        if (producto.getDetallecomandaList() == null) {
            producto.setDetallecomandaList(new ArrayList<Detallecomanda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                producto.setIdCategoria(idCategoria);
            }
            List<Promocion> attachedPromocionList = new ArrayList<Promocion>();
            for (Promocion promocionListPromocionToAttach : producto.getPromocionList()) {
                promocionListPromocionToAttach = em.getReference(promocionListPromocionToAttach.getClass(), promocionListPromocionToAttach.getIdPromocion());
                attachedPromocionList.add(promocionListPromocionToAttach);
            }
            producto.setPromocionList(attachedPromocionList);
            List<Detallecomanda> attachedDetallecomandaList = new ArrayList<Detallecomanda>();
            for (Detallecomanda detallecomandaListDetallecomandaToAttach : producto.getDetallecomandaList()) {
                detallecomandaListDetallecomandaToAttach = em.getReference(detallecomandaListDetallecomandaToAttach.getClass(), detallecomandaListDetallecomandaToAttach.getIdDetalleComanda());
                attachedDetallecomandaList.add(detallecomandaListDetallecomandaToAttach);
            }
            producto.setDetallecomandaList(attachedDetallecomandaList);
            em.persist(producto);
            if (idCategoria != null) {
                idCategoria.getProductoList().add(producto);
                idCategoria = em.merge(idCategoria);
            }
            for (Promocion promocionListPromocion : producto.getPromocionList()) {
                Producto oldIdProductoOfPromocionListPromocion = promocionListPromocion.getIdProducto();
                promocionListPromocion.setIdProducto(producto);
                promocionListPromocion = em.merge(promocionListPromocion);
                if (oldIdProductoOfPromocionListPromocion != null) {
                    oldIdProductoOfPromocionListPromocion.getPromocionList().remove(promocionListPromocion);
                    oldIdProductoOfPromocionListPromocion = em.merge(oldIdProductoOfPromocionListPromocion);
                }
            }
            for (Detallecomanda detallecomandaListDetallecomanda : producto.getDetallecomandaList()) {
                Producto oldIdProductoOfDetallecomandaListDetallecomanda = detallecomandaListDetallecomanda.getIdProducto();
                detallecomandaListDetallecomanda.setIdProducto(producto);
                detallecomandaListDetallecomanda = em.merge(detallecomandaListDetallecomanda);
                if (oldIdProductoOfDetallecomandaListDetallecomanda != null) {
                    oldIdProductoOfDetallecomandaListDetallecomanda.getDetallecomandaList().remove(detallecomandaListDetallecomanda);
                    oldIdProductoOfDetallecomandaListDetallecomanda = em.merge(oldIdProductoOfDetallecomandaListDetallecomanda);
                }
            }
            
            em.flush();
            
            em.getTransaction().commit();
            
            
            
            return producto;
            
        } catch (Exception ex) {
            if (findProducto(producto.getIdProducto()) != null) {
                throw new PreexistingEntityException("Producto " + producto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws NonexistentEntityException, Exception {
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();

        // 1. Verificar si el objeto existe antes de intentar actualizarlo (opcional pero recomendado)
        // Usamos findProducto() que asumo está definido en tu JpaController.
        if (findProducto(producto.getIdProducto()) == null) {
            Integer id = producto.getIdProducto();
            throw new NonexistentEntityException("El producto con el id;  " + id + " no existe");
        }
        
        // 2. Usar em.merge() para actualizar el objeto
        // Esta línea busca la entidad por el ID del objeto 'producto' y aplica los nuevos valores.
        em.merge(producto);
        
        em.getTransaction().commit();

    } catch (Exception ex) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        // La excepción NonexistentEntityException ya la manejamos arriba
        // para lanzar el error específico si el objeto no se encuentra.
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = producto.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getProductoList().remove(producto);
                idCategoria = em.merge(idCategoria);
            }
            List<Promocion> promocionList = producto.getPromocionList();
            for (Promocion promocionListPromocion : promocionList) {
                promocionListPromocion.setIdProducto(null);
                promocionListPromocion = em.merge(promocionListPromocion);
            }
            List<Detallecomanda> detallecomandaList = producto.getDetallecomandaList();
            for (Detallecomanda detallecomandaListDetallecomanda : detallecomandaList) {
                detallecomandaListDetallecomanda.setIdProducto(null);
                detallecomandaListDetallecomanda = em.merge(detallecomandaListDetallecomanda);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }
    public List<Producto> findProductosByIdCategoria(int idCategoria) {
    EntityManager em = getEntityManager();
    try {
        return em.createNamedQuery("Producto.findByIdCategoria", Producto.class)
                 .setParameter("idCategoria", idCategoria)
                 .getResultList();
    } finally {
        em.close();
    }
}

    
    public List<Producto> findProductosByNombreProducto(String nombreProducto) {
    EntityManager em = getEntityManager();
    try {
        return em.createNamedQuery("Producto.findByNombreProducto", Producto.class)
                 .setParameter("nombre_producto", nombreProducto)
                 .getResultList();
    } finally {
        em.close();
    }
}    
    
    
    
    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
