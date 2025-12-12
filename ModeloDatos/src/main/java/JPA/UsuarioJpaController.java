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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

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

        // ⭐ ENCRIPTAR LA CONTRASEÑA ANTES DE GUARDAR
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
            usuario.setPassword(hash);
        }

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            List<Cajaefectivo> attachedCajaefectivoList = new ArrayList<>();
            for (Cajaefectivo ce : usuario.getCajaefectivoList()) {
                ce = em.getReference(ce.getClass(), ce.getIdCorte());
                attachedCajaefectivoList.add(ce);
            }
            usuario.setCajaefectivoList(attachedCajaefectivoList);

            List<Comanda> attachedComandaList = new ArrayList<>();
            for (Comanda c : usuario.getComandaList()) {
                c = em.getReference(c.getClass(), c.getIdComanda());
                attachedComandaList.add(c);
            }
            usuario.setComandaList(attachedComandaList);

            List<Rol> attachedRolList = new ArrayList<>();
            for (Rol r : usuario.getRolList()) {
                r = em.getReference(r.getClass(), r.getIdRol());
                attachedRolList.add(r);
            }
            usuario.setRolList(attachedRolList);

            em.persist(usuario);

            for (Cajaefectivo ce : usuario.getCajaefectivoList()) {
                Usuario old = ce.getIdUsuario();
                ce.setIdUsuario(usuario);
                ce = em.merge(ce);
                if (old != null) {
                    old.getCajaefectivoList().remove(ce);
                    em.merge(old);
                }
            }

            for (Comanda c : usuario.getComandaList()) {
                Usuario old = c.getIdUsuario();
                c.setIdUsuario(usuario);
                c = em.merge(c);
                if (old != null) {
                    old.getComandaList().remove(c);
                    em.merge(old);
                }
            }

            for (Rol r : usuario.getRolList()) {
                Usuario old = r.getIdUsuario();
                r.setIdUsuario(usuario);
                r = em.merge(r);
                if (old != null) {
                    old.getRolList().remove(r);
                    em.merge(old);
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
        if (persistentUsuario == null) {
            throw new NonexistentEntityException("Usuario no encontrado");
        }

        // --------------------------
        // Contraseña: encriptar solo si viene algo nuevo
        // --------------------------
        String oldPasswordHash = persistentUsuario.getPassword();
        String newPassword = usuario.getPassword();

        if (newPassword != null && !newPassword.isEmpty()) {
            String nuevoHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            usuario.setPassword(nuevoHash);
        } else {
            usuario.setPassword(oldPasswordHash);
        }

        // --------------------------
        // Mantener listas relacionadas sin cambios
        // --------------------------
        usuario.setCajaefectivoList(persistentUsuario.getCajaefectivoList());
        usuario.setComandaList(persistentUsuario.getComandaList());
        usuario.setRolList(persistentUsuario.getRolList()); // no tocamos roles

        em.merge(usuario);
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



    public Usuario findUsuarioByNombre(String nombreUsuario) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre",
                    Usuario.class
            );
            query.setParameter("nombre", nombreUsuario);

            List<Usuario> resultados = query.getResultList();

            if (resultados.isEmpty()) {
                return null; // No encontrado
            } else {
                return resultados.get(0); // Regresamos el primero
            }

        } finally {
            em.close();
        }
    }
    
    
    
    public boolean existeUsuarioPorNombre(String nombreUsuario) {
        EntityManager em = getEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.nombreUsuario = :nombre",
                    Long.class
            ).setParameter("nombre", nombreUsuario)
             .getSingleResult();

            return count > 0;
        } finally {
            em.close();
        }
    }
    
    
    public Usuario iniciarSesion(String nombreUsuario, String passwordIngresada){   
        EntityManager em = getEntityManager();
               try {
                   // 1. Buscar usuario por nombre
                   TypedQuery<Usuario> query = em.createQuery(
                           "SELECT u FROM Usuario u WHERE u.nombreUsuario = :nombre",
                           Usuario.class
                   );
                   query.setParameter("nombre", nombreUsuario);
                   query.setMaxResults(1); // Solo queremos uno

                   List<Usuario> resultados = query.getResultList();

                   if (resultados.isEmpty()) {
                       return null; // No existe usuario
                   }

                   Usuario usuario = resultados.get(0);

                   // 2. Validar contraseña con BCrypt
                   if (BCrypt.checkpw(passwordIngresada, usuario.getPassword())) {
                       return usuario; // Credenciales correctas → devolver entidad
                   }

                   return null; // Contraseña incorrecta

               } finally {
                   em.close();
               }
   }

    
    
    
    public void generarNuevoPorDefecto(){
        
        try {
        Usuario usuarioAdmin = new Usuario();
        usuarioAdmin.setEstado(1);
        usuarioAdmin.setNombreUsuario("admin");
        usuarioAdmin.setPassword("admin");
        usuarioAdmin.setIdUsuario(1);
        
        
        
        Rol rol = new Rol();
        rol.setIdUsuario(usuarioAdmin);
        rol.setNombre("Administrador");
        rol.setIdRol(1);
        
        Rol rol2 = new Rol();
        rol2.setIdUsuario(usuarioAdmin);
        rol2.setNombre("Atendiente");
        rol2.setIdRol(2);
        
        RolJpaController ctrlRol = new RolJpaController();
        
        
            create(usuarioAdmin);
            ctrlRol.create(rol);
            ctrlRol.create(rol2);

        

        } catch (Exception ex) {
            Logger.getLogger(UsuarioJpaController.class.getName()).log(Level.SEVERE, null, ex);
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
