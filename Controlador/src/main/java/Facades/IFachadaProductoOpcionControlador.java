/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Productoopcion;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaProductoOpcionControlador {
    
    public void agregarProductoOpcion(Productoopcion productoOpcion) throws Exception;
    
    public void eliminarProductoOpcion(Productoopcion productoOpcion) throws Exception;
    
    public List<Productoopcion> findProductoopcionByProducto(Integer idProducto);
    
}
