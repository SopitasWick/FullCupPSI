/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Comanda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Usuario;
import Entidades.Venta;
import java.util.ArrayList;
import java.util.List;
import Entidades.Detallecomanda;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class ComandaJpaController implements Serializable {

    public ComandaJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comanda comanda) throws PreexistingEntityException, Exception {
        if (comanda.getVentaList() == null) {
            comanda.setVentaList(new ArrayList<Venta>());
        }
        if (comanda.getDetallecomandaList() == null) {
            comanda.setDetallecomandaList(new ArrayList<Detallecomanda>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = comanda.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                comanda.setIdUsuario(idUsuario);
            }
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : comanda.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getIdVenta());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            comanda.setVentaList(attachedVentaList);
            List<Detallecomanda> attachedDetallecomandaList = new ArrayList<Detallecomanda>();
            for (Detallecomanda detallecomandaListDetallecomandaToAttach : comanda.getDetallecomandaList()) {
                detallecomandaListDetallecomandaToAttach = em.getReference(detallecomandaListDetallecomandaToAttach.getClass(), detallecomandaListDetallecomandaToAttach.getIdDetalleComanda());
                attachedDetallecomandaList.add(detallecomandaListDetallecomandaToAttach);
            }
            comanda.setDetallecomandaList(attachedDetallecomandaList);
            em.persist(comanda);
            if (idUsuario != null) {
                idUsuario.getComandaList().add(comanda);
                idUsuario = em.merge(idUsuario);
            }
            for (Venta ventaListVenta : comanda.getVentaList()) {
                Comanda oldIdComandaOfVentaListVenta = ventaListVenta.getIdComanda();
                ventaListVenta.setIdComanda(comanda);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldIdComandaOfVentaListVenta != null) {
                    oldIdComandaOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldIdComandaOfVentaListVenta = em.merge(oldIdComandaOfVentaListVenta);
                }
            }
            for (Detallecomanda detallecomandaListDetallecomanda : comanda.getDetallecomandaList()) {
                Comanda oldIdComandaOfDetallecomandaListDetallecomanda = detallecomandaListDetallecomanda.getIdComanda();
                detallecomandaListDetallecomanda.setIdComanda(comanda);
                detallecomandaListDetallecomanda = em.merge(detallecomandaListDetallecomanda);
                if (oldIdComandaOfDetallecomandaListDetallecomanda != null) {
                    oldIdComandaOfDetallecomandaListDetallecomanda.getDetallecomandaList().remove(detallecomandaListDetallecomanda);
                    oldIdComandaOfDetallecomandaListDetallecomanda = em.merge(oldIdComandaOfDetallecomandaListDetallecomanda);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComanda(comanda.getIdComanda()) != null) {
                throw new PreexistingEntityException("Comanda " + comanda + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comanda comanda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comanda persistentComanda = em.find(Comanda.class, comanda.getIdComanda());
            Usuario idUsuarioOld = persistentComanda.getIdUsuario();
            Usuario idUsuarioNew = comanda.getIdUsuario();
            List<Venta> ventaListOld = persistentComanda.getVentaList();
            List<Venta> ventaListNew = comanda.getVentaList();
            List<Detallecomanda> detallecomandaListOld = persistentComanda.getDetallecomandaList();
            List<Detallecomanda> detallecomandaListNew = comanda.getDetallecomandaList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                comanda.setIdUsuario(idUsuarioNew);
            }
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getIdVenta());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            comanda.setVentaList(ventaListNew);
            List<Detallecomanda> attachedDetallecomandaListNew = new ArrayList<Detallecomanda>();
            for (Detallecomanda detallecomandaListNewDetallecomandaToAttach : detallecomandaListNew) {
                detallecomandaListNewDetallecomandaToAttach = em.getReference(detallecomandaListNewDetallecomandaToAttach.getClass(), detallecomandaListNewDetallecomandaToAttach.getIdDetalleComanda());
                attachedDetallecomandaListNew.add(detallecomandaListNewDetallecomandaToAttach);
            }
            detallecomandaListNew = attachedDetallecomandaListNew;
            comanda.setDetallecomandaList(detallecomandaListNew);
            comanda = em.merge(comanda);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getComandaList().remove(comanda);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getComandaList().add(comanda);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    ventaListOldVenta.setIdComanda(null);
                    ventaListOldVenta = em.merge(ventaListOldVenta);
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Comanda oldIdComandaOfVentaListNewVenta = ventaListNewVenta.getIdComanda();
                    ventaListNewVenta.setIdComanda(comanda);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldIdComandaOfVentaListNewVenta != null && !oldIdComandaOfVentaListNewVenta.equals(comanda)) {
                        oldIdComandaOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldIdComandaOfVentaListNewVenta = em.merge(oldIdComandaOfVentaListNewVenta);
                    }
                }
            }
            for (Detallecomanda detallecomandaListOldDetallecomanda : detallecomandaListOld) {
                if (!detallecomandaListNew.contains(detallecomandaListOldDetallecomanda)) {
                    detallecomandaListOldDetallecomanda.setIdComanda(null);
                    detallecomandaListOldDetallecomanda = em.merge(detallecomandaListOldDetallecomanda);
                }
            }
            for (Detallecomanda detallecomandaListNewDetallecomanda : detallecomandaListNew) {
                if (!detallecomandaListOld.contains(detallecomandaListNewDetallecomanda)) {
                    Comanda oldIdComandaOfDetallecomandaListNewDetallecomanda = detallecomandaListNewDetallecomanda.getIdComanda();
                    detallecomandaListNewDetallecomanda.setIdComanda(comanda);
                    detallecomandaListNewDetallecomanda = em.merge(detallecomandaListNewDetallecomanda);
                    if (oldIdComandaOfDetallecomandaListNewDetallecomanda != null && !oldIdComandaOfDetallecomandaListNewDetallecomanda.equals(comanda)) {
                        oldIdComandaOfDetallecomandaListNewDetallecomanda.getDetallecomandaList().remove(detallecomandaListNewDetallecomanda);
                        oldIdComandaOfDetallecomandaListNewDetallecomanda = em.merge(oldIdComandaOfDetallecomandaListNewDetallecomanda);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comanda.getIdComanda();
                if (findComanda(id) == null) {
                    throw new NonexistentEntityException("The comanda with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
public void actualizarTotalComanda(Integer idComanda, float nuevoTotal) throws Exception {
    EntityManager em = getEntityManager();
    try {
        em.getTransaction().begin();

        // Buscamos la comanda existente
        Comanda comanda = em.find(Comanda.class, idComanda);
        if (comanda == null) {
            throw new NonexistentEntityException("La comanda con ID " + idComanda + " no existe.");
        }

        // Actualizamos solo los campos necesarios
        comanda.setTotalComanda(nuevoTotal);
        comanda.setEstadoComanda("Abierta"); // si quieres actualizar el estado
        comanda.setFechaHoracomanda(new java.util.Date()); // opcional, si quieres registrar el último cambio

        // Guardamos cambios
        em.merge(comanda);

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
            Comanda comanda;
            try {
                comanda = em.getReference(Comanda.class, id);
                comanda.getIdComanda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comanda with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = comanda.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getComandaList().remove(comanda);
                idUsuario = em.merge(idUsuario);
            }
            List<Venta> ventaList = comanda.getVentaList();
            for (Venta ventaListVenta : ventaList) {
                ventaListVenta.setIdComanda(null);
                ventaListVenta = em.merge(ventaListVenta);
            }
            List<Detallecomanda> detallecomandaList = comanda.getDetallecomandaList();
            for (Detallecomanda detallecomandaListDetallecomanda : detallecomandaList) {
                detallecomandaListDetallecomanda.setIdComanda(null);
                detallecomandaListDetallecomanda = em.merge(detallecomandaListDetallecomanda);
            }
            em.remove(comanda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comanda> findComandaEntities() {
        return findComandaEntities(true, -1, -1);
    }

    public List<Comanda> findComandaEntities(int maxResults, int firstResult) {
        return findComandaEntities(false, maxResults, firstResult);
    }

    private List<Comanda> findComandaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comanda.class));
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

    public Comanda findComanda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comanda.class, id);
        } finally {
            em.close();
        }
    }

    public int getComandaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comanda> rt = cq.from(Comanda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
