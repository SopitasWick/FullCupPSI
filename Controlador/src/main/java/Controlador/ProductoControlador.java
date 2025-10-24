/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Entidades.Producto;
import Interfaces.IProductoControlador;
import JPA.ProductoJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class ProductoControlador implements IProductoControlador{

    
    private final ProductoJpaController productoController;
    
    
    public ProductoControlador() {
        this.productoController = new ProductoJpaController();
    }
    
    
    
    @Override
    public List<Producto> obtenerTodosLosProductos() {
        
        return productoController.findProductoEntities();
        
    }
    
    
    
}
