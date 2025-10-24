/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Controlador.ExtraProductosControlador;
import Entidades.ExtrasProductos;
import Entidades.Producto;
import JPA.ProductoJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebaExtras {
     public static void main(String[] args) {
        ExtraProductosControlador extraController = new ExtraProductosControlador();
        ProductoJpaController productoController = new ProductoJpaController();
        
        try {
            System.out.println("=== PRUEBA DE EXTRAPRODUCTOS CONTROLADOR ===\n");
            
            System.out.println("1. Creando un producto de prueba...");
            Producto producto = new Producto(100, "Hamburguesa Especial", 85.50f);
            try {
                productoController.create(producto);
                System.out.println("Producto creado: " + producto.getNombreProducto());
            } catch (Exception e) {
                System.out.println("Producto ya existe: " + e.getMessage());
                producto = productoController.findProducto(100);
            }
            System.out.println();
            
            System.out.println("2. Creando extras para el producto...");
            
            ExtrasProductos extra1 = new ExtrasProductos();
            extra1.setNombreExtra("Queso Extra");
            extra1.setPrecioExtra(15.0f);
            extra1.setCantidad(1);
            extra1.setIdProducto(producto);
            
            ExtrasProductos extra2 = new ExtrasProductos();
            extra2.setNombreExtra("Tocino");
            extra2.setPrecioExtra(20.0f);
            extra2.setCantidad(2);
            extra2.setIdProducto(producto);
            
            ExtrasProductos extra3 = new ExtrasProductos();
            extra3.setNombreExtra("Aguacate");
            extra3.setPrecioExtra(12.0f);
            extra3.setCantidad(1);
            extra3.setIdProducto(producto);
            
            try {
                extraController.create(extra1);
                System.out.println("Extra creado: " + extra1.getNombreExtra() + " - ID: " + extra1.getIdExtraProducto());
            } catch (Exception e) {
                System.out.println("Error al crear extra1: " + e.getMessage());
            }
            
            try {
                extraController.create(extra2);
                System.out.println("Extra creado: " + extra2.getNombreExtra() + " - ID: " + extra2.getIdExtraProducto());
            } catch (Exception e) {
                System.out.println("Error al crear extra2: " + e.getMessage());
            }
            
            try {
                extraController.create(extra3);
                System.out.println("Extra creado: " + extra3.getNombreExtra() + " - ID: " + extra3.getIdExtraProducto());
            } catch (Exception e) {
                System.out.println("Error al crear extra3: " + e.getMessage());
            }
            System.out.println();
            
            System.out.println("3. Listando todos los extras...");
            List<ExtrasProductos> todosLosExtras = extraController.findExtraProductoEntities();
            System.out.println("Total de extras: " + todosLosExtras.size());
            for (ExtrasProductos extra : todosLosExtras) {
                System.out.println("   - ID: " + extra.getIdExtraProducto() + " | " + extra.getNombreExtra() + " | $" + extra.getPrecioExtra() + " | Cant: " + extra.getCantidad());
            }
            System.out.println();
            
            System.out.println("4. Buscando extras del producto ID 100...");
            List<ExtrasProductos> extrasPorProducto = extraController.findExtrasByProducto(100);
            System.out.println("Extras encontrados: " + extrasPorProducto.size());
            for (ExtrasProductos extra : extrasPorProducto) {
                System.out.println("   - " + extra.getNombreExtra() + " ($" + extra.getPrecioExtra() + ")");
            }
            System.out.println();
            
            System.out.println("5. Calculando precio total de extras...");
            float precioTotal = extraController.calcularPrecioTotalExtras(extrasPorProducto);
            System.out.println("Precio total de extras: $" + precioTotal);
            System.out.println();
            
            System.out.println("6. Actualizando un extra...");
            if (!todosLosExtras.isEmpty()) {
                ExtrasProductos extraParaActualizar = todosLosExtras.get(0);
                System.out.println("Extra antes: " + extraParaActualizar.getNombreExtra() + " - $" + extraParaActualizar.getPrecioExtra());
                extraParaActualizar.setPrecioExtra(18.0f);
                extraParaActualizar.setCantidad(3);
                try {
                    extraController.edit(extraParaActualizar);
                    System.out.println("Extra actualizado: " + extraParaActualizar.getNombreExtra() + " - $" + extraParaActualizar.getPrecioExtra() + " - Cant: " + extraParaActualizar.getCantidad());
                } catch (Exception e) {
                    System.out.println("Error al actualizar: " + e.getMessage());
                }
            }
            System.out.println();
            
            System.out.println("7. Verificando disponibilidad...");
            if (!todosLosExtras.isEmpty()) {
                Integer idExtra = todosLosExtras.get(0).getIdExtraProducto();
                boolean disponible = extraController.verificarDisponibilidad(idExtra);
                System.out.println("Extra ID " + idExtra + " disponible: " + disponible);
            }
            System.out.println();
            
            System.out.println("8. Contando total de extras...");
            int totalExtras = extraController.getExtraProductoCount();
            System.out.println("Total de extras en BD: " + totalExtras);
            System.out.println();
            
            System.out.println("=== PRUEBA COMPLETADA ===");
            
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
