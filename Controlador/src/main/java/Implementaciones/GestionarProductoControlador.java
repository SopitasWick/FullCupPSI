/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Producto;
import Entidades.Valoropcion;
import Facades.IFachadaProductoControlador;
import Facades.IFachadaProductoModel;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarProductoControlador implements IFachadaProductoControlador {
      IFachadaProductoModel productoModel;
    
    public GestionarProductoControlador() {
        this.productoModel = new GestionarProductoModel();
    }
    
    @Override
    public Producto obtenerProducto(Integer id) throws Exception {
        return productoModel.obtenerProducto(id);
    }
    
    @Override
    public void agregarProducto(Producto producto) throws Exception {
        productoModel.agregarProducto(producto);
    }
    
    @Override
    public void editarProducto(Producto producto) throws Exception {
        productoModel.editarProducto(producto);
    }
    
    @Override
    public void eliminarProducto(Integer id) throws Exception {
        productoModel.eliminarProducto(id);
    }
    
    @Override
    public List<Producto> obtenerTodosLosProductos() throws Exception {
        return productoModel.obtenerTodosLosProductos();
    }
    
    @Override
    public List<Producto> obtenerProductosPorCategoria(Integer idCategoria) throws Exception {
        return productoModel.obtenerProductosPorCategoria(idCategoria);
    }
    
    @Override
    public List<Producto> obtenerProductosPorNombre(String nombreProducto) throws Exception{
        return productoModel.obtenerProductosPorNombre(nombreProducto);
    }
    
    

    @Override
    public List<Valoropcion> obtenerDetallesPorProducto(Integer idProducto) throws Exception {
    return productoModel.obtenerDetallesPorProducto(idProducto);
    }

    @Override
    public void cambiarEstadoProductosByCategoria(Integer idCategoria, String estado) throws Exception {
            productoModel.cambiarEstadoProductosByCategoria(idCategoria, estado);
    }
}
