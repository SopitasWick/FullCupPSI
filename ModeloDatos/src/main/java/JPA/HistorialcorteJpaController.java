/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JPA;

import Entidades.Cajaefectivo;
import Entidades.Historialcorte;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author ruben
 */
public class HistorialcorteJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public HistorialcorteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("itson.edu.mx_ModeloDatos_jar_1.0-FullCupSystemPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historialcorte historial) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    // ------------------------------
    // OBTENER TODOS LOS HISTORIALES
    // ------------------------------

    public List<Historialcorte> findHistorialcorteEntities() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historialcorte.class));
            Query q = em.createQuery(cq);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    // Método para registrar rápidamente un historial de corte
    public void registrarHistorial(Cajaefectivo caja, char tipoCorte) {
        Historialcorte h = new Historialcorte(
                caja.getIdCorte(),
                tipoCorte,
                caja.getMontoInicial(),
                caja.getMontoFinal(),
                caja.getDiferencia(),
                new java.util.Date()
        );

        create(h);
    }
}
