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
        if (pageIndex > 0) return NO_SUCH_PAGE;

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 20;
        g.drawString("Full Cup", 80, y); y += 15;
        g.drawString("Comanda #" + comanda.getIdComanda(), 10, y); y += 15;
        g.drawString("Fecha: " + sdf.format(comanda.getFechaHoracomanda()), 10, y); y += 15;
        g.drawString("--------------------------------", 10, y); y += 15;

        for (Detallecomanda d : comanda.getDetallecomandaList()) {
            String producto = d.getIdProducto().getNombreProducto();
            String cantidad = "x" + d.getCaintidaddetalleComanda();
            String subtotal = String.format("$%.2f", d.getSubTotaldetalleComanda());
            g.drawString(producto + " " + cantidad + "   " + subtotal, 10, y);
            y += 15;
        }

        g.drawString("--------------------------------", 10, y); y += 15;
        g.drawString("TOTAL: $" + comanda.getTotalComanda(), 10, y);
        y += 30;
        g.drawString("¡Gracias por su compra!", 40, y);

        return PAGE_EXISTS;
    }

    public static void imprimirComanda(Comanda comanda) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(new TicketPrinter(comanda));
            job.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }
}
