/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaDetallesComandaControlador {
    
    
    public Detallecomanda agregarDetallesComandas(Detallecomanda detallecomanda) throws Exception;
    
    public void editarDetallesComandas(Detallecomanda detallecomanda) throws Exception;
    
    public void eliminarDetallesComandas(Integer id) throws Exception;    
    
    public List<Detallecomanda> obtenerDetallesComandasPorComanda(Comanda comanda) throws Exception;
    
    
}
