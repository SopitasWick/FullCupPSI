/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Impresion;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import java.awt.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;

public class TicketPrinter implements Printable {

    private final Comanda comanda;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public TicketPrinter(Comanda comanda) {
        this.comanda = comanda;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));

        int y = 15;

        // ===== ENCABEZADO =====
        g2d.drawString("====================================", 10, y); y += 15;
        g2d.drawString("        CAFETERIA FULL CUP        ", 10, y); y += 15;
        g2d.drawString("====================================", 10, y); y += 20;
         g2d.drawString(comanda.getDescripcionGeneral(), 10, y); y += 15;
        g2d.drawString("====================================", 10, y); y += 20;
        

        // ===== INFO GENERAL =====
        g2d.drawString("Comanda ID: " + comanda.getIdComanda(), 10, y); y += 15;
        g2d.drawString("Fecha: " + sdf.format(comanda.getFechaHoracomanda()), 10, y); y += 15;
        g2d.drawString("Estado: " + comanda.getEstadoComanda(), 10, y); y += 15;
        g2d.drawString("------------------------------------", 10, y); y += 15;

        // ===== ENCABEZADO PRODUCTOS =====
        g2d.drawString("Producto              Cant   Subtotal", 10, y); y += 15;
        g2d.drawString("------------------------------------", 10, y); y += 15;

        // ===== DETALLES =====
        float total = 0;
        if (comanda.getDetallecomandaList() != null) {
            for (Detallecomanda d : comanda.getDetallecomandaList()) {
                String nombre = (d.getIdProducto() != null) ? d.getIdProducto().getNombreProducto() : "Producto";
                int cantidad = (d.getCaintidaddetalleComanda() != null) ? d.getCaintidaddetalleComanda() : 1;
                float subtotal = (d.getSubTotaldetalleComanda() != null) ? d.getSubTotaldetalleComanda() : 0f;
                //Sumar subtotales para obtener el total
                total+=d.getSubTotaldetalleComanda();

                g2d.drawString(String.format("%-20s %3d %8.2f", nombre, cantidad, subtotal), 10, y);
                y += 15;

                // --- Ajustar la nota del producto ---
                if (d.getNotadetalleComanda() != null && !d.getNotadetalleComanda().isEmpty()) {
                    String nota = d.getNotadetalleComanda();
                    int maxChars = 32;
                    String[] palabras = nota.split(" ");
                    StringBuilder linea = new StringBuilder("Nota: ");
                    for (String palabra : palabras) {
                        if (linea.length() + palabra.length() + 1 > maxChars) {
                            g2d.drawString("   " + linea.toString(), 10, y);
                            y += 15;
                            linea = new StringBuilder("      " + palabra + " ");
                        } else {
                            linea.append(palabra).append(" ");
                        }
                    }
                    if (linea.length() > 0) {
                        g2d.drawString("   " + linea.toString(), 10, y);
                        y += 15;
                    }
                }

                g2d.drawString("------------------------------------", 10, y);
                y += 15;
            } // ← AQUÍ FALTABA ESTA LLAVE para cerrar el for
        }

        // ===== TOTAL =====
        g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
        g2d.drawString(String.format("TOTAL: %30.2f", total), 10, y);
        y += 20;

        // ===== PIE DE TICKET =====
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 9));
        g2d.drawString("Gracias por su compra!", 10, y); y += 12;
        g2d.drawString("Vuelva pronto :)", 10, y); y += 15;
        g2d.drawString("====================================", 10, y);

        return PAGE_EXISTS;
    }
}

