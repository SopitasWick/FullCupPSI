/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Entidades.TamanoVaso;
import Implementaciones.GestionarTamanoVasosControlador;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebasTamañoVaso {
      public static void main(String[] args) {
        
        GestionarTamanoVasosControlador controlador = new GestionarTamanoVasosControlador();
        
        try {
            System.out.println(" PRUEBA DE GESTIÓN DE TAMAÑOS DE VASOS \n");
            
            System.out.println("1. Agregando nuevos tamaños de vaso...");
            
            TamanoVaso pequeno = new TamanoVaso();
            pequeno.setNombre("Pequeño");
            pequeno.setPrecio(12.1f);
            controlador.agregarTamanoVaso(pequeno);
            System.out.println("    Tamaño Pequeño agregado");
            
            TamanoVaso mediano = new TamanoVaso();
            mediano.setNombre("Mediano");
            mediano.setPrecio(45.0f);
            controlador.agregarTamanoVaso(mediano);
            System.out.println("    Tamaño Mediano agregado");
            
            TamanoVaso grande = new TamanoVaso();
            grande.setNombre("Grande");
            grande.setPrecio(55.0f);
            controlador.agregarTamanoVaso(grande);
            System.out.println("    Tamaño Grande agregado\n");
            
            System.out.println("2. Listando todos los tamaños de vaso:");
            List<TamanoVaso> todos = controlador.obtenerTodosLosTamanosVasos();
            for (TamanoVaso tv : todos) {
                System.out.println("   ID: " + tv.getIdTamanoVaso() + 
                                 " | Nombre: " + tv.getNombre() + 
                                 " | Precio: $" + tv.getPrecio());
            }
            System.out.println();
            
            if (!todos.isEmpty()) {
                Integer idPrueba = todos.get(0).getIdTamanoVaso();
                System.out.println("3. Obteniendo tamaño con ID " + idPrueba + ":");
                TamanoVaso obtenido = controlador.obtenerTamanoVaso(idPrueba);
                System.out.println("   Nombre: " + obtenido.getNombre());
                System.out.println("   Precio: $" + obtenido.getPrecio() + "\n");
                
                System.out.println("4. Editando el tamaño...");
                obtenido.setNombre(obtenido.getNombre() + " (Actualizado)");
                controlador.editarTamanoVaso(obtenido);
                System.out.println("    Tamaño editado correctamente\n");
                
                System.out.println("5. Verificando la edición:");
                TamanoVaso editado = controlador.obtenerTamanoVaso(idPrueba);
                System.out.println("   Nombre: " + editado.getNombre());
                System.out.println("   Nuevo Precio: $" + editado.getPrecio() + "\n");
                
                System.out.println("6. Eliminando el tamaño con ID " + idPrueba + "...");
                controlador.eliminarTamanoVaso(idPrueba);
                System.out.println("    Tamaño eliminado correctamente\n");
                
                System.out.println("7. Listando tamaños después de la eliminación:");
                List<TamanoVaso> despuesEliminar = controlador.obtenerTodosLosTamanosVasos();
                for (TamanoVaso tv : despuesEliminar) {
                    System.out.println("   ID: " + tv.getIdTamanoVaso() + 
                                     " | Nombre: " + tv.getNombre());
                }
            }
            
            System.out.println("\nPRUEBA COMPLETADA EXITOSAMENTE ");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
