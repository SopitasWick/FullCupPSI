/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.edu.mx.modelodatos;

import Entidades.Producto;
import JPA.ProductoJpaController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class ModeloDatos {

    public static void main(String[] args) {
        //Agregar producto
       Producto producto = new Producto(1,"Coca", (float) 50.00);
       ProductoJpaController jpaTest = new ProductoJpaController();
       
        try {
            jpaTest.create(producto);
            System.out.println("Se agregi con exito el Producto");
        } catch (Exception ex) {
            Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
