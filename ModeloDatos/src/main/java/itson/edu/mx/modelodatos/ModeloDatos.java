/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package itson.edu.mx.modelodatos;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Producto;
import JPA.ComandaJpaController;
import JPA.DetallecomandaJpaController;
import JPA.ProductoJpaController;
import JPA.exceptions.NonexistentEntityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author usuario
 */
public class ModeloDatos {

    public static void main(String[] args) {
        //CONTROLADORES NECESARIOS**********************************************
        //ProductoJpaController jpaProducto = new ProductoJpaController();
        ComandaJpaController jpaComanda = new ComandaJpaController();
        DetallecomandaJpaController jpaDetComanda = new DetallecomandaJpaController();
        //ENTIDADES NECESARIOS**************************************************
        Producto producto = new Producto();
        Comanda comanda = new Comanda();
        Detallecomanda detalleComanda = new Detallecomanda();
        //RECURSOS
         Date fecha = new Date(); // fecha actual
         float subTotal = 0;
         float total = 0;
         int cantProductos = 0;
         List<Producto> listaProductos = new ArrayList<>();
        Scanner entrada = new Scanner(System.in);
        int opcion;
        int opcionSubmenu;
        
        do {
            System.out.println("=================================");
            System.out.println("        MENÚ DE OPCIONES");
            System.out.println("=================================");
            System.out.println("1. Agregar Comanda nueva");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("→ Agregando comanda...");
                    comanda.setIdComanda(jpaComanda.getComandaCount()+1);
                    //detalleComanda.setIdComanda(comanda);
                    comanda.setFechaHoracomanda(fecha);
                    
                    // Aquí podrías llamar a un método registrarProducto();
                    
                    do {
                        System.out.println("\n------ SUBMENU DE AGREGAR COMANDA ------");
                        System.out.println("1. Agregar producto");
                        System.out.println("2. Agregar extra");
                        System.out.println("3. Guardar cambios");
                        System.out.println("0. Regresar al menú principal");
                        System.out.print("Selecciona una opción: ");
                        opcionSubmenu = entrada.nextInt();

                        switch (opcionSubmenu) {
                            case 1:
                                System.out.println("→ Agregando producto...");
                                
                                do {
                        System.out.println("\n------ SUBMENU PRODUCTOS ------");
                        System.out.println("1. Coca cola 600");
                        System.out.println("2. Cafe americano 12oz");
                        System.out.println("3. Muffin");
                        System.out.println("0. Regresar al menú principal");
                        System.out.print("Selecciona una opción: ");
                        opcionSubmenu = entrada.nextInt();

                        switch (opcionSubmenu) {
                            case 1:
                                System.out.println("→ Agregando Coca dola de 600...");
                                producto.setIdProducto(1);// Producto que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+50;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad productos
                                break;
                            case 2:
                                System.out.println("→ Agregando Cafe americano de 12oz...");
                                producto.setIdProducto(2);// Producto que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+50;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad 
                                break;
                            case 3:
                                System.out.println("→ Agregando Muffin...");
                                producto.setIdProducto(3);// Producto que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+25;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad 
                                break;
                            case 0:
                                System.out.println("↩ Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("⚠️ Opción no válida, intenta de nuevo.");
                        }
                        System.out.println();
                    } while (opcionSubmenu != 0);
                                break;
                            case 2:
                                System.out.println("→ Agregando extra...");
                                do {
                        System.out.println("\n------ SUBMENU EXTRAS ------");
                        System.out.println("1. Shot extra cafe");
                        System.out.println("2. Crema batida");
                        System.out.println("3. Jarabe vainilla");
                        System.out.println("0. Regresar al menú principal");
                        System.out.print("Selecciona una opción: ");
                        opcionSubmenu = entrada.nextInt();

                        switch (opcionSubmenu) {
                            case 1:
                                System.out.println("→ Agregando extra shot extra cafe...");
                                producto.setIdProducto(6);// Insumo que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+15;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad 
                                break;
                            case 2:
                                System.out.println("→ Agregando extra crema batida...");
                                producto.setIdProducto(4);// Insumo que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+12;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad
                                break;
                            case 3:
                                System.out.println("→ Agregando shot extra jarabe vainilla...");
                                producto.setIdProducto(5);// Insumo que se necesita
                                listaProductos.add(producto);//Agregarlo a la lista productos
                                subTotal = subTotal+16;//Actualizar valor sub total
                                detalleComanda.setSubTotaldetalleComanda((float)subTotal);//actualizar subtotal
                                cantProductos=cantProductos+1;//Actualizar valor cantidad productos
                                detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad
                                break;
                            case 0:
                                System.out.println("↩ Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("⚠️ Opción no válida, intenta de nuevo.");
                        }
                        System.out.println();
                    } while (opcionSubmenu != 0);
                                break;
                            case 3:
                                System.out.println("Guardando cambios comanda...");
                                comanda.setEstadoComanda("Abierta");
                                comanda.setTotalComanda(subTotal);
                                try {
                                    jpaComanda.create(comanda);
                                } catch (Exception ex) {
                                    Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                //Vaciando lista de productos en detalle listas
                                for (int i = 0; i < listaProductos.size(); i++) {
                                Producto n = listaProductos.get(i); // obtenemos el elemento en la posición i
                                detalleComanda.setIdProducto(n);
                                detalleComanda.setIdDetalleComanda(jpaDetComanda.getDetallecomandaCount()+1);
                                detalleComanda.setIdComanda(comanda);
                            try {
                                jpaDetComanda.create(detalleComanda);
                            } catch (Exception ex) {
                                Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                System.out.println("Posición " + i + ": " + n);
                                }
                            {
                                
                            }
                                break;

                            case 0:
                                System.out.println("↩ Volviendo al menú principal...");
                                break;
                            default:
                                System.out.println("⚠️ Opción no válida, intenta de nuevo.");
                        }
                        System.out.println();
                    } while (opcionSubmenu != 0);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema... 👋");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida, intenta de nuevo.");
            }

            System.out.println(); // Salto de línea
        } while (opcion != 0);

    
    }
}
