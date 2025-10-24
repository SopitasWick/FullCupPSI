/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.modelodatos;

import Entidades.Leche;
import Entidades.TamanoVaso;
import JPA.LecheJpaController;
import JPA.TamanoVasoJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class pruebasVaso {
        public static void main(String[] args) {

System.out.println("=== POBLACIÓN DE TAMAÑOS DE VASO Y TIPOS DE LECHE ===\n");
        
        TamanoVasoJpaController tamanoController = new TamanoVasoJpaController();
        LecheJpaController lecheController = new LecheJpaController();
        
        try {
            // ============================================
            // 1. CREAR TAMAÑOS DE VASO
            // ============================================
            System.out.println("1. Creando tamaños de vaso...");
            
            TamanoVaso chico = new TamanoVaso();
            chico.setNombre("Chico");
            chico.setPrecio(0.0f);
            tamanoController.create(chico);
            System.out.println("  ✓ Tamaño creado: Chico - $0.0");
            
            TamanoVaso mediano = new TamanoVaso();
            mediano.setNombre("Mediano");
            mediano.setPrecio(5.0f);
            tamanoController.create(mediano);
            System.out.println("  ✓ Tamaño creado: Mediano - $5.0");
            
            TamanoVaso grande = new TamanoVaso();
            grande.setNombre("Grande");
            grande.setPrecio(10.0f);
            tamanoController.create(grande);
            System.out.println("  ✓ Tamaño creado: Grande - $10.0");
            
            System.out.println();
            
            // ============================================
            // 2. CREAR TIPOS DE LECHE
            // ============================================
            System.out.println("2. Creando tipos de leche...");
            
            Leche entera = new Leche();
            entera.setNombre("Entera");
            entera.setPrecio(0.0f);
            lecheController.create(entera);
            System.out.println("  ✓ Leche creada: Entera - $0.0");
            
            Leche deslactosada = new Leche();
            deslactosada.setNombre("Deslactosada");
            deslactosada.setPrecio(5.0f);
            lecheController.create(deslactosada);
            System.out.println("  ✓ Leche creada: Deslactosada - $5.0");
            
            Leche almendra = new Leche();
            almendra.setNombre("Almendra");
            almendra.setPrecio(8.0f);
            lecheController.create(almendra);
            System.out.println("  ✓ Leche creada: Almendra - $8.0");
            
            Leche soya = new Leche();
            soya.setNombre("Soya");
            soya.setPrecio(8.0f);
            lecheController.create(soya);
            System.out.println("  ✓ Leche creada: Soya - $8.0");
            
            Leche coco = new Leche();
            coco.setNombre("Coco");
            coco.setPrecio(10.0f);
            lecheController.create(coco);
            System.out.println("  ✓ Leche creada: Coco - $10.0");
            
            Leche avena = new Leche();
            avena.setNombre("Avena");
            avena.setPrecio(8.0f);
            lecheController.create(avena);
            System.out.println("  ✓ Leche creada: Avena - $8.0");
            
            System.out.println();
            
            // ============================================
            // 3. LISTAR TODOS LOS TAMAÑOS
            // ============================================
            System.out.println("3. Lista de tamaños de vaso creados:");
            List<TamanoVaso> tamanos = tamanoController.findTamanoVasoEntities();
            System.out.println("Total: " + tamanos.size());
            for (TamanoVaso t : tamanos) {
                System.out.println("  - ID: " + t.getIdTamanoVaso() + 
                                 " | Nombre: " + t.getNombre() + 
                                 " | Precio: $" + t.getPrecio());
            }
            System.out.println();
            
            // ============================================
            // 4. LISTAR TODOS LOS TIPOS DE LECHE
            // ============================================
            System.out.println("4. Lista de tipos de leche creados:");
            List<Leche> leches = lecheController.findLecheEntities();
            System.out.println("Total: " + leches.size());
            for (Leche l : leches) {
                System.out.println("  - ID: " + l.getIdLeche() + 
                                 " | Tipo: " + l.getNombre() + 
                                 " | Precio: $" + l.getPrecio());
            }
            System.out.println();
            
            // ============================================
            // 5. BUSCAR POR ID
            // ============================================
            System.out.println("5. Búsqueda por ID:");
            TamanoVaso tamanoEncontrado = tamanoController.findTamanoVaso(1);
            if (tamanoEncontrado != null) {
                System.out.println("  ✓ Tamaño con ID 1: " + tamanoEncontrado.getNombre());
            }
            
            Leche lecheEncontrada = lecheController.findLeche(1);
            if (lecheEncontrada != null) {
                System.out.println("  ✓ Leche con ID 1: " + lecheEncontrada.getNombre());
            }
            System.out.println();
            
            // ============================================
            // 6. CONTADORES
            // ============================================
            System.out.println("6. Resumen:");
            System.out.println("  Total de tamaños en BD: " + tamanoController.getTamanoVasoCount());
            System.out.println("  Total de tipos de leche en BD: " + lecheController.getLecheCount());
            
        } catch (Exception e) {
            System.err.println("\nERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
    }

}
