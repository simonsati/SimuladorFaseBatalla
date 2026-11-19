package co.edu.uao.yugioh;

import co.edu.uao.yugioh.controlador.ControladorDuelo;
import co.edu.uao.yugioh.modelo.ArchivoHistorial;
import co.edu.uao.yugioh.modelo.HistorialCombate;
import co.edu.uao.yugioh.vista.VentanaDuelo;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal del simulador de la Fase de Batalla de Yu-Gi-Oh!.
 * Crea el modelo, la vista y el controlador (MVC) y muestra la ventana.
 *
 * @author simonsati
 */
public class Main {

    public static void main(String[] args) {
        HistorialCombate historial = new ArchivoHistorial();
        configurarApariencia();
        java.awt.EventQueue.invokeLater(() -> {
            VentanaDuelo vista = new VentanaDuelo();
            ControladorDuelo controlador = new ControladorDuelo(vista, historial);
            controlador.iniciar();
        });
    }

    /**
     * Aplica el tema oscuro FlatLaf. Si la librería no está disponible se usa
     * Nimbus, el look and feel por defecto de los formularios de NetBeans.
     */
    private static void configurarApariencia() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
            Color dorado = new Color(212, 175, 55);
            UIManager.put("TabbedPane.underlineColor", dorado);
            UIManager.put("TabbedPane.inactiveUnderlineColor", dorado);
            UIManager.put("Component.focusColor", dorado);
            UIManager.put("TabbedPane.tabHeight", 38);
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 10);
            UIManager.put("ProgressBar.arc", 999);
            UIManager.put("TableHeader.background", new Color(42, 48, 88));
            UIManager.put("TableHeader.foreground", new Color(240, 214, 130));
            UIManager.put("TableHeader.height", 30);
            UIManager.put("Table.alternateRowColor", new Color(36, 41, 76));
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                    } catch (ReflectiveOperationException | UnsupportedLookAndFeelException otra) {
                        System.err.println("No se pudo aplicar el look and feel: " + otra.getMessage());
                    }
                    break;
                }
            }
        }
    }
}
