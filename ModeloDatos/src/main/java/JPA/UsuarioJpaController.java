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
import Entidades.Cajaefectivo;
import java.util.ArrayList;
import java.util.List;
import Entidades.Comanda;
import Entidades.Rol;
import Entidades.Usuario;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author usuario
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getCajaefectivoList() == null) {
            usuario.setCajaefectivoList(new ArrayList<Cajaefectivo>());
        }
        if (usuario.getComandaList() == null) {
            usuario.setComandaList(new ArrayList<Comanda>());
        }
        if (usuario.getRolList() == null) {
            usuario.setRolList(new ArrayList<Rol>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cajaefectivo> attachedCajaefectivoList = new ArrayList<Cajaefectivo>();
            for (Cajaefectivo cajaefectivoListCajaefectivoToAttach : usuario.getCajaefectivoList()) {
                cajaefectivoListCajaefectivoToAttach = em.getReference(cajaefectivoListCajaefectivoToAttach.getClass(), cajaefectivoListCajaefectivoToAttach.getIdCorte());
                attachedCajaefectivoList.add(cajaefectivoListCajaefectivoToAttach);
            }
            usuario.setCajaefectivoList(attachedCajaefectivoList);
            List<Comanda> attachedComandaList = new ArrayList<Comanda>();
            for (Comanda comandaListComandaToAttach : usuario.getComandaList()) {
                comandaListComandaToAttach = em.getReference(comandaListComandaToAttach.getClass(), comandaListComandaToAttach.getIdComanda());
                attachedComandaList.add(comandaListComandaToAttach);
            }
            usuario.setComandaList(attachedComandaList);
            List<Rol> attachedRolList = new ArrayList<Rol>();
            for (Rol rolListRolToAttach : usuario.getRolList()) {
                rolListRolToAttach = em.getReference(rolListRolToAttach.getClass(), rolListRolToAttach.getIdRol());
                attachedRolList.add(rolListRolToAttach);
            }
            usuario.setRolList(attachedRolList);
            em.persist(usuario);
            for (Cajaefectivo cajaefectivoListCajaefectivo : usuario.getCajaefectivoList()) {
                Usuario oldIdUsuarioOfCajaefectivoListCajaefectivo = cajaefectivoListCajaefectivo.getIdUsuario();
                cajaefectivoListCajaefectivo.setIdUsuario(usuario);
                cajaefectivoListCajaefectivo = em.merge(cajaefectivoListCajaefectivo);
                if (oldIdUsuarioOfCajaefectivoListCajaefectivo != null) {
                    oldIdUsuarioOfCajaefectivoListCajaefectivo.getCajaefectivoList().remove(cajaefectivoListCajaefectivo);
                    oldIdUsuarioOfCajaefectivoListCajaefectivo = em.merge(oldIdUsuarioOfCajaefectivoListCajaefectivo);
                }
            }
            for (Comanda comandaListComanda : usuario.getComandaList()) {
                Usuario oldIdUsuarioOfComandaListComanda = comandaListComanda.getIdUsuario();
                comandaListComanda.setIdUsuario(usuario);
                comandaListComanda = em.merge(comandaListComanda);
                if (oldIdUsuarioOfComandaListComanda != null) {
                    oldIdUsuarioOfComandaListComanda.getComandaList().remove(comandaListComanda);
                    oldIdUsuarioOfComandaListComanda = em.merge(oldIdUsuarioOfComandaListComanda);
                }
            }
            for (Rol rolListRol : usuario.getRolList()) {
                Usuario oldIdUsuarioOfRolListRol = rolListRol.getIdUsuario();
                rolListRol.setIdUsuario(usuario);
                rolListRol = em.merge(rolListRol);
                if (oldIdUsuarioOfRolListRol != null) {
                    oldIdUsuarioOfRolListRol.getRolList().remove(rolListRol);
                    oldIdUsuarioOfRolListRol = em.merge(oldIdUsuarioOfRolListRol);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<Cajaefectivo> cajaefectivoListOld = persistentUsuario.getCajaefectivoList();
            List<Cajaefectivo> cajaefectivoListNew = usuario.getCajaefectivoList();
            List<Comanda> comandaListOld = persistentUsuario.getComandaList();
            List<Comanda> comandaListNew = usuario.getComandaList();
            List<Rol> rolListOld = persistentUsuario.getRolList();
            List<Rol> rolListNew = usuario.getRolList();
            List<Cajaefectivo> attachedCajaefectivoListNew = new ArrayList<Cajaefectivo>();
            for (Cajaefectivo cajaefectivoListNewCajaefectivoToAttach : cajaefectivoListNew) {
                cajaefectivoListNewCajaefectivoToAttach = em.getReference(cajaefectivoListNewCajaefectivoToAttach.getClass(), cajaefectivoListNewCajaefectivoToAttach.getIdCorte());
                attachedCajaefectivoListNew.add(cajaefectivoListNewCajaefectivoToAttach);
            }
            cajaefectivoListNew = attachedCajaefectivoListNew;
            usuario.setCajaefectivoList(cajaefectivoListNew);
            List<Comanda> attachedComandaListNew = new ArrayList<Comanda>();
            for (Comanda comandaListNewComandaToAttach : comandaListNew) {
                comandaListNewComandaToAttach = em.getReference(comandaListNewComandaToAttach.getClass(), comandaListNewComandaToAttach.getIdComanda());
                attachedComandaListNew.add(comandaListNewComandaToAttach);
            }
            comandaListNew = attachedComandaListNew;
            usuario.setComandaList(comandaListNew);
            List<Rol> attachedRolListNew = new ArrayList<Rol>();
            for (Rol rolListNewRolToAttach : rolListNew) {
                rolListNewRolToAttach = em.getReference(rolListNewRolToAttach.getClass(), rolListNewRolToAttach.getIdRol());
                attachedRolListNew.add(rolListNewRolToAttach);
            }
            rolListNew = attachedRolListNew;
            usuario.setRolList(rolListNew);
            usuario = em.merge(usuario);
            for (Cajaefectivo cajaefectivoListOldCajaefectivo : cajaefectivoListOld) {
                if (!cajaefectivoListNew.contains(cajaefectivoListOldCajaefectivo)) {
                    cajaefectivoListOldCajaefectivo.setIdUsuario(null);
                    cajaefectivoListOldCajaefectivo = em.merge(cajaefectivoListOldCajaefectivo);
                }
            }
            for (Cajaefectivo cajaefectivoListNewCajaefectivo : cajaefectivoListNew) {
                if (!cajaefectivoListOld.contains(cajaefectivoListNewCajaefectivo)) {
                    Usuario oldIdUsuarioOfCajaefectivoListNewCajaefectivo = cajaefectivoListNewCajaefectivo.getIdUsuario();
                    cajaefectivoListNewCajaefectivo.setIdUsuario(usuario);
                    cajaefectivoListNewCajaefectivo = em.merge(cajaefectivoListNewCajaefectivo);
                    if (oldIdUsuarioOfCajaefectivoListNewCajaefectivo != null && !oldIdUsuarioOfCajaefectivoListNewCajaefectivo.equals(usuario)) {
                        oldIdUsuarioOfCajaefectivoListNewCajaefectivo.getCajaefectivoList().remove(cajaefectivoListNewCajaefectivo);
                        oldIdUsuarioOfCajaefectivoListNewCajaefectivo = em.merge(oldIdUsuarioOfCajaefectivoListNewCajaefectivo);
                    }
                }
            }
            for (Comanda comandaListOldComanda : comandaListOld) {
                if (!comandaListNew.contains(comandaListOldComanda)) {
                    comandaListOldComanda.setIdUsuario(null);
                    comandaListOldComanda = em.merge(comandaListOldComanda);
                }
            }
            for (Comanda comandaListNewComanda : comandaListNew) {
                if (!comandaListOld.contains(comandaListNewComanda)) {
                    Usuario oldIdUsuarioOfComandaListNewComanda = comandaListNewComanda.getIdUsuario();
                    comandaListNewComanda.setIdUsuario(usuario);
                    comandaListNewComanda = em.merge(comandaListNewComanda);
                    if (oldIdUsuarioOfComandaListNewComanda != null && !oldIdUsuarioOfComandaListNewComanda.equals(usuario)) {
                        oldIdUsuarioOfComandaListNewComanda.getComandaList().remove(comandaListNewComanda);
                        oldIdUsuarioOfComandaListNewComanda = em.merge(oldIdUsuarioOfComandaListNewComanda);
                    }
                }
            }
            for (Rol rolListOldRol : rolListOld) {
                if (!rolListNew.contains(rolListOldRol)) {
                    rolListOldRol.setIdUsuario(null);
                    rolListOldRol = em.merge(rolListOldRol);
                }
            }
            for (Rol rolListNewRol : rolListNew) {
                if (!rolListOld.contains(rolListNewRol)) {
                    Usuario oldIdUsuarioOfRolListNewRol = rolListNewRol.getIdUsuario();
                    rolListNewRol.setIdUsuario(usuario);
                    rolListNewRol = em.merge(rolListNewRol);
                    if (oldIdUsuarioOfRolListNewRol != null && !oldIdUsuarioOfRolListNewRol.equals(usuario)) {
                        oldIdUsuarioOfRolListNewRol.getRolList().remove(rolListNewRol);
                        oldIdUsuarioOfRolListNewRol = em.merge(oldIdUsuarioOfRolListNewRol);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Cajaefectivo> cajaefectivoList = usuario.getCajaefectivoList();
            for (Cajaefectivo cajaefectivoListCajaefectivo : cajaefectivoList) {
                cajaefectivoListCajaefectivo.setIdUsuario(null);
                cajaefectivoListCajaefectivo = em.merge(cajaefectivoListCajaefectivo);
            }
            List<Comanda> comandaList = usuario.getComandaList();
            for (Comanda comandaListComanda : comandaList) {
                comandaListComanda.setIdUsuario(null);
                comandaListComanda = em.merge(comandaListComanda);
            }
            List<Rol> rolList = usuario.getRolList();
            for (Rol rolListRol : rolList) {
                rolListRol.setIdUsuario(null);
                rolListRol = em.merge(rolListRol);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
