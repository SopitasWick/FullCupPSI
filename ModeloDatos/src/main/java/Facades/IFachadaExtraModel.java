/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.ExtrasProductos;
import Entidades.Producto;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaExtraModel {
    
    
    public ExtrasProductos obtenerExtras(Integer id) throws Exception;
    
    public void agregarExtrasProductos(ExtrasProductos extra) throws Exception;
    
    public void editarExtrasProductos(ExtrasProductos extra) throws Exception;
    
    public void eliminarExtrasProductos(Integer id) throws Exception;
    
    public List<ExtrasProductos> obtenerTodosLosExtrasPorProducto(Producto producto) throws Exception;
    
        public List<ExtrasProductos> obtenerTodosLosExtras() throws Exception;

}
