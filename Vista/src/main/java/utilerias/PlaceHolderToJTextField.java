/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sergio Arturo
 */
public class PlaceHolderToJTextField extends JLabel implements FocusListener, DocumentListener {
    
    
    private JTextComponent component;
    private Document document;

    public PlaceHolderToJTextField(String text, JTextComponent component) {
        this.component = component;
        document = component.getDocument();

        setText(text);
        setFont(component.getFont());
        setForeground(Color.GRAY);
        setBorder(BorderFactory.createEmptyBorder(component.getInsets().top, 
                                                  component.getInsets().left + 2, 0, 0));
        setHorizontalAlignment(SwingConstants.LEADING);

        component.addFocusListener(this);
        document.addDocumentListener(this);

        component.setLayout(new BorderLayout());
        component.add(this);
        checkForPrompt();
    }

    private void checkForPrompt() {
        setVisible(component.getText().isEmpty() && !component.hasFocus());
    }

    @Override
    public void focusGained(FocusEvent e) {
        checkForPrompt();
    }

    @Override
    public void focusLost(FocusEvent e) {
        checkForPrompt();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkForPrompt();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {}
}
    
