/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilerias;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Sergio Arturo
 */
public class NumberDecimalDocumentFilter extends DocumentFilter{
    
    // Patrón que acepta números enteros o decimales (con punto decimal opcional)
    // ^\\d* es cero o más dígitos al inicio
    // \\.? acepta cero o un punto
    // \\d*$ acepta cero o más dígitos hasta el final
    private final Pattern pattern = Pattern.compile("^\\d*\\.?\\d*$");

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        // Llama a replace, ya que insertString es solo un caso especial de replace
        replace(fb, offset, 0, string, attr);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        // 1. Obtener el texto resultante después de la operación
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String resultingText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
        
        // Manejar el caso de texto vacío (que siempre es válido)
        if (resultingText.isEmpty()) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }

        // 2. Validar con la expresión regular
        Matcher matcher = pattern.matcher(resultingText);

        if (matcher.matches()) {
            // Si el texto resultante es válido (solo números y un solo punto)
            super.replace(fb, offset, length, text, attrs);
        } else {
            // Si el texto no es válido, ignora la operación (no llama a super.replace)
            // Opcionalmente, podrías emitir un sonido de alerta o mostrar un mensaje
        }
    }
}
