/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Producto;
import Facades.IFachadaProductoModel;
import JPA.ProductoJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarProductoModel  implements IFachadaProductoModel  {
     
    ProductoJpaController productoJPA;
    
    public GestionarProductoModel() {
        this.productoJPA = new ProductoJpaController();
    }
    
    @Override
    public Producto obtenerProducto(Integer id) throws Exception {
        return productoJPA.findProducto(id);
    }
    
    @Override
    public void agregarProducto(Producto producto) throws Exception {
        productoJPA.create(producto);
    }
    
    @Override
    public void editarProducto(Producto producto) throws Exception {
        productoJPA.edit(producto);
    }
    
    @Override
    public void eliminarProducto(Integer id) throws Exception {
        productoJPA.destroy(id);
    }
    
    @Override
    public List<Producto> obtenerTodosLosProductos() throws Exception {
        return productoJPA.findProductoEntities();
    }
    
    @Override
    public List<Producto> obtenerProductosPorCategoria(Integer idCategoria) throws Exception {
        return productoJPA.findProductosByIdCategoria(idCategoria);
    }
}
