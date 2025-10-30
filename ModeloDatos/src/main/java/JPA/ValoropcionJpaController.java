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
import Entidades.Detalleopcion;
import Entidades.Valoropcion;
import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class ValoropcionJpaController implements Serializable {

    public ValoropcionJpaController() {
    }
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Valoropcion valoropcion) throws PreexistingEntityException, Exception {
        if (valoropcion.getDetalleopcionList() == null) {
            valoropcion.setDetalleopcionList(new ArrayList<Detalleopcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcionproducto idOpcionProducto = valoropcion.getIdOpcionProducto();
            if (idOpcionProducto != null) {
                idOpcionProducto = em.getReference(idOpcionProducto.getClass(), idOpcionProducto.getIdOpcionProducto());
                valoropcion.setIdOpcionProducto(idOpcionProducto);
            }
            List<Detalleopcion> attachedDetalleopcionList = new ArrayList<Detalleopcion>();
            for (Detalleopcion detalleopcionListDetalleopcionToAttach : valoropcion.getDetalleopcionList()) {
                detalleopcionListDetalleopcionToAttach = em.getReference(detalleopcionListDetalleopcionToAttach.getClass(), detalleopcionListDetalleopcionToAttach.getIdDetalleOpcion());
                attachedDetalleopcionList.add(detalleopcionListDetalleopcionToAttach);
            }
            valoropcion.setDetalleopcionList(attachedDetalleopcionList);
            em.persist(valoropcion);
            if (idOpcionProducto != null) {
                idOpcionProducto.getValoropcionList().add(valoropcion);
                idOpcionProducto = em.merge(idOpcionProducto);
            }
            for (Detalleopcion detalleopcionListDetalleopcion : valoropcion.getDetalleopcionList()) {
                Valoropcion oldIdValorOpcionOfDetalleopcionListDetalleopcion = detalleopcionListDetalleopcion.getIdValorOpcion();
                detalleopcionListDetalleopcion.setIdValorOpcion(valoropcion);
                detalleopcionListDetalleopcion = em.merge(detalleopcionListDetalleopcion);
                if (oldIdValorOpcionOfDetalleopcionListDetalleopcion != null) {
                    oldIdValorOpcionOfDetalleopcionListDetalleopcion.getDetalleopcionList().remove(detalleopcionListDetalleopcion);
                    oldIdValorOpcionOfDetalleopcionListDetalleopcion = em.merge(oldIdValorOpcionOfDetalleopcionListDetalleopcion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findValoropcion(valoropcion.getIdValorOpcion()) != null) {
                throw new PreexistingEntityException("Valoropcion " + valoropcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Valoropcion valoropcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoropcion persistentValoropcion = em.find(Valoropcion.class, valoropcion.getIdValorOpcion());
            Opcionproducto idOpcionProductoOld = persistentValoropcion.getIdOpcionProducto();
            Opcionproducto idOpcionProductoNew = valoropcion.getIdOpcionProducto();
            List<Detalleopcion> detalleopcionListOld = persistentValoropcion.getDetalleopcionList();
            List<Detalleopcion> detalleopcionListNew = valoropcion.getDetalleopcionList();
            List<String> illegalOrphanMessages = null;
            for (Detalleopcion detalleopcionListOldDetalleopcion : detalleopcionListOld) {
                if (!detalleopcionListNew.contains(detalleopcionListOldDetalleopcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Detalleopcion " + detalleopcionListOldDetalleopcion + " since its idValorOpcion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idOpcionProductoNew != null) {
                idOpcionProductoNew = em.getReference(idOpcionProductoNew.getClass(), idOpcionProductoNew.getIdOpcionProducto());
                valoropcion.setIdOpcionProducto(idOpcionProductoNew);
            }
            List<Detalleopcion> attachedDetalleopcionListNew = new ArrayList<Detalleopcion>();
            for (Detalleopcion detalleopcionListNewDetalleopcionToAttach : detalleopcionListNew) {
                detalleopcionListNewDetalleopcionToAttach = em.getReference(detalleopcionListNewDetalleopcionToAttach.getClass(), detalleopcionListNewDetalleopcionToAttach.getIdDetalleOpcion());
                attachedDetalleopcionListNew.add(detalleopcionListNewDetalleopcionToAttach);
            }
            detalleopcionListNew = attachedDetalleopcionListNew;
            valoropcion.setDetalleopcionList(detalleopcionListNew);
            valoropcion = em.merge(valoropcion);
            if (idOpcionProductoOld != null && !idOpcionProductoOld.equals(idOpcionProductoNew)) {
                idOpcionProductoOld.getValoropcionList().remove(valoropcion);
                idOpcionProductoOld = em.merge(idOpcionProductoOld);
            }
            if (idOpcionProductoNew != null && !idOpcionProductoNew.equals(idOpcionProductoOld)) {
                idOpcionProductoNew.getValoropcionList().add(valoropcion);
                idOpcionProductoNew = em.merge(idOpcionProductoNew);
            }
            for (Detalleopcion detalleopcionListNewDetalleopcion : detalleopcionListNew) {
                if (!detalleopcionListOld.contains(detalleopcionListNewDetalleopcion)) {
                    Valoropcion oldIdValorOpcionOfDetalleopcionListNewDetalleopcion = detalleopcionListNewDetalleopcion.getIdValorOpcion();
                    detalleopcionListNewDetalleopcion.setIdValorOpcion(valoropcion);
                    detalleopcionListNewDetalleopcion = em.merge(detalleopcionListNewDetalleopcion);
                    if (oldIdValorOpcionOfDetalleopcionListNewDetalleopcion != null && !oldIdValorOpcionOfDetalleopcionListNewDetalleopcion.equals(valoropcion)) {
                        oldIdValorOpcionOfDetalleopcionListNewDetalleopcion.getDetalleopcionList().remove(detalleopcionListNewDetalleopcion);
                        oldIdValorOpcionOfDetalleopcionListNewDetalleopcion = em.merge(oldIdValorOpcionOfDetalleopcionListNewDetalleopcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = valoropcion.getIdValorOpcion();
                if (findValoropcion(id) == null) {
                    throw new NonexistentEntityException("The valoropcion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Valoropcion valoropcion;
            try {
                valoropcion = em.getReference(Valoropcion.class, id);
                valoropcion.getIdValorOpcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The valoropcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Detalleopcion> detalleopcionListOrphanCheck = valoropcion.getDetalleopcionList();
            for (Detalleopcion detalleopcionListOrphanCheckDetalleopcion : detalleopcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Valoropcion (" + valoropcion + ") cannot be destroyed since the Detalleopcion " + detalleopcionListOrphanCheckDetalleopcion + " in its detalleopcionList field has a non-nullable idValorOpcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Opcionproducto idOpcionProducto = valoropcion.getIdOpcionProducto();
            if (idOpcionProducto != null) {
                idOpcionProducto.getValoropcionList().remove(valoropcion);
                idOpcionProducto = em.merge(idOpcionProducto);
            }
            em.remove(valoropcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Valoropcion> findValoropcionEntities() {
        return findValoropcionEntities(true, -1, -1);
    }

    public List<Valoropcion> findValoropcionEntities(int maxResults, int firstResult) {
        return findValoropcionEntities(false, maxResults, firstResult);
    }

    private List<Valoropcion> findValoropcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Valoropcion.class));
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

    public Valoropcion findValoropcion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Valoropcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getValoropcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Valoropcion> rt = cq.from(Valoropcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
