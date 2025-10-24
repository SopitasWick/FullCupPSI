/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.modelodatos;

import Entidades.Categoria;
import Entidades.ExtrasProductos;
import Entidades.Leche;
import Entidades.Producto;
import Entidades.TamanoVaso;
import JPA.CategoriaJpaController;
import JPA.ExtraProductosJpaController;
import JPA.LecheJpaController;
import JPA.ProductoJpaController;
import JPA.TamanoVasoJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebasExtras {
   

    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE ExtraProductosJpaController ===\n");
        
        // Inicializar controladores
        ExtraProductosJpaController extraController = new ExtraProductosJpaController();
        ProductoJpaController productoController = new ProductoJpaController();
        CategoriaJpaController categoriaController = new CategoriaJpaController();
        TamanoVasoJpaController tamanoController = new TamanoVasoJpaController();
        LecheJpaController lecheController = new LecheJpaController();
        
        try {
            // 1. CREAR UN EXTRA PRODUCTO
            System.out.println("1. Creando un nuevo extra producto...");
            
            // Obtener entidades relacionadas (asume que ya existen en la BD)
            Producto producto = productoController.findProducto(1); // Ajusta el ID según tu BD
            Categoria categoria = categoriaController.findCategoria(1);
            TamanoVaso tamanoVaso = tamanoController.findTamanoVaso(1);
            Leche leche = lecheController.findLeche(1);
            
            if (producto == null || categoria == null || tamanoVaso == null || leche == null) {
                System.out.println("ERROR: Asegúrate de tener productos, categorías, tamaños y tipos de leche en la BD");
                return;
            }
            
            ExtrasProductos extra = new ExtrasProductos();
            extra.setNombreExtra("Shot de Espresso");
            extra.setPrecioExtra(15.0f);
            extra.setCantidad(1);
            extra.setIdProducto(producto);
            extra.setIdCategoria(categoria);
            extra.setIdTamanoVaso(tamanoVaso);
            extra.setIdLeche(leche);
            
            extraController.create(extra);
            System.out.println("✓ Extra creado exitosamente: " + extra.getNombreExtra());
            System.out.println("  ID: " + extra.getIdExtraProducto() + "\n");
            
            // 2. BUSCAR EXTRA POR ID
            System.out.println("2. Buscando extra por ID...");
            ExtrasProductos extraEncontrado = extraController.findExtraProducto(extra.getIdExtraProducto());
            if (extraEncontrado != null) {
                System.out.println("✓ Extra encontrado:");
                imprimirExtra(extraEncontrado);
            }
            System.out.println();
            
            // 3. LISTAR TODOS LOS EXTRAS
            System.out.println("3. Listando todos los extras...");
            List<ExtrasProductos> todosLosExtras = extraController.findExtraProductoEntities();
            System.out.println("Total de extras: " + todosLosExtras.size());
            for (ExtrasProductos e : todosLosExtras) {
                System.out.println("  - " + e.getNombreExtra() + " ($" + e.getPrecioExtra() + ")");
            }
            System.out.println();
            
            // 4. BUSCAR EXTRAS POR PRODUCTO
            System.out.println("4. Buscando extras por producto...");
            List<ExtrasProductos> extrasPorProducto = extraController.findExtrasByProducto(producto.getIdProducto());
            System.out.println("Extras del producto '" + producto.getNombreProducto() + "': " + extrasPorProducto.size());
            for (ExtrasProductos e : extrasPorProducto) {
                System.out.println("  - " + e.getNombreExtra());
            }
            System.out.println();
            
            // 5. BUSCAR EXTRAS POR CATEGORÍA
            System.out.println("5. Buscando extras por categoría...");
            List<ExtrasProductos> extrasPorCategoria = extraController.findExtrasByCategoria(categoria.getIdCategoria());
            System.out.println("Extras de la categoría '" + categoria.getNombre()+ "': " + extrasPorCategoria.size());
            for (ExtrasProductos e : extrasPorCategoria) {
                System.out.println("  - " + e.getNombreExtra());
            }
            System.out.println();
            
            // 6. BUSCAR EXTRAS POR TAMAÑO DE VASO
            System.out.println("6. Buscando extras por tamaño de vaso...");
            List<ExtrasProductos> extrasPorTamano = extraController.findExtrasByTamanoVaso(tamanoVaso.getIdTamanoVaso());
            System.out.println("Extras con tamaño '" + tamanoVaso.getNombre()+ "': " + extrasPorTamano.size());
            for (ExtrasProductos e : extrasPorTamano) {
                System.out.println("  - " + e.getNombreExtra());
            }
            System.out.println();
            
            // 7. BUSCAR EXTRAS POR TIPO DE LECHE
            System.out.println("7. Buscando extras por tipo de leche...");
            List<ExtrasProductos> extrasPorLeche = extraController.findExtrasByLeche(leche.getIdLeche());
            System.out.println("Extras con leche '" + leche.getNombre()+ "': " + extrasPorLeche.size());
            for (ExtrasProductos e : extrasPorLeche) {
                System.out.println("  - " + e.getNombreExtra());
            }
            System.out.println();
            
//            // 8. ACTUALIZAR UN EXTRA
//            System.out.println("8. Actualizando extra...");
//            extraEncontrado.setPrecioExtra(20.0f);
//            extraEncontrado.setCantidad(2);
//            extraController.edit(extraEncontrado);
//            System.out.println("✓ Extra actualizado: " + extraEncontrado.getNombreExtra());
//            System.out.println("  Nuevo precio: $" + extraEncontrado.getPrecioExtra());
//            System.out.println("  Nueva cantidad: " + extraEncontrado.getCantidad() + "\n");
//            
//            // 9. CONTAR EXTRAS
//            System.out.println("9. Contando extras...");
//            int totalExtras = extraController.getExtraProductoCount();
//            System.out.println("Total de extras en la base de datos: " + totalExtras + "\n");
            
//            // 10. ELIMINAR EXTRA
//            System.out.println("10. Eliminando extra de prueba...");
//            extraController.destroy(extra.getIdExtraProducto());
//            System.out.println("✓ Extra eliminado exitosamente\n");
//            
//            // Verificar eliminación
//            ExtrasProductos extraEliminado = extraController.findExtraProducto(extra.getIdExtraProducto());
//            if (extraEliminado == null) {
//                System.out.println("✓ Confirmado: El extra ya no existe en la base de datos");
//            }
//            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== FIN DE LAS PRUEBAS ===");
    }
    
    private static void imprimirExtra(ExtrasProductos extra) {
        System.out.println("  ID: " + extra.getIdExtraProducto());
        System.out.println("  Nombre: " + extra.getNombreExtra());
        System.out.println("  Precio: $" + extra.getPrecioExtra());
        System.out.println("  Cantidad: " + extra.getCantidad());
        if (extra.getIdProducto() != null) {
            System.out.println("  Producto: " + extra.getIdProducto().getNombreProducto());
        }
        if (extra.getIdCategoria() != null) {
            System.out.println("  Categoría: " + extra.getIdCategoria().getNombre());
        }
        if (extra.getIdTamanoVaso() != null) {
            System.out.println("  Tamaño: " + extra.getIdTamanoVaso().getNombre());
        }
        if (extra.getIdLeche() != null) {
            System.out.println("  Leche: " + extra.getIdLeche().getNombre());
        }
    }
}


