/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Comanda;
import Entidades.Producto;
import java.util.List;

/**
 *
 * @author usuario
 */
public interface IGestionarComanda {
 public List<Producto> InicializarComanda(); 
 public Producto AgregarProductoComanda(Producto producto);
 public String EliminarProductoComanda(Producto producto);
 public String FinalizarComanda(Comanda comanda);
 public String CancelarComanda(Comanda comanda); 
}
