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
import Entidades.Cajaefectivo;
import Entidades.Venta;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author usuario
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venta venta) throws PreexistingEntityException, Exception {
        if (venta.getCajaefectivoList() == null) {
            venta.setCajaefectivoList(new ArrayList<Cajaefectivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda idComanda = venta.getIdComanda();
            if (idComanda != null) {
                idComanda = em.getReference(idComanda.getClass(), idComanda.getIdComanda());
                venta.setIdComanda(idComanda);
            }
            List<Cajaefectivo> attachedCajaefectivoList = new ArrayList<Cajaefectivo>();
            for (Cajaefectivo cajaefectivoListCajaefectivoToAttach : venta.getCajaefectivoList()) {
                cajaefectivoListCajaefectivoToAttach = em.getReference(cajaefectivoListCajaefectivoToAttach.getClass(), cajaefectivoListCajaefectivoToAttach.getIdCorte());
                attachedCajaefectivoList.add(cajaefectivoListCajaefectivoToAttach);
            }
            venta.setCajaefectivoList(attachedCajaefectivoList);
            em.persist(venta);
            if (idComanda != null) {
                idComanda.getVentaList().add(venta);
                idComanda = em.merge(idComanda);
            }
            for (Cajaefectivo cajaefectivoListCajaefectivo : venta.getCajaefectivoList()) {
                Venta oldIdVentaOfCajaefectivoListCajaefectivo = cajaefectivoListCajaefectivo.getIdVenta();
                cajaefectivoListCajaefectivo.setIdVenta(venta);
                cajaefectivoListCajaefectivo = em.merge(cajaefectivoListCajaefectivo);
                if (oldIdVentaOfCajaefectivoListCajaefectivo != null) {
                    oldIdVentaOfCajaefectivoListCajaefectivo.getCajaefectivoList().remove(cajaefectivoListCajaefectivo);
                    oldIdVentaOfCajaefectivoListCajaefectivo = em.merge(oldIdVentaOfCajaefectivoListCajaefectivo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVenta(venta.getIdVenta()) != null) {
                throw new PreexistingEntityException("Venta " + venta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venta venta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta persistentVenta = em.find(Venta.class, venta.getIdVenta());
            Comanda idComandaOld = persistentVenta.getIdComanda();
            Comanda idComandaNew = venta.getIdComanda();
            List<Cajaefectivo> cajaefectivoListOld = persistentVenta.getCajaefectivoList();
            List<Cajaefectivo> cajaefectivoListNew = venta.getCajaefectivoList();
            if (idComandaNew != null) {
                idComandaNew = em.getReference(idComandaNew.getClass(), idComandaNew.getIdComanda());
                venta.setIdComanda(idComandaNew);
            }
            List<Cajaefectivo> attachedCajaefectivoListNew = new ArrayList<Cajaefectivo>();
            for (Cajaefectivo cajaefectivoListNewCajaefectivoToAttach : cajaefectivoListNew) {
                cajaefectivoListNewCajaefectivoToAttach = em.getReference(cajaefectivoListNewCajaefectivoToAttach.getClass(), cajaefectivoListNewCajaefectivoToAttach.getIdCorte());
                attachedCajaefectivoListNew.add(cajaefectivoListNewCajaefectivoToAttach);
            }
            cajaefectivoListNew = attachedCajaefectivoListNew;
            venta.setCajaefectivoList(cajaefectivoListNew);
            venta = em.merge(venta);
            if (idComandaOld != null && !idComandaOld.equals(idComandaNew)) {
                idComandaOld.getVentaList().remove(venta);
                idComandaOld = em.merge(idComandaOld);
            }
            if (idComandaNew != null && !idComandaNew.equals(idComandaOld)) {
                idComandaNew.getVentaList().add(venta);
                idComandaNew = em.merge(idComandaNew);
            }
            for (Cajaefectivo cajaefectivoListOldCajaefectivo : cajaefectivoListOld) {
                if (!cajaefectivoListNew.contains(cajaefectivoListOldCajaefectivo)) {
                    cajaefectivoListOldCajaefectivo.setIdVenta(null);
                    cajaefectivoListOldCajaefectivo = em.merge(cajaefectivoListOldCajaefectivo);
                }
            }
            for (Cajaefectivo cajaefectivoListNewCajaefectivo : cajaefectivoListNew) {
                if (!cajaefectivoListOld.contains(cajaefectivoListNewCajaefectivo)) {
                    Venta oldIdVentaOfCajaefectivoListNewCajaefectivo = cajaefectivoListNewCajaefectivo.getIdVenta();
                    cajaefectivoListNewCajaefectivo.setIdVenta(venta);
                    cajaefectivoListNewCajaefectivo = em.merge(cajaefectivoListNewCajaefectivo);
                    if (oldIdVentaOfCajaefectivoListNewCajaefectivo != null && !oldIdVentaOfCajaefectivoListNewCajaefectivo.equals(venta)) {
                        oldIdVentaOfCajaefectivoListNewCajaefectivo.getCajaefectivoList().remove(cajaefectivoListNewCajaefectivo);
                        oldIdVentaOfCajaefectivoListNewCajaefectivo = em.merge(oldIdVentaOfCajaefectivoListNewCajaefectivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venta.getIdVenta();
                if (findVenta(id) == null) {
                    throw new NonexistentEntityException("The venta with id " + id + " no longer exists.");
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
            Venta venta;
            try {
                venta = em.getReference(Venta.class, id);
                venta.getIdVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venta with id " + id + " no longer exists.", enfe);
            }
            Comanda idComanda = venta.getIdComanda();
            if (idComanda != null) {
                idComanda.getVentaList().remove(venta);
                idComanda = em.merge(idComanda);
            }
            List<Cajaefectivo> cajaefectivoList = venta.getCajaefectivoList();
            for (Cajaefectivo cajaefectivoListCajaefectivo : cajaefectivoList) {
                cajaefectivoListCajaefectivo.setIdVenta(null);
                cajaefectivoListCajaefectivo = em.merge(cajaefectivoListCajaefectivo);
            }
            em.remove(venta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venta> findVentaEntities() {
        return findVentaEntities(true, -1, -1);
    }

    public List<Venta> findVentaEntities(int maxResults, int firstResult) {
        return findVentaEntities(false, maxResults, firstResult);
    }

    private List<Venta> findVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venta.class));
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

    public Venta findVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venta.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venta> rt = cq.from(Venta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
