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

    public static void main(String[] args) throws Exception {
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
            System.out.println("2. Eliminar Comanda");
            System.out.println("3. Editar Comanda");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("→ Agregando comanda...");
                    comanda.setIdComanda(jpaComanda.getComandaCount() + 1);
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
                                            subTotal = subTotal + 50;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
                                            detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad productos
                                            break;
                                        case 2:
                                            System.out.println("→ Agregando Cafe americano de 12oz...");
                                            producto.setIdProducto(2);// Producto que se necesita
                                            listaProductos.add(producto);//Agregarlo a la lista productos
                                            subTotal = subTotal + 50;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
                                            detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad 
                                            break;
                                        case 3:
                                            System.out.println("→ Agregando Muffin...");
                                            producto.setIdProducto(3);// Producto que se necesita
                                            listaProductos.add(producto);//Agregarlo a la lista productos
                                            subTotal = subTotal + 25;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
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
                                            subTotal = subTotal + 15;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
                                            detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad 
                                            break;
                                        case 2:
                                            System.out.println("→ Agregando extra crema batida...");
                                            producto.setIdProducto(4);// Insumo que se necesita
                                            listaProductos.add(producto);//Agregarlo a la lista productos
                                            subTotal = subTotal + 12;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
                                            detalleComanda.setCaintidaddetalleComanda(cantProductos);//Actualizar cantidad
                                            break;
                                        case 3:
                                            System.out.println("→ Agregando shot extra jarabe vainilla...");
                                            producto.setIdProducto(5);// Insumo que se necesita
                                            listaProductos.add(producto);//Agregarlo a la lista productos
                                            subTotal = subTotal + 16;//Actualizar valor sub total
                                            detalleComanda.setSubTotaldetalleComanda((float) subTotal);//actualizar subtotal
                                            cantProductos = cantProductos + 1;//Actualizar valor cantidad productos
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
                                    detalleComanda.setIdDetalleComanda(jpaDetComanda.getDetallecomandaCount() + 1);
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
                case 2: // ELIMINAR COMANDA
                    ComandaJpaController comJpa = new ComandaJpaController();

                    // Listar solo comandas NO eliminadas
                    List<Comanda> comandasActivas = comJpa.findComandaEntities()
                            .stream()
                            .filter(c -> !"Eliminado".equalsIgnoreCase(c.getEstadoComanda()))
                            .toList();

                    if (comandasActivas.isEmpty()) {
                        System.out.println("\nNo hay comandas activas para eliminar.");
                        break;
                    }

                    System.out.println("\n--- COMANDAS DISPONIBLES ---");
                    for (Comanda c : comandasActivas) {
                        System.out.println("ID: " + c.getIdComanda()
                                + " | Fecha: " + c.getFechaHoracomanda()
                                + " | Estado: " + c.getEstadoComanda()
                                + " | Total: " + c.getTotalComanda());
                    }

                    System.out.print("\nIngrese el ID de la comanda a eliminar: ");
                    int idEliminar = entrada.nextInt();

                    Comanda cEliminar = comJpa.findComanda(idEliminar);

                    if (cEliminar == null) {
                        System.out.println("No existe una comanda con ese ID.");
                        break;
                    }

                    if ("Eliminado".equalsIgnoreCase(cEliminar.getEstadoComanda())) {
                        System.out.println("Esa comanda ya está eliminada.");
                        break;
                    }

                    // Cambiar estado y guardar
                    cEliminar.setEstadoComanda("Eliminado");
                    try {
                        comJpa.edit(cEliminar);
                        System.out.println("\nComanda eliminada correctamente (estado = Eliminado).");
                    } catch (Exception e) {
                        System.out.println("Error al eliminar comanda: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("Saliendo del sistema... 👋");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida, intenta de nuevo.");

                    break;

                case 3: // EDITAR COMANDA
                    ComandaJpaController comJpaEditar = new ComandaJpaController();
                    DetallecomandaJpaController detJpaEditar = new DetallecomandaJpaController();
                    ProductoJpaController prodJpaEditar = new ProductoJpaController();

                    // Mostrar comandas activas
                    List<Comanda> comandasEditar = comJpaEditar.findComandaEntities()
                            .stream()
                            .filter(c -> !"Eliminado".equalsIgnoreCase(c.getEstadoComanda()))
                            .toList();

                    if (comandasEditar.isEmpty()) {
                        System.out.println("\nNo hay comandas disponibles para editar.");
                        break;
                    }

                    System.out.println("\n--- COMANDAS DISPONIBLES PARA EDITAR ---");
                    for (Comanda c : comandasEditar) {
                        System.out.println("ID: " + c.getIdComanda()
                                + " | Fecha: " + c.getFechaHoracomanda()
                                + " | Estado: " + c.getEstadoComanda()
                                + " | Total: " + c.getTotalComanda());
                    }

                    System.out.print("\nIngrese el ID de la comanda que desea editar: ");
                    int idEditar = entrada.nextInt();

                    Comanda comandaEditar = comJpaEditar.findComanda(idEditar);

                    if (comandaEditar == null) {
                        System.out.println("No existe una comanda con ese ID.");
                        break;
                    }

                    // Mostrar detalles actuales
                    List<Detallecomanda> detallesActuales = detJpaEditar.findDetallecomandaEntities()
                            .stream()
                            .filter(d -> d.getIdComanda().getIdComanda() == idEditar)
                            .toList();

                    System.out.println("\n--- DETALLES ACTUALES DE LA COMANDA ---");
                    for (Detallecomanda d : detallesActuales) {
                        Producto p = d.getIdProducto();
                        System.out.println("ID Detalle: " + d.getIdDetalleComanda()
                                + " | Producto: " + p.getNombreProducto()
                                + " | Subtotal: " + d.getSubTotaldetalleComanda()
                                + " | Cantidad: " + d.getCaintidaddetalleComanda());
                    }

                    System.out.println("\n¿Qué desea hacer?");
                    System.out.println("1. Agregar producto nuevo");
                    System.out.println("2. Eliminar producto de la comanda");
                    System.out.println("3. Cambiar estado (Abierta / Cerrada)");
                    System.out.println("0. Cancelar edición");
                    System.out.print("Seleccione una opción: ");
                    int opEditar = entrada.nextInt();

                    switch (opEditar) {
                        case 1:
                            // Agregar nuevo producto
                            System.out.println("\n--- PRODUCTOS DISPONIBLES ---");
                            System.out.println("1. Coca cola 600");
                            System.out.println("2. Café americano 12oz");
                            System.out.println("3. Muffin");
                            System.out.print("Seleccione un producto: ");
                            int prodSel = entrada.nextInt();

                            Producto nuevoProd = new Producto();
                            float precio = 0;
                            switch (prodSel) {
                                case 1:
                                    nuevoProd = prodJpaEditar.findProducto(1);
                                    precio = 50;
                                    break;
                                case 2:
                                    nuevoProd = prodJpaEditar.findProducto(2);
                                    precio = 50;
                                    break;
                                case 3:
                                    nuevoProd = prodJpaEditar.findProducto(3);
                                    precio = 25;
                                    break;
                                default:
                                    System.out.println("Opción no válida.");
                                    break;
                            }

                            if (nuevoProd != null) {
                                Detallecomanda nuevoDetalle = new Detallecomanda();
                                nuevoDetalle.setIdDetalleComanda(detJpaEditar.getDetallecomandaCount() + 1);
                                nuevoDetalle.setIdComanda(comandaEditar);
                                nuevoDetalle.setIdProducto(nuevoProd);
                                nuevoDetalle.setSubTotaldetalleComanda(precio);
                                nuevoDetalle.setCaintidaddetalleComanda(1);
                                detJpaEditar.create(nuevoDetalle);

                                // Actualizar total
                                comandaEditar.setTotalComanda(comandaEditar.getTotalComanda() + precio);
                                comJpaEditar.edit(comandaEditar);

                                System.out.println("Producto agregado correctamente.");
                            }
                            break;

                        case 2:
                            // Eliminar producto específico
                            System.out.print("Ingrese el ID del detalle a eliminar: ");
                            int idDetalleEliminar = entrada.nextInt();

                            Detallecomanda detalleEliminar = detJpaEditar.findDetallecomanda(idDetalleEliminar);
                            if (detalleEliminar == null) {
                                System.out.println("No existe un detalle con ese ID.");
                                break;
                            }

                            float subtotalEliminado = detalleEliminar.getSubTotaldetalleComanda();
                            try {
                                detJpaEditar.destroy(idDetalleEliminar);
                                comandaEditar.setTotalComanda(comandaEditar.getTotalComanda() - subtotalEliminado);
                                comJpaEditar.edit(comandaEditar);
                                System.out.println("Producto eliminado correctamente.");
                            } catch (NonexistentEntityException ex) {
                                Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;

                        case 3:
                            System.out.println("Estado actual: " + comandaEditar.getEstadoComanda());
                            System.out.print("Nuevo estado (Abierta/Cerrada): ");
                            String nuevoEstado = entrada.next();
                            comandaEditar.setEstadoComanda(nuevoEstado);
                            try {
                                comJpaEditar.edit(comandaEditar);
                                System.out.println("Estado actualizado correctamente.");
                            } catch (Exception ex) {
                                Logger.getLogger(ModeloDatos.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            break;

                        case 0:
                            System.out.println("Cancelando edición...");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                    break;

            }

            System.out.println(); // Salto de línea
            break;

        } while (opcion != 0);

    }
}
