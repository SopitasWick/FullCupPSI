/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Entidades.Categoria;
import Entidades.ExtrasProductos;
import Entidades.Leche;
import Entidades.Producto;
import Entidades.TamanoVaso;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarExtrasControlador;
import Implementaciones.GestionarLecheControlador;
import Implementaciones.GestionarProductoControlador;
import Implementaciones.GestionarTamanoVasosControlador;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebasExtra {
   public static void main(String[] args) {
        
       GestionarExtrasControlador controlador = new GestionarExtrasControlador();
        
        GestionarProductoControlador productoCtrl = new GestionarProductoControlador();
        GestionarCategoriaControlador categoriaCtrl = new GestionarCategoriaControlador();
        GestionarTamanoVasosControlador tamanoVasoCtrl = new GestionarTamanoVasosControlador();
        GestionarLecheControlador lecheCtrl = new GestionarLecheControlador();
        
        try {
            System.out.println(" PRUEBA DE GESTIÓN DE EXTRAS PRODUCTOS \n");
            
            System.out.println("0. Obteniendo entidades para relacionar...");
            
            List<Producto> productos = productoCtrl.obtenerTodosLosProductos();
            Producto producto = !productos.isEmpty() ? productos.get(0) : null;
            
            List<Categoria> categorias = categoriaCtrl.obtenerTodasLasCategorias();
            Categoria categoria = !categorias.isEmpty() ? categorias.get(0) : null;
            
            List<TamanoVaso> tamanos = tamanoVasoCtrl.obtenerTodosLosTamanosVasos();
            TamanoVaso tamanoVaso = !tamanos.isEmpty() ? tamanos.get(0) : null;
            
            List<Leche> leches = lecheCtrl.obtenerTodasLasLeches();
            Leche leche = !leches.isEmpty() ? leches.get(0) : null;
            
            if (producto != null) {
                System.out.println("    Producto encontrado: " + producto.getNombreProducto());
            }
            if (categoria != null) {
                System.out.println("    Categoría encontrada: " + categoria.getNombre());
            }
            if (tamanoVaso != null) {
                System.out.println("    Tamaño de vaso encontrado: " + tamanoVaso.getNombre());
            }
            if (leche != null) {
                System.out.println("    Leche encontrada: " + leche.getNombre());
            }
            System.out.println();
            
            System.out.println("1. Agregando nuevos extras de productos...");
            
            ExtrasProductos extra1 = new ExtrasProductos();
            extra1.setNombreExtra("Shot de Espresso Extra");
            extra1.setPrecioExtra(15.0f);
            extra1.setCantidad(1);
            extra1.setIdProducto(producto);
            extra1.setIdCategoria(categoria);
            controlador.agregarExtrasProductos(extra1);
            System.out.println("    Extra 'Shot de Espresso' agregado");
            
            ExtrasProductos extra2 = new ExtrasProductos();
            extra2.setNombreExtra("Crema Batida");
            extra2.setPrecioExtra(10.0f);
            extra2.setCantidad(1);
            extra2.setIdProducto(producto);
            extra2.setIdCategoria(categoria);
            extra2.setIdTamanoVaso(tamanoVaso);
            controlador.agregarExtrasProductos(extra2);
            System.out.println("    Extra 'Crema Batida' agregado");
            
            ExtrasProductos extra3 = new ExtrasProductos();
            extra3.setNombreExtra("Jarabe de Vainilla");
            extra3.setPrecioExtra(12.0f);
            extra3.setCantidad(2);
            extra3.setIdProducto(producto);
            extra3.setIdCategoria(categoria);
            extra3.setIdLeche(leche);
            controlador.agregarExtrasProductos(extra3);
            System.out.println("    Extra 'Jarabe de Vainilla' agregado\n");
            
            System.out.println("2. Listando todos los extras de productos:");
            List<ExtrasProductos> todos = controlador.obtenerTodosLosExtras();
            for (ExtrasProductos extra : todos) {
                System.out.println("   ID: " + extra.getIdExtraProducto() + 
                                 " | Nombre: " + extra.getNombreExtra() + 
                                 " | Precio: $" + extra.getPrecioExtra() +
                                 " | Cantidad: " + extra.getCantidad());
            }
            System.out.println();
            
            ExtrasProductos paraProbar = null;
            for (ExtrasProductos extra : todos) {
                if (extra.getNombreExtra().equals("Shot de Espresso Extra") || 
                    extra.getNombreExtra().equals("Crema Batida") || 
                    extra.getNombreExtra().equals("Jarabe de Vainilla")) {
                    paraProbar = extra;
                    break;
                }
            }
            
            if (paraProbar != null) {
                Integer idPrueba = paraProbar.getIdExtraProducto();
                
                System.out.println("3. Obteniendo extra con ID " + idPrueba + ":");
                ExtrasProductos obtenido = controlador.obtenerExtras(idPrueba);
                System.out.println("   Nombre: " + obtenido.getNombreExtra());
                System.out.println("   Precio: $" + obtenido.getPrecioExtra());
                System.out.println("   Cantidad: " + obtenido.getCantidad());
                if (obtenido.getIdProducto() != null) {
                    System.out.println("   Producto: " + obtenido.getIdProducto().getNombreProducto());
                }
                if (obtenido.getIdCategoria() != null) {
                    System.out.println("   Categoría: " + obtenido.getIdCategoria().getNombre());
                }
                System.out.println();
                
                System.out.println("4. Editando el extra...");
                obtenido.setPrecioExtra(obtenido.getPrecioExtra() + 5.0f);
                obtenido.setNombreExtra(obtenido.getNombreExtra() + " (Premium)");
                obtenido.setCantidad(obtenido.getCantidad() + 1);
                controlador.editarExtrasProductos(obtenido);
                System.out.println("    Extra editado correctamente\n");
                
                System.out.println("5. Verificando la edición:");
                ExtrasProductos editado = controlador.obtenerExtras(idPrueba);
                System.out.println("   Nombre actualizado: " + editado.getNombreExtra());
                System.out.println("   Precio actualizado: $" + editado.getPrecioExtra());
                System.out.println("   Cantidad actualizada: " + editado.getCantidad() + "\n");
                
                System.out.println("6. Eliminando el extra con ID " + idPrueba + "...");
                controlador.eliminarExtrasProductos(idPrueba);
                System.out.println("    Extra eliminado correctamente\n");
                
                System.out.println("7. Listando extras después de la eliminación:");
                List<ExtrasProductos> despuesEliminar = controlador.obtenerTodosLosExtras();
                for (ExtrasProductos extra : despuesEliminar) {
                    System.out.println("   ID: " + extra.getIdExtraProducto() + 
                                     " | Nombre: " + extra.getNombreExtra() + 
                                     " | Precio: $" + extra.getPrecioExtra());
                }
            }
            
            System.out.println("\n=== PRUEBA COMPLETADA EXITOSAMENTE ===");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    
    
}  
}
