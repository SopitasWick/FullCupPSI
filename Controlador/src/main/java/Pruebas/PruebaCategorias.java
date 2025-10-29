/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Entidades.Categoria;
import Implementaciones.GestionarCategoriaControlador;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebaCategorias {
 
    
    public static void main(String[] args) {
        
        GestionarCategoriaControlador controlador = new GestionarCategoriaControlador();
        
        try {
            System.out.println(" PRUEBA DE GESTIÓN DE CATEGORÍAS \n");
            
            System.out.println("1. Agregando nuevas categorías...");
            
            Categoria bebidas = new Categoria();
            bebidas.setNombre("Bebidas Calientes");
            controlador.agregarCategoria(bebidas);
            System.out.println("    Categoría Bebidas Calientes agregada");
            
            Categoria postres = new Categoria();
            postres.setNombre("Postres");
            controlador.agregarCategoria(postres);
            System.out.println("    Categoría Postres agregada");
            
            Categoria snacks = new Categoria();
            snacks.setNombre("Snacks");
            controlador.agregarCategoria(snacks);
            System.out.println("    Categoría Snacks agregada\n");
            
            System.out.println("2. Listando todas las categorías:");
            List<Categoria> todas = controlador.obtenerTodasLasCategorias();
            for (Categoria cat : todas) {
                System.out.println("   ID: " + cat.getIdCategoria() + 
                                 " | Nombre: " + cat.getNombre());
            }
            System.out.println();
            
            Categoria paraProbar = null;
            for (Categoria cat : todas) {
                if (cat.getNombre().equals("Bebidas Calientes") || 
                    cat.getNombre().equals("Postres") || 
                    cat.getNombre().equals("Snacks")) {
                    paraProbar = cat;
                    break;
                }
            }
            
            if (paraProbar != null) {
                Integer idPrueba = paraProbar.getIdCategoria();
                
                System.out.println("3. Obteniendo categoría con ID " + idPrueba + ":");
                Categoria obtenida = controlador.obtenerCategoria(idPrueba);
                System.out.println("   Nombre: " + obtenida.getNombre() + "\n");
                
                System.out.println("4. Editando la categoría...");
                obtenida.setNombre(obtenida.getNombre() + " (Actualizada)");
                controlador.editarCategoria(obtenida);
                System.out.println("    Categoría editada correctamente\n");
                
                System.out.println("5. Verificando la edición:");
                Categoria editada = controlador.obtenerCategoria(idPrueba);
                System.out.println("   Nombre actualizado: " + editada.getNombre() + "\n");
                
                System.out.println("6. Eliminando la categoría con ID " + idPrueba + "...");
                controlador.eliminarCategoria(idPrueba);
                System.out.println("    Categoría eliminada correctamente\n");
                
                System.out.println("7. Listando categorías después de la eliminación:");
                List<Categoria> despuesEliminar = controlador.obtenerTodasLasCategorias();
                for (Categoria cat : despuesEliminar) {
                    System.out.println("   ID: " + cat.getIdCategoria() + 
                                     " | Nombre: " + cat.getNombre());
                }
            }
            
            System.out.println("\n PRUEBA COMPLETADA EXITOSAMENTE ");
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    
}
}
