/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Productoopcion;
import Facades.IFachadaProductoOpcionModel;
import JPA.ProductoopcionJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarProductoOpcionModel implements IFachadaProductoOpcionModel{
    
    
    ProductoopcionJpaController productoOpcionJPA;
    
    public GestionarProductoOpcionModel() {
        this.productoOpcionJPA = new ProductoopcionJpaController();
    }
    

    @Override
    public void agregarProductoOpcion(Productoopcion productoOpcion) throws Exception {
        productoOpcionJPA.create(productoOpcion);
    }

    @Override
    public void eliminarProductoOpcion(Productoopcion productoOpcion) throws Exception {
        productoOpcionJPA.destroy(productoOpcion.getIdProductoOpcion());
    }

    @Override
    public List<Productoopcion> findProductoopcionByProducto(Integer idProducto) {
        return productoOpcionJPA.findProductoopcionByProducto(idProducto);
    }
    
}
