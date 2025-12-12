/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Sergio Arturo
 */
public class HintToTextField {
    
    
    public static void addHint(JTextField textField, String hint) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.repaint();
            }
        });

        textField.setLayout(new BorderLayout());
        JLabel hintLabel = new JLabel(hint);
        hintLabel.setForeground(Color.GRAY);
        hintLabel.setFont(textField.getFont().deriveFont(Font.ITALIC));
        hintLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        // Usa un overlay para que no bloquee la escritura
        textField.add(hintLabel);

        // Actualiza visibilidad dinámicamente
        Timer timer = new Timer(100, e -> {
            hintLabel.setVisible(textField.getText().isEmpty() && !textField.isFocusOwner());
        });
        timer.start();
    }
    
    
    
    public static void addHintContra(JPasswordField field, String hint) {
       final JLabel hintLabel = new JLabel(hint);
       hintLabel.setForeground(Color.GRAY);
       hintLabel.setFont(field.getFont().deriveFont(Font.ITALIC));
       hintLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

       // Usamos overlay en vez de modificar el JPasswordField
       field.setLayout(new BorderLayout());
       field.add(hintLabel);

       Runnable actualizar = () -> {
           boolean vacio = field.getPassword().length == 0;
           boolean foco = field.isFocusOwner();
           hintLabel.setVisible(vacio && !foco);
       };

       // Foco
       field.addFocusListener(new FocusAdapter() {
           @Override public void focusGained(FocusEvent e) { actualizar.run(); }
           @Override public void focusLost(FocusEvent e) { actualizar.run(); }
       });

       // Cambio de texto (cuando escribes)
       field.getDocument().addDocumentListener(new DocumentListener() {
           @Override public void insertUpdate(DocumentEvent e) { actualizar.run(); }
           @Override public void removeUpdate(DocumentEvent e) { actualizar.run(); }
           @Override public void changedUpdate(DocumentEvent e) { actualizar.run(); }
       });

       // Estado inicial
       actualizar.run();
   }


    
}
