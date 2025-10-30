/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Opcionproducto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Productoopcion;
import java.util.ArrayList;
import java.util.List;
import Entidades.Valoropcion;
import JPA.exceptions.IllegalOrphanException;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class OpcionproductoJpaController implements Serializable {

    public OpcionproductoJpaController() {
    }
     private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Opcionproducto opcionproducto) throws PreexistingEntityException, Exception {
        if (opcionproducto.getProductoopcionList() == null) {
            opcionproducto.setProductoopcionList(new ArrayList<Productoopcion>());
        }
        if (opcionproducto.getValoropcionList() == null) {
            opcionproducto.setValoropcionList(new ArrayList<Valoropcion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Productoopcion> attachedProductoopcionList = new ArrayList<Productoopcion>();
            for (Productoopcion productoopcionListProductoopcionToAttach : opcionproducto.getProductoopcionList()) {
                productoopcionListProductoopcionToAttach = em.getReference(productoopcionListProductoopcionToAttach.getClass(), productoopcionListProductoopcionToAttach.getIdProductoOpcion());
                attachedProductoopcionList.add(productoopcionListProductoopcionToAttach);
            }
            opcionproducto.setProductoopcionList(attachedProductoopcionList);
            List<Valoropcion> attachedValoropcionList = new ArrayList<Valoropcion>();
            for (Valoropcion valoropcionListValoropcionToAttach : opcionproducto.getValoropcionList()) {
                valoropcionListValoropcionToAttach = em.getReference(valoropcionListValoropcionToAttach.getClass(), valoropcionListValoropcionToAttach.getIdValorOpcion());
                attachedValoropcionList.add(valoropcionListValoropcionToAttach);
            }
            opcionproducto.setValoropcionList(attachedValoropcionList);
            em.persist(opcionproducto);
            for (Productoopcion productoopcionListProductoopcion : opcionproducto.getProductoopcionList()) {
                Opcionproducto oldIdOpcionProductoOfProductoopcionListProductoopcion = productoopcionListProductoopcion.getIdOpcionProducto();
                productoopcionListProductoopcion.setIdOpcionProducto(opcionproducto);
                productoopcionListProductoopcion = em.merge(productoopcionListProductoopcion);
                if (oldIdOpcionProductoOfProductoopcionListProductoopcion != null) {
                    oldIdOpcionProductoOfProductoopcionListProductoopcion.getProductoopcionList().remove(productoopcionListProductoopcion);
                    oldIdOpcionProductoOfProductoopcionListProductoopcion = em.merge(oldIdOpcionProductoOfProductoopcionListProductoopcion);
                }
            }
            for (Valoropcion valoropcionListValoropcion : opcionproducto.getValoropcionList()) {
                Opcionproducto oldIdOpcionProductoOfValoropcionListValoropcion = valoropcionListValoropcion.getIdOpcionProducto();
                valoropcionListValoropcion.setIdOpcionProducto(opcionproducto);
                valoropcionListValoropcion = em.merge(valoropcionListValoropcion);
                if (oldIdOpcionProductoOfValoropcionListValoropcion != null) {
                    oldIdOpcionProductoOfValoropcionListValoropcion.getValoropcionList().remove(valoropcionListValoropcion);
                    oldIdOpcionProductoOfValoropcionListValoropcion = em.merge(oldIdOpcionProductoOfValoropcionListValoropcion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOpcionproducto(opcionproducto.getIdOpcionProducto()) != null) {
                throw new PreexistingEntityException("Opcionproducto " + opcionproducto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Opcionproducto opcionproducto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Opcionproducto persistentOpcionproducto = em.find(Opcionproducto.class, opcionproducto.getIdOpcionProducto());
            List<Productoopcion> productoopcionListOld = persistentOpcionproducto.getProductoopcionList();
            List<Productoopcion> productoopcionListNew = opcionproducto.getProductoopcionList();
            List<Valoropcion> valoropcionListOld = persistentOpcionproducto.getValoropcionList();
            List<Valoropcion> valoropcionListNew = opcionproducto.getValoropcionList();
            List<String> illegalOrphanMessages = null;
            for (Productoopcion productoopcionListOldProductoopcion : productoopcionListOld) {
                if (!productoopcionListNew.contains(productoopcionListOldProductoopcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Productoopcion " + productoopcionListOldProductoopcion + " since its idOpcionProducto field is not nullable.");
                }
            }
            for (Valoropcion valoropcionListOldValoropcion : valoropcionListOld) {
                if (!valoropcionListNew.contains(valoropcionListOldValoropcion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Valoropcion " + valoropcionListOldValoropcion + " since its idOpcionProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Productoopcion> attachedProductoopcionListNew = new ArrayList<Productoopcion>();
            for (Productoopcion productoopcionListNewProductoopcionToAttach : productoopcionListNew) {
                productoopcionListNewProductoopcionToAttach = em.getReference(productoopcionListNewProductoopcionToAttach.getClass(), productoopcionListNewProductoopcionToAttach.getIdProductoOpcion());
                attachedProductoopcionListNew.add(productoopcionListNewProductoopcionToAttach);
            }
            productoopcionListNew = attachedProductoopcionListNew;
            opcionproducto.setProductoopcionList(productoopcionListNew);
            List<Valoropcion> attachedValoropcionListNew = new ArrayList<Valoropcion>();
            for (Valoropcion valoropcionListNewValoropcionToAttach : valoropcionListNew) {
                valoropcionListNewValoropcionToAttach = em.getReference(valoropcionListNewValoropcionToAttach.getClass(), valoropcionListNewValoropcionToAttach.getIdValorOpcion());
                attachedValoropcionListNew.add(valoropcionListNewValoropcionToAttach);
            }
            valoropcionListNew = attachedValoropcionListNew;
            opcionproducto.setValoropcionList(valoropcionListNew);
            opcionproducto = em.merge(opcionproducto);
            for (Productoopcion productoopcionListNewProductoopcion : productoopcionListNew) {
                if (!productoopcionListOld.contains(productoopcionListNewProductoopcion)) {
                    Opcionproducto oldIdOpcionProductoOfProductoopcionListNewProductoopcion = productoopcionListNewProductoopcion.getIdOpcionProducto();
                    productoopcionListNewProductoopcion.setIdOpcionProducto(opcionproducto);
                    productoopcionListNewProductoopcion = em.merge(productoopcionListNewProductoopcion);
                    if (oldIdOpcionProductoOfProductoopcionListNewProductoopcion != null && !oldIdOpcionProductoOfProductoopcionListNewProductoopcion.equals(opcionproducto)) {
                        oldIdOpcionProductoOfProductoopcionListNewProductoopcion.getProductoopcionList().remove(productoopcionListNewProductoopcion);
                        oldIdOpcionProductoOfProductoopcionListNewProductoopcion = em.merge(oldIdOpcionProductoOfProductoopcionListNewProductoopcion);
                    }
                }
            }
            for (Valoropcion valoropcionListNewValoropcion : valoropcionListNew) {
                if (!valoropcionListOld.contains(valoropcionListNewValoropcion)) {
                    Opcionproducto oldIdOpcionProductoOfValoropcionListNewValoropcion = valoropcionListNewValoropcion.getIdOpcionProducto();
                    valoropcionListNewValoropcion.setIdOpcionProducto(opcionproducto);
                    valoropcionListNewValoropcion = em.merge(valoropcionListNewValoropcion);
                    if (oldIdOpcionProductoOfValoropcionListNewValoropcion != null && !oldIdOpcionProductoOfValoropcionListNewValoropcion.equals(opcionproducto)) {
                        oldIdOpcionProductoOfValoropcionListNewValoropcion.getValoropcionList().remove(valoropcionListNewValoropcion);
                        oldIdOpcionProductoOfValoropcionListNewValoropcion = em.merge(oldIdOpcionProductoOfValoropcionListNewValoropcion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = opcionproducto.getIdOpcionProducto();
                if (findOpcionproducto(id) == null) {
                    throw new NonexistentEntityException("The opcionproducto with id " + id + " no longer exists.");
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
            Opcionproducto opcionproducto;
            try {
                opcionproducto = em.getReference(Opcionproducto.class, id);
                opcionproducto.getIdOpcionProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opcionproducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Productoopcion> productoopcionListOrphanCheck = opcionproducto.getProductoopcionList();
            for (Productoopcion productoopcionListOrphanCheckProductoopcion : productoopcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Opcionproducto (" + opcionproducto + ") cannot be destroyed since the Productoopcion " + productoopcionListOrphanCheckProductoopcion + " in its productoopcionList field has a non-nullable idOpcionProducto field.");
            }
            List<Valoropcion> valoropcionListOrphanCheck = opcionproducto.getValoropcionList();
            for (Valoropcion valoropcionListOrphanCheckValoropcion : valoropcionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Opcionproducto (" + opcionproducto + ") cannot be destroyed since the Valoropcion " + valoropcionListOrphanCheckValoropcion + " in its valoropcionList field has a non-nullable idOpcionProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(opcionproducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Opcionproducto> findOpcionproductoEntities() {
        return findOpcionproductoEntities(true, -1, -1);
    }

    public List<Opcionproducto> findOpcionproductoEntities(int maxResults, int firstResult) {
        return findOpcionproductoEntities(false, maxResults, firstResult);
    }

    private List<Opcionproducto> findOpcionproductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Opcionproducto.class));
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

    public Opcionproducto findOpcionproducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Opcionproducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpcionproductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Opcionproducto> rt = cq.from(Opcionproducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
