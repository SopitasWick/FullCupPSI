/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Producto;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public interface IFachadaProductoControlador {
    public Producto obtenerProducto(Integer id) throws Exception;
    
    public void agregarProducto(Producto producto) throws Exception;
    
    public void editarProducto(Producto producto) throws Exception;
    
    public void eliminarProducto(Integer id) throws Exception;
    
    public List<Producto> obtenerTodosLosProductos() throws Exception;
    
    public List<Producto> obtenerProductosPorCategoria(Integer idCategoria) throws Exception;
    
}
