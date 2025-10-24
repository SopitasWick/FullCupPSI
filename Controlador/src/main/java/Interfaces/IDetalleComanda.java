/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Detallecomanda;

/**
 *
 * @author Sergio Arturo
 */
public interface IDetalleComanda {
    
    void guardarDetallecomanda(Detallecomanda detalleComa) throws Exception;
    
    void actualizarDetallecomanda(Detallecomanda comanda) throws Exception;
    
    void eliminarDetallecomanda(Integer id) throws Exception;
    
    Detallecomanda obtenerDetallecomanda(Integer id);
    
}
