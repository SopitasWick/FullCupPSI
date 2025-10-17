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
import Entidades.Promocion;
import Entidades.Promocioncantidad;
import java.util.ArrayList;
import java.util.List;
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
public class PromocionJpaController implements Serializable {

    public PromocionJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Promocion promocion) throws PreexistingEntityException, Exception {
        if (promocion.getPromocioncantidadList() == null) {
            promocion.setPromocioncantidadList(new ArrayList<Promocioncantidad>());
        }
        if (promocion.getPromocionporcentajeList() == null) {
            promocion.setPromocionporcentajeList(new ArrayList<Promocionporcentaje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = promocion.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                promocion.setIdProducto(idProducto);
            }
            List<Promocioncantidad> attachedPromocioncantidadList = new ArrayList<Promocioncantidad>();
            for (Promocioncantidad promocioncantidadListPromocioncantidadToAttach : promocion.getPromocioncantidadList()) {
                promocioncantidadListPromocioncantidadToAttach = em.getReference(promocioncantidadListPromocioncantidadToAttach.getClass(), promocioncantidadListPromocioncantidadToAttach.getIdPromocionCantidad());
                attachedPromocioncantidadList.add(promocioncantidadListPromocioncantidadToAttach);
            }
            promocion.setPromocioncantidadList(attachedPromocioncantidadList);
            List<Promocionporcentaje> attachedPromocionporcentajeList = new ArrayList<Promocionporcentaje>();
            for (Promocionporcentaje promocionporcentajeListPromocionporcentajeToAttach : promocion.getPromocionporcentajeList()) {
                promocionporcentajeListPromocionporcentajeToAttach = em.getReference(promocionporcentajeListPromocionporcentajeToAttach.getClass(), promocionporcentajeListPromocionporcentajeToAttach.getIdPromocionPorcentaje());
                attachedPromocionporcentajeList.add(promocionporcentajeListPromocionporcentajeToAttach);
            }
            promocion.setPromocionporcentajeList(attachedPromocionporcentajeList);
            em.persist(promocion);
            if (idProducto != null) {
                idProducto.getPromocionList().add(promocion);
                idProducto = em.merge(idProducto);
            }
            for (Promocioncantidad promocioncantidadListPromocioncantidad : promocion.getPromocioncantidadList()) {
                Promocion oldIdPromocionOfPromocioncantidadListPromocioncantidad = promocioncantidadListPromocioncantidad.getIdPromocion();
                promocioncantidadListPromocioncantidad.setIdPromocion(promocion);
                promocioncantidadListPromocioncantidad = em.merge(promocioncantidadListPromocioncantidad);
                if (oldIdPromocionOfPromocioncantidadListPromocioncantidad != null) {
                    oldIdPromocionOfPromocioncantidadListPromocioncantidad.getPromocioncantidadList().remove(promocioncantidadListPromocioncantidad);
                    oldIdPromocionOfPromocioncantidadListPromocioncantidad = em.merge(oldIdPromocionOfPromocioncantidadListPromocioncantidad);
                }
            }
            for (Promocionporcentaje promocionporcentajeListPromocionporcentaje : promocion.getPromocionporcentajeList()) {
                Promocion oldIdPromocionOfPromocionporcentajeListPromocionporcentaje = promocionporcentajeListPromocionporcentaje.getIdPromocion();
                promocionporcentajeListPromocionporcentaje.setIdPromocion(promocion);
                promocionporcentajeListPromocionporcentaje = em.merge(promocionporcentajeListPromocionporcentaje);
                if (oldIdPromocionOfPromocionporcentajeListPromocionporcentaje != null) {
                    oldIdPromocionOfPromocionporcentajeListPromocionporcentaje.getPromocionporcentajeList().remove(promocionporcentajeListPromocionporcentaje);
                    oldIdPromocionOfPromocionporcentajeListPromocionporcentaje = em.merge(oldIdPromocionOfPromocionporcentajeListPromocionporcentaje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPromocion(promocion.getIdPromocion()) != null) {
                throw new PreexistingEntityException("Promocion " + promocion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Promocion promocion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Promocion persistentPromocion = em.find(Promocion.class, promocion.getIdPromocion());
            Producto idProductoOld = persistentPromocion.getIdProducto();
            Producto idProductoNew = promocion.getIdProducto();
            List<Promocioncantidad> promocioncantidadListOld = persistentPromocion.getPromocioncantidadList();
            List<Promocioncantidad> promocioncantidadListNew = promocion.getPromocioncantidadList();
            List<Promocionporcentaje> promocionporcentajeListOld = persistentPromocion.getPromocionporcentajeList();
            List<Promocionporcentaje> promocionporcentajeListNew = promocion.getPromocionporcentajeList();
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getIdProducto());
                promocion.setIdProducto(idProductoNew);
            }
            List<Promocioncantidad> attachedPromocioncantidadListNew = new ArrayList<Promocioncantidad>();
            for (Promocioncantidad promocioncantidadListNewPromocioncantidadToAttach : promocioncantidadListNew) {
                promocioncantidadListNewPromocioncantidadToAttach = em.getReference(promocioncantidadListNewPromocioncantidadToAttach.getClass(), promocioncantidadListNewPromocioncantidadToAttach.getIdPromocionCantidad());
                attachedPromocioncantidadListNew.add(promocioncantidadListNewPromocioncantidadToAttach);
            }
            promocioncantidadListNew = attachedPromocioncantidadListNew;
            promocion.setPromocioncantidadList(promocioncantidadListNew);
            List<Promocionporcentaje> attachedPromocionporcentajeListNew = new ArrayList<Promocionporcentaje>();
            for (Promocionporcentaje promocionporcentajeListNewPromocionporcentajeToAttach : promocionporcentajeListNew) {
                promocionporcentajeListNewPromocionporcentajeToAttach = em.getReference(promocionporcentajeListNewPromocionporcentajeToAttach.getClass(), promocionporcentajeListNewPromocionporcentajeToAttach.getIdPromocionPorcentaje());
                attachedPromocionporcentajeListNew.add(promocionporcentajeListNewPromocionporcentajeToAttach);
            }
            promocionporcentajeListNew = attachedPromocionporcentajeListNew;
            promocion.setPromocionporcentajeList(promocionporcentajeListNew);
            promocion = em.merge(promocion);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getPromocionList().remove(promocion);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getPromocionList().add(promocion);
                idProductoNew = em.merge(idProductoNew);
            }
            for (Promocioncantidad promocioncantidadListOldPromocioncantidad : promocioncantidadListOld) {
                if (!promocioncantidadListNew.contains(promocioncantidadListOldPromocioncantidad)) {
                    promocioncantidadListOldPromocioncantidad.setIdPromocion(null);
                    promocioncantidadListOldPromocioncantidad = em.merge(promocioncantidadListOldPromocioncantidad);
                }
            }
            for (Promocioncantidad promocioncantidadListNewPromocioncantidad : promocioncantidadListNew) {
                if (!promocioncantidadListOld.contains(promocioncantidadListNewPromocioncantidad)) {
                    Promocion oldIdPromocionOfPromocioncantidadListNewPromocioncantidad = promocioncantidadListNewPromocioncantidad.getIdPromocion();
                    promocioncantidadListNewPromocioncantidad.setIdPromocion(promocion);
                    promocioncantidadListNewPromocioncantidad = em.merge(promocioncantidadListNewPromocioncantidad);
                    if (oldIdPromocionOfPromocioncantidadListNewPromocioncantidad != null && !oldIdPromocionOfPromocioncantidadListNewPromocioncantidad.equals(promocion)) {
                        oldIdPromocionOfPromocioncantidadListNewPromocioncantidad.getPromocioncantidadList().remove(promocioncantidadListNewPromocioncantidad);
                        oldIdPromocionOfPromocioncantidadListNewPromocioncantidad = em.merge(oldIdPromocionOfPromocioncantidadListNewPromocioncantidad);
                    }
                }
            }
            for (Promocionporcentaje promocionporcentajeListOldPromocionporcentaje : promocionporcentajeListOld) {
                if (!promocionporcentajeListNew.contains(promocionporcentajeListOldPromocionporcentaje)) {
                    promocionporcentajeListOldPromocionporcentaje.setIdPromocion(null);
                    promocionporcentajeListOldPromocionporcentaje = em.merge(promocionporcentajeListOldPromocionporcentaje);
                }
            }
            for (Promocionporcentaje promocionporcentajeListNewPromocionporcentaje : promocionporcentajeListNew) {
                if (!promocionporcentajeListOld.contains(promocionporcentajeListNewPromocionporcentaje)) {
                    Promocion oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje = promocionporcentajeListNewPromocionporcentaje.getIdPromocion();
                    promocionporcentajeListNewPromocionporcentaje.setIdPromocion(promocion);
                    promocionporcentajeListNewPromocionporcentaje = em.merge(promocionporcentajeListNewPromocionporcentaje);
                    if (oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje != null && !oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje.equals(promocion)) {
                        oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje.getPromocionporcentajeList().remove(promocionporcentajeListNewPromocionporcentaje);
                        oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje = em.merge(oldIdPromocionOfPromocionporcentajeListNewPromocionporcentaje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = promocion.getIdPromocion();
                if (findPromocion(id) == null) {
                    throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.");
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
            Promocion promocion;
            try {
                promocion = em.getReference(Promocion.class, id);
                promocion.getIdPromocion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The promocion with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = promocion.getIdProducto();
            if (idProducto != null) {
                idProducto.getPromocionList().remove(promocion);
                idProducto = em.merge(idProducto);
            }
            List<Promocioncantidad> promocioncantidadList = promocion.getPromocioncantidadList();
            for (Promocioncantidad promocioncantidadListPromocioncantidad : promocioncantidadList) {
                promocioncantidadListPromocioncantidad.setIdPromocion(null);
                promocioncantidadListPromocioncantidad = em.merge(promocioncantidadListPromocioncantidad);
            }
            List<Promocionporcentaje> promocionporcentajeList = promocion.getPromocionporcentajeList();
            for (Promocionporcentaje promocionporcentajeListPromocionporcentaje : promocionporcentajeList) {
                promocionporcentajeListPromocionporcentaje.setIdPromocion(null);
                promocionporcentajeListPromocionporcentaje = em.merge(promocionporcentajeListPromocionporcentaje);
            }
            em.remove(promocion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Promocion> findPromocionEntities() {
        return findPromocionEntities(true, -1, -1);
    }

    public List<Promocion> findPromocionEntities(int maxResults, int firstResult) {
        return findPromocionEntities(false, maxResults, firstResult);
    }

    private List<Promocion> findPromocionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promocion.class));
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

    public Promocion findPromocion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Promocion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPromocionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promocion> rt = cq.from(Promocion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
