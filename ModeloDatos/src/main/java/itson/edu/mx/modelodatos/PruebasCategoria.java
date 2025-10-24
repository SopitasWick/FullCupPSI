/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.modelodatos;

import Entidades.Categoria;
import JPA.CategoriaJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class PruebasCategoria {
     public static void main(String[] args) {
        CategoriaJpaController categoriaController = new CategoriaJpaController();
        
        try {
            System.out.println("=== PRUEBA DE CATEGORIA JPA CONTROLLER ===\n");
            
            System.out.println("1. Creando categorías...");
            
            Categoria categoria1 = new Categoria();
            categoria1.setIdCategoria(1);
            categoria1.setNombre("Hamburguesas");
            
            Categoria categoria2 = new Categoria();
            categoria2.setIdCategoria(2);
            categoria2.setNombre("Bebidas");
            
            Categoria categoria3 = new Categoria();
            categoria3.setIdCategoria(3);
            categoria3.setNombre("Postres");
            
            try {
                categoriaController.create(categoria1);
                System.out.println("Categoría creada: " + categoria1.getNombre());
            } catch (Exception e) {
                System.out.println("Categoría ya existe: " + categoria1.getNombre());
            }
            
            try {
                categoriaController.create(categoria2);
                System.out.println("Categoría creada: " + categoria2.getNombre());
            } catch (Exception e) {
                System.out.println("Categoría ya existe: " + categoria2.getNombre());
            }
            
            try {
                categoriaController.create(categoria3);
                System.out.println("Categoría creada: " + categoria3.getNombre());
            } catch (Exception e) {
                System.out.println("Categoría ya existe: " + categoria3.getNombre());
            }
            System.out.println();
            
            System.out.println("2. Buscando categoría por ID (1)...");
            Categoria categoriaEncontrada = categoriaController.findCategoria(1);
            if (categoriaEncontrada != null) {
                System.out.println("Categoría encontrada:");
                System.out.println("   ID: " + categoriaEncontrada.getIdCategoria());
                System.out.println("   Nombre: " + categoriaEncontrada.getNombre());
            } else {
                System.out.println("Categoría no encontrada");
            }
            System.out.println();
            
            System.out.println("3. Listando todas las categorías...");
            List<Categoria> todasLasCategorias = categoriaController.findCategoriaEntities();
            System.out.println("Total de categorías: " + todasLasCategorias.size());
            for (Categoria cat : todasLasCategorias) {
                System.out.println("   - ID: " + cat.getIdCategoria() + " | " + cat.getNombre());
            }
            System.out.println();
            
            System.out.println("4. Actualizando categoría ID 1...");
            Categoria categoriaActualizar = categoriaController.findCategoria(1);
            if (categoriaActualizar != null) {
                categoriaActualizar.setNombre("Hamburguesas Premium");
                try {
                    categoriaController.edit(categoriaActualizar);
                    System.out.println("Categoría actualizada: " + categoriaActualizar.getNombre());
                } catch (Exception e) {
                    System.out.println("Error al actualizar: " + e.getMessage());
                }
            } else {
                System.out.println("Categoría no encontrada para actualizar");
            }
            System.out.println();
            
            System.out.println("5. Contando categorías...");
            int totalCategorias = categoriaController.getCategoriaCount();
            System.out.println("Total de categorías en BD: " + totalCategorias);
            System.out.println();
            
            System.out.println("6. Buscando categorías con paginación (máximo 2)...");
            List<Categoria> categoriasPaginadas = categoriaController.findCategoriaEntities(2, 0);
            System.out.println("Categorías encontradas (página 1): " + categoriasPaginadas.size());
            for (Categoria cat : categoriasPaginadas) {
                System.out.println("   - " + cat.getNombre());
            }
            System.out.println();
            
            System.out.println("=== PRUEBA COMPLETADA ===");
            
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
}
}
