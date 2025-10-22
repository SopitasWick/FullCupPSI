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

/**
 *
 * @author Orlando Leyva
 */
public class Pruebas {
    public static void main(String[] args) {
         IComandaControlador negocio = new ComandaControlador();
        
       Comanda comanda = new Comanda();
        
        comanda.setTotalComanda(150.50f);
        comanda.setEstadoComanda("Abierta");
        
        try {
            negocio.guardarComanda(comanda);
            System.out.println("✓ Comanda guardada exitosamente!");
            System.out.println("  ID: " + comanda.getIdComanda());
            System.out.println("  Estado: " + comanda.getEstadoComanda());
            System.out.println("  Total: $" + comanda.getTotalComanda());
            System.out.println("  Fecha: " + comanda.getFechaHoracomanda());
        } catch (Exception e) {
            System.err.println("✗ Error al guardar: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Todas las comandas ===");
        negocio.obtenerTodasLasComandas().forEach(c -> {
            System.out.println("ID: " + c.getIdComanda() + 
                             " | Estado: " + c.getEstadoComanda() + 
                             " | Total: $" + c.getTotalComanda());
        });
    }
}
    
