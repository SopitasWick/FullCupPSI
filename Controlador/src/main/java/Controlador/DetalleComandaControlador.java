/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Entidades.Detallecomanda;
import Interfaces.IDetalleComanda;
import JPA.DetallecomandaJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class DetalleComandaControlador implements IDetalleComanda{
    
    
    private final DetallecomandaJpaController detallecomandaController;
    
    public DetalleComandaControlador() {
        this.detallecomandaController = new DetallecomandaJpaController();
    }
    

    //faltan validaciones
    @Override
    public void guardarDetallecomanda(Detallecomanda detalleComa) throws Exception {
        if (detalleComa == null) {
            throw new Exception("El detalle de la  comanda no puede ser nula");
        }
        if (detalleComa.getIdDetalleComanda() == null) {
            List<Detallecomanda> todas = detallecomandaController.findDetallecomandaEntities();
            int maxId = todas.stream()
                    .mapToInt(c -> c.getIdDetalleComanda()!= null ? c.getIdDetalleComanda() : 0)
                    .max()
                    .orElse(0);
            
            
            detalleComa.setIdDetalleComanda(maxId + 1);
        }
        
        
//        if (detalleComa.get) {
//            comanda.setFechaHoracomanda(new java.util.Date());
//        }
//        if (comanda.getEstadoComanda() == null || comanda.getEstadoComanda().isEmpty()) {
//            comanda.setEstadoComanda("Abierta");
//        }
//        if (comanda.getTotalComanda() == null) {
//            comanda.setTotalComanda(0.0f);
        
        detallecomandaController.create(detalleComa);
    }

    @Override
    public void actualizarDetallecomanda(Detallecomanda comanda) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarDetallecomanda(Integer id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Detallecomanda obtenerDetallecomanda(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
