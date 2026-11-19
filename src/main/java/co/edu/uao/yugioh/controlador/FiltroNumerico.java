package co.edu.uao.yugioh.controlador;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Filtro que solo deja escribir dígitos (0-9) en un campo de texto y limita
 * la cantidad de cifras. Funciona tanto al teclear como al pegar texto.
 *
 * @author simonsati
 */
public class FiltroNumerico extends DocumentFilter {

    private final int maximoDigitos;

    public FiltroNumerico(int maximoDigitos) {
        if (maximoDigitos <= 0) {
            throw new IllegalArgumentException("El máximo de dígitos debe ser positivo.");
        }
        this.maximoDigitos = maximoDigitos;
    }

    /**
     * Instala el filtro en un campo de texto de la vista.
     */
    public static void aplicar(JTextField campo, int maximoDigitos) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new FiltroNumerico(maximoDigitos));
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String texto, AttributeSet atributos)
            throws BadLocationException {
        if (esValido(fb, 0, texto)) {
            super.insertString(fb, offset, texto, atributos);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int longitud, String texto, AttributeSet atributos)
            throws BadLocationException {
        if (esValido(fb, longitud, texto)) {
            super.replace(fb, offset, longitud, texto, atributos);
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    private boolean esValido(FilterBypass fb, int caracteresReemplazados, String texto) {
        if (texto == null || texto.isEmpty()) {
            return true;
        }
        for (char c : texto.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        int longitudFinal = fb.getDocument().getLength() - caracteresReemplazados + texto.length();
        return longitudFinal <= maximoDigitos;
    }
}
