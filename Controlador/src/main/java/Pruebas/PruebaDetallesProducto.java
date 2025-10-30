/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import Entidades.Valoropcion;
import Facades.IFachadaProductoControlador;
import Implementaciones.GestionarProductoControlador;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class PruebaDetallesProducto {
    public static void main(String[] args){
        IFachadaProductoControlador fp = new GestionarProductoControlador();
        try {
            System.out.println("Obteniendo los detalles del cafe americano");
            List<Valoropcion> listaPrueba = fp.obtenerDetallesPorProducto(1);
            for (Valoropcion valor : listaPrueba) {
            System.out.println("Nombre detalle: " + valor.getNombreValor());
           }
            System.out.println("Obteniendo los detalles del cafe capuchino");
            List<Valoropcion> listaPrueba2 = fp.obtenerDetallesPorProducto(2);
            for (Valoropcion valor : listaPrueba2) {
            System.out.println("Nombre detalle: " + valor.getNombreValor());
           }
        } catch (Exception ex) {
            Logger.getLogger(PruebaDetallesProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("");
    }
}
