/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Cajaefectivo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Usuario;
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
public class CajaefectivoJpaController implements Serializable {

    public CajaefectivoJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cajaefectivo cajaefectivo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = cajaefectivo.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cajaefectivo.setIdUsuario(idUsuario);
            }
            em.persist(cajaefectivo);
            if (idUsuario != null) {
                idUsuario.getCajaefectivoList().add(cajaefectivo);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCajaefectivo(cajaefectivo.getIdCorte()) != null) {
                throw new PreexistingEntityException("Cajaefectivo " + cajaefectivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Cajaefectivo obtenerCajaAbierta() {

        EntityManager em = getEntityManager();

        try {
            List<Cajaefectivo> lista = em.createQuery(
                    "SELECT c FROM Cajaefectivo c WHERE c.estado = 0", Cajaefectivo.class)
                    .getResultList();

            return lista.isEmpty() ? null : lista.get(0);

        } finally {
            em.close();
        }
    }

    public void edit(Cajaefectivo cajaefectivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cajaefectivo persistentCajaefectivo = em.find(Cajaefectivo.class, cajaefectivo.getIdCorte());
            Usuario idUsuarioOld = persistentCajaefectivo.getIdUsuario();
            Usuario idUsuarioNew = cajaefectivo.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cajaefectivo.setIdUsuario(idUsuarioNew);
            }
            cajaefectivo = em.merge(cajaefectivo);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCajaefectivoList().remove(cajaefectivo);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCajaefectivoList().add(cajaefectivo);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cajaefectivo.getIdCorte();
                if (findCajaefectivo(id) == null) {
                    throw new NonexistentEntityException("The cajaefectivo with id " + id + " no longer exists.");
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
            Cajaefectivo cajaefectivo;
            try {
                cajaefectivo = em.getReference(Cajaefectivo.class, id);
                cajaefectivo.getIdCorte();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cajaefectivo with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = cajaefectivo.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCajaefectivoList().remove(cajaefectivo);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(cajaefectivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cajaefectivo> findCajaefectivoEntities() {
        return findCajaefectivoEntities(true, -1, -1);
    }

    public List<Cajaefectivo> findCajaefectivoEntities(int maxResults, int firstResult) {
        return findCajaefectivoEntities(false, maxResults, firstResult);
    }

    private List<Cajaefectivo> findCajaefectivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cajaefectivo.class));
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

    public Cajaefectivo findCajaefectivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cajaefectivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCajaefectivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cajaefectivo> rt = cq.from(Cajaefectivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
