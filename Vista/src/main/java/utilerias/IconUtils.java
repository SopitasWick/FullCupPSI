/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Sergio Arturo
 */
public class IconUtils {
    
    
    private static Image iconImage;

    // Cargar el icono solo una vez
    static {
        try {
            URL url = IconUtils.class.getResource("/iconos/cafeIcon.png");
            if (url != null) {
                iconImage = new ImageIcon(url).getImage();
            } else {
                System.err.println("No se encontró el recurso del icono");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para aplicar icono a cualquier JFrame
    public static void applyIcon(JFrame frame) {
        if (iconImage != null) {
            frame.setIconImage(iconImage);
        }
    }
}
    
