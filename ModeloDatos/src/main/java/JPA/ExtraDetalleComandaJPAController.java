/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Detallecomanda;
import Entidades.Extra;
import Entidades.ExtrasDetalleComanda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sergio Arturo
 */
public class ExtraDetalleComandaJPAController {
    
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");

    
    public ExtraDetalleComandaJPAController(){

    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    


    public ExtrasDetalleComanda guardarExtraDetalleComanda(ExtrasDetalleComanda extraDetalleComanda) {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = getEntityManager();
            transaction = em.getTransaction();

            transaction.begin();

            em.persist(extraDetalleComanda);

            transaction.commit();

            return extraDetalleComanda;

        }
        catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al guardar la entidad ExtraDetalleComanda: " + e.getMessage());
            throw new RuntimeException("Fallo en la persistencia del extraDetalleComanda.", e);

        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    public ExtrasDetalleComanda actualizarExtraDetalleComanda(ExtrasDetalleComanda extraDetalleComanda) {
        EntityManager em = null;
        EntityTransaction transaction = null;
        ExtrasDetalleComanda extraActualizado = null;

        try {
            em = getEntityManager();
            transaction = em.getTransaction();

            transaction.begin();

            extraActualizado = em.merge(extraDetalleComanda);

            transaction.commit();

            return extraActualizado;

        }
        catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al actualizar la entidad ExtraDetalleComanda: " + e.getMessage());
            throw new RuntimeException("Fallo en la actualización del extraDetalleComanda.", e);

        }
        finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    
    
    public void eliminarExtrasPorDetalleComanda(Detallecomanda detalleComanda) {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = getEntityManager(); // Método para obtener tu EntityManager
            transaction = em.getTransaction();

            transaction.begin();
            
            // 1. Crear la JPQL de eliminación masiva
            // Usamos 'e.detalleComanda' que es el nombre del atributo de relación en tu clase ExtrasDetalleComanda
            String jpql = "DELETE FROM ExtrasDetalleComanda e WHERE e.detalleComanda = :detalleComandaParam";
            
            Query query = em.createQuery(jpql);

            // 2. Establecer el parámetro con el objeto Detallecomanda completo
            // JPA lo maneja automáticamente comparando el ID
            query.setParameter("detalleComandaParam", detalleComanda);

            // 3. Ejecutar la consulta. executeUpdate() devuelve el número de filas eliminadas.
            int filasEliminadas = query.executeUpdate();
            
            transaction.commit();
            
            // Opcional: imprimir las filas eliminadas para depuración
            System.out.println(filasEliminadas + " ExtrasDetalleComanda eliminados para Detallecomanda ID: " + detalleComanda.getIdDetalleComanda());

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            // Manejo de errores 
            System.err.println("Error al eliminar los extras del DetalleComanda: " + e.getMessage());
            // Es buena práctica relanzar la excepción para que el llamador sepa que falló.
            throw new RuntimeException("Fallo en la eliminación masiva de ExtrasDetalleComanda.", e); 

        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    
    
    
public List <ExtrasDetalleComanda> obtenerTodosLosExtrasPorDetalleComanda(Detallecomanda idDetalleComanda) {
    EntityManager em = null;
    List<ExtrasDetalleComanda> extras = null;

    try {
        em = getEntityManager();
    
        // 1. JPQL CORREGIDA: Usa e.detalleComanda (el atributo Java)
        // Usamos un nombre de parámetro distinto para evitar confusiones.
        TypedQuery<ExtrasDetalleComanda> query = em.createQuery(
            "SELECT e FROM ExtrasDetalleComanda e WHERE e.detalleComanda = :detalleComandaParam", 
            ExtrasDetalleComanda.class
        );
        
        // 2. CORRECCIÓN: Establecer el objeto Detallecomanda COMPLETO como parámetro.
        query.setParameter("detalleComandaParam", idDetalleComanda); 

        // 3. Ejecutar la consulta y obtener la lista de resultados
        extras = query.getResultList();
        
        return extras;

    } catch (Exception e) {
        // En caso de error, es mejor lanzar una excepción de aplicación envuelta,
        // pero se mantiene tu estructura de manejo de errores:
        System.err.println("Error al obtener ExtrasDetalleComanda: " + e.getMessage());
        throw new RuntimeException("Fallo en la consulta de extras.", e);

    } finally {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
     
     
    
}
