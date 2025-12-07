/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Productoopcion;
import Facades.IFachadaProductoOpcionControlador;
import Facades.IFachadaProductoOpcionModel;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarProductoOpcionControlador implements IFachadaProductoOpcionControlador{
    
    IFachadaProductoOpcionModel productoOpcionModel;
    
    public GestionarProductoOpcionControlador() {
        this.productoOpcionModel = new GestionarProductoOpcionModel();
    }

    @Override
    public void agregarProductoOpcion(Productoopcion productoOpcion) throws Exception {
        productoOpcionModel.agregarProductoOpcion(productoOpcion);
    }

    @Override
    public void eliminarProductoOpcion(Productoopcion productoOpcion) throws Exception {
        productoOpcionModel.eliminarProductoOpcion(productoOpcion);
    }

    @Override
    public List<Productoopcion> findProductoopcionByProducto(Integer idProducto) {
        return productoOpcionModel.findProductoopcionByProducto(idProducto);
    }
    
}
