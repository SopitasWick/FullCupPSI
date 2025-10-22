/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Entidades.Comanda;
import Interfaces.IComandaControlador;
import JPA.ComandaJpaController;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Orlando Leyva
 */
public class ComandaControlador implements IComandaControlador{
         private final ComandaJpaController comandaController;
    
    public ComandaControlador() {
        this.comandaController = new ComandaJpaController();
    }
    
//    @Override
//    public void guardarComanda(Comanda comanda) throws Exception {
//        if (comanda == null) {
//            throw new Exception("La comanda no puede ser nula");
//        }
//        comandaController.create(comanda);
//    }
    @Override
    public void guardarComanda(Comanda comanda) throws Exception {
        if (comanda == null) {
            throw new Exception("La comanda no puede ser nula");
        }
        if (comanda.getIdComanda() == null) {
            List<Comanda> todas = comandaController.findComandaEntities();
            int maxId = todas.stream()
                    .mapToInt(c -> c.getIdComanda() != null ? c.getIdComanda() : 0)
                    .max()
                    .orElse(0);
            comanda.setIdComanda(maxId + 1);
        }
        if (comanda.getFechaHoracomanda() == null) {
            comanda.setFechaHoracomanda(new java.util.Date());
        }
        if (comanda.getEstadoComanda() == null || comanda.getEstadoComanda().isEmpty()) {
            comanda.setEstadoComanda("Abierta");
        }
        if (comanda.getTotalComanda() == null) {
            comanda.setTotalComanda(0.0f);
        }
        comandaController.create(comanda);
    }
    
    @Override
    public void actualizarComanda(Comanda comanda) throws Exception {
        if (comanda == null) {
            throw new Exception("La comanda no puede ser nula");
        }
        if (comanda.getIdComanda() == null) {
            throw new Exception("La comanda debe tener un ID");
        }
        comandaController.edit(comanda);
    }
    
    @Override
    public void eliminarComanda(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("ID de comanda inválido");
        }
        comandaController.destroy(id);
    }
    
    @Override
    public Comanda obtenerComanda(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return comandaController.findComanda(id);
    }
    
    @Override
    public List<Comanda> obtenerTodasLasComandas() {
        return comandaController.findComandaEntities();
    }
    
    @Override
    public List<Comanda> obtenerComandasPaginadas(int maxResults, int firstResult) {
        if (maxResults <= 0) {
            maxResults = 10;
        }
        if (firstResult < 0) {
            firstResult = 0;
        }
        return comandaController.findComandaEntities(maxResults, firstResult);
    }
    
    @Override
    public int contarComandas() {
        return comandaController.getComandaCount();
    }
    
    @Override
    public List<Comanda> obtenerComandasPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            return List.of();
        }
        return comandaController.findComandaEntities()
                .stream()
                .filter(c -> c.getIdUsuario() != null && 
                             c.getIdUsuario().getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }
    

}
