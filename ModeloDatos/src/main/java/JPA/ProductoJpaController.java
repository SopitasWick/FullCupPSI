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
import Entidades.Promocioncantidad;
import java.util.ArrayList;
import java.util.List;
import Entidades.Detallecomanda;
import Entidades.Producto;
import Entidades.Promocionporcentaje;
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

    public void create(Producto producto) throws PreexistingEntityException, Exception {
        if (producto.getPromocioncantidadList() == null) {
            producto.setPromocioncantidadList(new ArrayList<Promocioncantidad>());
        }
        if (producto.getDetallecomandaList() == null) {
            producto.setDetallecomandaList(new ArrayList<Detallecomanda>());
        }
        if (producto.getPromocionporcentajeList() == null) {
            producto.setPromocionporcentajeList(new ArrayList<Promocionporcentaje>());
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
            List<Promocioncantidad> attachedPromocioncantidadList = new ArrayList<Promocioncantidad>();
            for (Promocioncantidad promocioncantidadListPromocioncantidadToAttach : producto.getPromocioncantidadList()) {
                promocioncantidadListPromocioncantidadToAttach = em.getReference(promocioncantidadListPromocioncantidadToAttach.getClass(), promocioncantidadListPromocioncantidadToAttach.getIdPromocionCantidad());
                attachedPromocioncantidadList.add(promocioncantidadListPromocioncantidadToAttach);
            }
            producto.setPromocioncantidadList(attachedPromocioncantidadList);
            List<Detallecomanda> attachedDetallecomandaList = new ArrayList<Detallecomanda>();
            for (Detallecomanda detallecomandaListDetallecomandaToAttach : producto.getDetallecomandaList()) {
                detallecomandaListDetallecomandaToAttach = em.getReference(detallecomandaListDetallecomandaToAttach.getClass(), detallecomandaListDetallecomandaToAttach.getIdDetalleComanda());
                attachedDetallecomandaList.add(detallecomandaListDetallecomandaToAttach);
            }
            producto.setDetallecomandaList(attachedDetallecomandaList);
            List<Promocionporcentaje> attachedPromocionporcentajeList = new ArrayList<Promocionporcentaje>();
            for (Promocionporcentaje promocionporcentajeListPromocionporcentajeToAttach : producto.getPromocionporcentajeList()) {
                promocionporcentajeListPromocionporcentajeToAttach = em.getReference(promocionporcentajeListPromocionporcentajeToAttach.getClass(), promocionporcentajeListPromocionporcentajeToAttach.getIdPromocionPorcentaje());
                attachedPromocionporcentajeList.add(promocionporcentajeListPromocionporcentajeToAttach);
            }
            producto.setPromocionporcentajeList(attachedPromocionporcentajeList);
            em.persist(producto);
            if (idCategoria != null) {
                idCategoria.getProductoList().add(producto);
                idCategoria = em.merge(idCategoria);
            }
            for (Promocioncantidad promocioncantidadListPromocioncantidad : producto.getPromocioncantidadList()) {
                Producto oldIdProductoOfPromocioncantidadListPromocioncantidad = promocioncantidadListPromocioncantidad.getIdProducto();
                promocioncantidadListPromocioncantidad.setIdProducto(producto);
                promocioncantidadListPromocioncantidad = em.merge(promocioncantidadListPromocioncantidad);
                if (oldIdProductoOfPromocioncantidadListPromocioncantidad != null) {
                    oldIdProductoOfPromocioncantidadListPromocioncantidad.getPromocioncantidadList().remove(promocioncantidadListPromocioncantidad);
                    oldIdProductoOfPromocioncantidadListPromocioncantidad = em.merge(oldIdProductoOfPromocioncantidadListPromocioncantidad);
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
            for (Promocionporcentaje promocionporcentajeListPromocionporcentaje : producto.getPromocionporcentajeList()) {
                Producto oldIdProductoOfPromocionporcentajeListPromocionporcentaje = promocionporcentajeListPromocionporcentaje.getIdProducto();
                promocionporcentajeListPromocionporcentaje.setIdProducto(producto);
                promocionporcentajeListPromocionporcentaje = em.merge(promocionporcentajeListPromocionporcentaje);
                if (oldIdProductoOfPromocionporcentajeListPromocionporcentaje != null) {
                    oldIdProductoOfPromocionporcentajeListPromocionporcentaje.getPromocionporcentajeList().remove(promocionporcentajeListPromocionporcentaje);
                    oldIdProductoOfPromocionporcentajeListPromocionporcentaje = em.merge(oldIdProductoOfPromocionporcentajeListPromocionporcentaje);
                }
            }
            em.getTransaction().commit();
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
            Categoria idCategoriaOld = persistentProducto.getIdCategoria();
            Categoria idCategoriaNew = producto.getIdCategoria();
            List<Promocioncantidad> promocioncantidadListOld = persistentProducto.getPromocioncantidadList();
            List<Promocioncantidad> promocioncantidadListNew = producto.getPromocioncantidadList();
            List<Detallecomanda> detallecomandaListOld = persistentProducto.getDetallecomandaList();
            List<Detallecomanda> detallecomandaListNew = producto.getDetallecomandaList();
            List<Promocionporcentaje> promocionporcentajeListOld = persistentProducto.getPromocionporcentajeList();
            List<Promocionporcentaje> promocionporcentajeListNew = producto.getPromocionporcentajeList();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getIdCategoria());
                producto.setIdCategoria(idCategoriaNew);
            }
            List<Promocioncantidad> attachedPromocioncantidadListNew = new ArrayList<Promocioncantidad>();
            for (Promocioncantidad promocioncantidadListNewPromocioncantidadToAttach : promocioncantidadListNew) {
                promocioncantidadListNewPromocioncantidadToAttach = em.getReference(promocioncantidadListNewPromocioncantidadToAttach.getClass(), promocioncantidadListNewPromocioncantidadToAttach.getIdPromocionCantidad());
                attachedPromocioncantidadListNew.add(promocioncantidadListNewPromocioncantidadToAttach);
            }
            promocioncantidadListNew = attachedPromocioncantidadListNew;
            producto.setPromocioncantidadList(promocioncantidadListNew);
            List<Detallecomanda> attachedDetallecomandaListNew = new ArrayList<Detallecomanda>();
            for (Detallecomanda detallecomandaListNewDetallecomandaToAttach : detallecomandaListNew) {
                detallecomandaListNewDetallecomandaToAttach = em.getReference(detallecomandaListNewDetallecomandaToAttach.getClass(), detallecomandaListNewDetallecomandaToAttach.getIdDetalleComanda());
                attachedDetallecomandaListNew.add(detallecomandaListNewDetallecomandaToAttach);
            }
            detallecomandaListNew = attachedDetallecomandaListNew;
            producto.setDetallecomandaList(detallecomandaListNew);
            List<Promocionporcentaje> attachedPromocionporcentajeListNew = new ArrayList<Promocionporcentaje>();
            for (Promocionporcentaje promocionporcentajeListNewPromocionporcentajeToAttach : promocionporcentajeListNew) {
                promocionporcentajeListNewPromocionporcentajeToAttach = em.getReference(promocionporcentajeListNewPromocionporcentajeToAttach.getClass(), promocionporcentajeListNewPromocionporcentajeToAttach.getIdPromocionPorcentaje());
                attachedPromocionporcentajeListNew.add(promocionporcentajeListNewPromocionporcentajeToAttach);
            }
            promocionporcentajeListNew = attachedPromocionporcentajeListNew;
            producto.setPromocionporcentajeList(promocionporcentajeListNew);
            producto = em.merge(producto);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getProductoList().remove(producto);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getProductoList().add(producto);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (Promocioncantidad promocioncantidadListOldPromocioncantidad : promocioncantidadListOld) {
                if (!promocioncantidadListNew.contains(promocioncantidadListOldPromocioncantidad)) {
                    promocioncantidadListOldPromocioncantidad.setIdProducto(null);
                    promocioncantidadListOldPromocioncantidad = em.merge(promocioncantidadListOldPromocioncantidad);
                }
            }
            for (Promocioncantidad promocioncantidadListNewPromocioncantidad : promocioncantidadListNew) {
                if (!promocioncantidadListOld.contains(promocioncantidadListNewPromocioncantidad)) {
                    Producto oldIdProductoOfPromocioncantidadListNewPromocioncantidad = promocioncantidadListNewPromocioncantidad.getIdProducto();
                    promocioncantidadListNewPromocioncantidad.setIdProducto(producto);
                    promocioncantidadListNewPromocioncantidad = em.merge(promocioncantidadListNewPromocioncantidad);
                    if (oldIdProductoOfPromocioncantidadListNewPromocioncantidad != null && !oldIdProductoOfPromocioncantidadListNewPromocioncantidad.equals(producto)) {
                        oldIdProductoOfPromocioncantidadListNewPromocioncantidad.getPromocioncantidadList().remove(promocioncantidadListNewPromocioncantidad);
                        oldIdProductoOfPromocioncantidadListNewPromocioncantidad = em.merge(oldIdProductoOfPromocioncantidadListNewPromocioncantidad);
                    }
                }
            }
            for (Detallecomanda detallecomandaListOldDetallecomanda : detallecomandaListOld) {
                if (!detallecomandaListNew.contains(detallecomandaListOldDetallecomanda)) {
                    detallecomandaListOldDetallecomanda.setIdProducto(null);
                    detallecomandaListOldDetallecomanda = em.merge(detallecomandaListOldDetallecomanda);
                }
            }
            for (Detallecomanda detallecomandaListNewDetallecomanda : detallecomandaListNew) {
                if (!detallecomandaListOld.contains(detallecomandaListNewDetallecomanda)) {
                    Producto oldIdProductoOfDetallecomandaListNewDetallecomanda = detallecomandaListNewDetallecomanda.getIdProducto();
                    detallecomandaListNewDetallecomanda.setIdProducto(producto);
                    detallecomandaListNewDetallecomanda = em.merge(detallecomandaListNewDetallecomanda);
                    if (oldIdProductoOfDetallecomandaListNewDetallecomanda != null && !oldIdProductoOfDetallecomandaListNewDetallecomanda.equals(producto)) {
                        oldIdProductoOfDetallecomandaListNewDetallecomanda.getDetallecomandaList().remove(detallecomandaListNewDetallecomanda);
                        oldIdProductoOfDetallecomandaListNewDetallecomanda = em.merge(oldIdProductoOfDetallecomandaListNewDetallecomanda);
                    }
                }
            }
            for (Promocionporcentaje promocionporcentajeListOldPromocionporcentaje : promocionporcentajeListOld) {
                if (!promocionporcentajeListNew.contains(promocionporcentajeListOldPromocionporcentaje)) {
                    promocionporcentajeListOldPromocionporcentaje.setIdProducto(null);
                    promocionporcentajeListOldPromocionporcentaje = em.merge(promocionporcentajeListOldPromocionporcentaje);
                }
            }
            for (Promocionporcentaje promocionporcentajeListNewPromocionporcentaje : promocionporcentajeListNew) {
                if (!promocionporcentajeListOld.contains(promocionporcentajeListNewPromocionporcentaje)) {
                    Producto oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje = promocionporcentajeListNewPromocionporcentaje.getIdProducto();
                    promocionporcentajeListNewPromocionporcentaje.setIdProducto(producto);
                    promocionporcentajeListNewPromocionporcentaje = em.merge(promocionporcentajeListNewPromocionporcentaje);
                    if (oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje != null && !oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje.equals(producto)) {
                        oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje.getPromocionporcentajeList().remove(promocionporcentajeListNewPromocionporcentaje);
                        oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje = em.merge(oldIdProductoOfPromocionporcentajeListNewPromocionporcentaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            List<Promocioncantidad> promocioncantidadList = producto.getPromocioncantidadList();
            for (Promocioncantidad promocioncantidadListPromocioncantidad : promocioncantidadList) {
                promocioncantidadListPromocioncantidad.setIdProducto(null);
                promocioncantidadListPromocioncantidad = em.merge(promocioncantidadListPromocioncantidad);
            }
            List<Detallecomanda> detallecomandaList = producto.getDetallecomandaList();
            for (Detallecomanda detallecomandaListDetallecomanda : detallecomandaList) {
                detallecomandaListDetallecomanda.setIdProducto(null);
                detallecomandaListDetallecomanda = em.merge(detallecomandaListDetallecomanda);
            }
            List<Promocionporcentaje> promocionporcentajeList = producto.getPromocionporcentajeList();
            for (Promocionporcentaje promocionporcentajeListPromocionporcentaje : promocionporcentajeList) {
                promocionporcentajeListPromocionporcentaje.setIdProducto(null);
                promocionporcentajeListPromocionporcentaje = em.merge(promocionporcentajeListPromocionporcentaje);
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
