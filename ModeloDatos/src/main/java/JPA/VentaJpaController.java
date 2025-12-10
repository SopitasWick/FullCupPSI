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
import Entidades.Venta;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class VentaJpaController implements Serializable {

    public VentaJpaController() {
    }

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Venta> obtenerVentasDeHoy() {
        EntityManager em = getEntityManager();

        LocalDate hoy = LocalDate.now();
        LocalDateTime ini = hoy.atStartOfDay();
        LocalDateTime fin = hoy.atTime(LocalTime.MAX);

        // Convertir LocalDateTime → java.util.Date
        Date iniDate = Date.from(ini.atZone(ZoneId.systemDefault()).toInstant());
        Date finDate = Date.from(fin.atZone(ZoneId.systemDefault()).toInstant());

        try {
            return em.createQuery(
                    "SELECT v FROM Venta v WHERE v.fechaHora BETWEEN :ini AND :fin", Venta.class)
                    .setParameter("ini", iniDate)
                    .setParameter("fin", finDate)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void create(Venta venta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda idComanda = venta.getIdComanda();
            if (idComanda != null) {
                idComanda = em.getReference(idComanda.getClass(), idComanda.getIdComanda());
                venta.setIdComanda(idComanda);
            }
            em.persist(venta);
            if (idComanda != null) {
                idComanda.getVentaList().add(venta);
                idComanda = em.merge(idComanda);
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
            if (idComandaNew != null) {
                idComandaNew = em.getReference(idComandaNew.getClass(), idComandaNew.getIdComanda());
                venta.setIdComanda(idComandaNew);
            }
            venta = em.merge(venta);
            if (idComandaOld != null && !idComandaOld.equals(idComandaNew)) {
                idComandaOld.getVentaList().remove(venta);
                idComandaOld = em.merge(idComandaOld);
            }
            if (idComandaNew != null && !idComandaNew.equals(idComandaOld)) {
                idComandaNew.getVentaList().add(venta);
                idComandaNew = em.merge(idComandaNew);
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
