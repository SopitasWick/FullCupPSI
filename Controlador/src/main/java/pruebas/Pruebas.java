/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Controlador.ComandaControlador;
import Entidades.Comanda;
import Entidades.Usuario;
import Interfaces.IComandaControlador;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class Pruebas {
    public static void main(String[] args) {
              IComandaControlador negocio = new ComandaControlador();
        
//        System.out.println("=== CREAR COMANDA ===");
//        Comanda comanda = new Comanda();
//        comanda.setTotalComanda(150.50f);
//        comanda.setEstadoComanda("Abierta");
//        
//        try {
//            negocio.guardarComanda(comanda);
//            System.out.println("✓ Comanda guardada exitosamente!");
//            System.out.println("  ID: " + comanda.getIdComanda());
//            System.out.println("  Estado: " + comanda.getEstadoComanda());
//            System.out.println("  Total: $" + comanda.getTotalComanda());
//            System.out.println("  Fecha: " + comanda.getFechaHoracomanda());
//        } catch (Exception e) {
//            System.err.println("✗ Error al guardar: " + e.getMessage());
//            e.printStackTrace();
//            return;
//        }
        
//       System.out.println("=== EDITAR COMANDA ID 2 ===");
//        
//        try {
//            Comanda comanda2 = negocio.obtenerComanda(2);
//            
//            if (comanda2 != null) {
//                System.out.println("Comanda encontrada:");
//                System.out.println("  ID: " + comanda2.getIdComanda());
//                System.out.println("  Estado antes: " + comanda2.getEstadoComanda());
//                System.out.println("  Total antes: " + comanda2.getTotalComanda());
//                
//                 Date fechaHoracomanda = new Date(); 
//                comanda2.setFechaHoracomanda(fechaHoracomanda);
//                comanda2.setEstadoComanda("Abierta");
//                comanda2.setTotalComanda(900.00f);
//                
//                negocio.actualizarComanda(comanda2);
//                System.out.println("\n Comanda ID 2 actualizada exitosamente!");
//                
//                Comanda comandaActualizada = negocio.obtenerComanda(2);
//                System.out.println("  ID: " + comandaActualizada.getIdComanda());
//                System.out.println("  Estado después: " + comandaActualizada.getEstadoComanda());
//                System.out.println("  Total después: $" + comandaActualizada.getTotalComanda());
//                
//            } else {
//                System.err.println("No se encontró la comanda con ID 2");
//            }
//            
//        } catch (Exception e) {
//            System.err.println("Error al editar: " + e.getMessage());
//            e.printStackTrace();
//        }
//        
//        System.out.println("\n=== TODAS LAS COMANDAS ===");
//        negocio.obtenerTodasLasComandas().forEach(c -> {
//            System.out.println("ID: " + c.getIdComanda() + 
//                             " | Estado: " + c.getEstadoComanda() + 
//                             " | Total: $" + c.getTotalComanda());
//        });
//    }


 System.out.println("=== ELIMINAR COMANDA ID 4 ===");
        
        try {
            // Paso 1: Verificar que existe antes de eliminar
            Comanda comanda3 = negocio.obtenerComanda(5);
            
            if (comanda3 != null) {
                System.out.println("Comanda encontrada:");
                System.out.println("  ID: " + comanda3.getIdComanda());
                System.out.println("  Estado: " + comanda3.getEstadoComanda());
                System.out.println("  Total: $" + comanda3.getTotalComanda());
                System.out.println("  Fecha: " + comanda3.getFechaHoracomanda());
                
                // Paso 2: Eliminar la comanda
                negocio.eliminarComanda(5);
                System.out.println("\n✓ Comanda ID 3 eliminada exitosamente!");
                
                // Paso 3: Verificar que ya no existe
                Comanda comandaEliminada = negocio.obtenerComanda(3);
                if (comandaEliminada == null) {
                    System.out.println("✓ Confirmado: La comanda ID 3 ya no existe");
                } else {
                    System.out.println("⚠ La comanda aún existe (esto no debería pasar)");
                }
                
            } else {
                System.err.println("✗ No se encontró la comanda con ID 3");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Error al eliminar: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Mostrar todas las comandas restantes
        System.out.println("\n=== TODAS LAS COMANDAS (después de eliminar) ===");
        List<Comanda> comandas = negocio.obtenerTodasLasComandas();
        System.out.println("Total de comandas: " + comandas.size());
        comandas.forEach(c -> {
            System.out.println("ID: " + c.getIdComanda() + 
                             " | Estado: " + c.getEstadoComanda() + 
                             " | Total: $" + c.getTotalComanda());
        });
        
        // Probar casos de error
        System.out.println("\n=== PRUEBAS DE VALIDACIÓN ===");
        
        // Intentar eliminar un ID que no existe
        try {
            negocio.eliminarComanda(999);
            System.out.println("⚠ No lanzó error al eliminar ID inexistente");
        } catch (Exception e) {
            System.out.println("✓ Validación correcta: " + e.getMessage());
        }
        
        // Intentar eliminar con ID nulo
        try {
            negocio.eliminarComanda(null);
        } catch (Exception e) {
            System.out.println("✓ Validación correcta: " + e.getMessage());
        }
        
        // Intentar eliminar con ID inválido
        try {
            negocio.eliminarComanda(0);
        } catch (Exception e) {
            System.out.println("✓ Validación correcta: " + e.getMessage());
        }
    


    }

}
    
