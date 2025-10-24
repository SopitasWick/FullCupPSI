
package itson.edu.mx.vista;

import Formularios.Comandas;

public class Vista {

   public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread (buena práctica).
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crea una instancia de tu ventana diseñada y la hace visible.
                new Comandas().setVisible(true);
            }
        });
    }
}
