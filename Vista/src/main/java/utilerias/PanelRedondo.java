/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Sergio Arturo
 */
public class PanelRedondo extends JPanel{
    
    private int radius = 25;
    
    // Constructor, otros métodos...

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
    
        Graphics2D g2 = (Graphics2D) g.create();

        // 1. Activar suavizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // El grosor del borde es 1. Necesitamos un margen de 1 píxel.
        int margin = 1; 
        int width = getWidth();
        int height = getHeight();

        // 2. Dibujar el fondo redondeado (rellenado)
        g2.setColor(getBackground()); 
        // Dibujamos el fondo ligeramente más pequeño para dejar espacio al borde
        g2.fillRoundRect(
            margin, margin,          // X e Y iniciales
            width - margin * 2,      // Ancho ajustado
            height - margin * 2,     // Alto ajustado
            radius, radius
        );

        // 3. Dibujar el borde redondeado (contorno)
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1)); 
        // Dibujamos el contorno justo donde termina el fondo relleno
        g2.drawRoundRect(
            margin, margin,          // X e Y iniciales
            width - margin * 2,      // Ancho ajustado
            height - margin * 2,     // Alto ajustado
            radius, radius
        ); 

        g2.dispose();
    }

    public void setRadius(int r) {
        this.radius = r;
        repaint();
    }
}

