/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Extra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sergio Arturo
 */
public class ExtraJPAController {
    
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    
    public ExtraJPAController(){

    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    


    public Extra guardarExtra(Extra extra) {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = getEntityManager();
            transaction = em.getTransaction();

            transaction.begin();

            em.persist(extra);

            transaction.commit();

            return extra;

        }
        catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar la entidad Extra: " + e.getMessage());
            throw new RuntimeException("Fallo en la persistencia del extra.", e);

        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public Extra actualizarExtra(Extra extra) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        Extra extraActualizado = null;

        try {
            em = getEntityManager();
            transaction = em.getTransaction();

            transaction.begin();

            extraActualizado = em.merge(extra);

            transaction.commit();

            return extraActualizado;

        }
        catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar la entidad Extra: " + e.getMessage());
            throw new RuntimeException("Fallo en la actualización del extra.", e);

        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    
    
    
     public List <Extra> obtenerTodosLosExtras() {
        EntityManager em = null;
       // EntityTransaction transaction = null;
        List<Extra> extras = null;

        try {
            em = getEntityManager();
            
            // 1. Crear la  JPQL
            TypedQuery<Extra> query = em.createQuery("SELECT e FROM Extra e", Extra.class);
            
            // 2. Ejecutar la consulta y obtener la lista de resultados
            extras = query.getResultList();
            
            return extras;


        }
        catch (Exception e) {
            
            System.err.println("Error al actualizar la entidad Extra: " + e.getMessage());
            throw new RuntimeException("Fallo en la actualización del extra.", e);

        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    
    
    
}
