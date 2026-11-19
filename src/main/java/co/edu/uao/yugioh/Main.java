package co.edu.uao.yugioh;

import co.edu.uao.yugioh.controlador.ControladorDuelo;
import co.edu.uao.yugioh.modelo.ArchivoHistorial;
import co.edu.uao.yugioh.modelo.CatalogoMonstruos;
import co.edu.uao.yugioh.modelo.HistorialCombate;
import co.edu.uao.yugioh.modelo.Monstruo;
import co.edu.uao.yugioh.modelo.PersistenciaException;
import co.edu.uao.yugioh.modelo.RegistroCombate;
import co.edu.uao.yugioh.modelo.TipoEnfrentamiento;
import co.edu.uao.yugioh.vista.VentanaDuelo;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal del simulador de la Fase de Batalla de Yu-Gi-Oh!.
 * Crea el modelo, la vista y el controlador (MVC) y muestra la ventana.
 * Antes de abrir la ventana imprime en consola dos reportes sobre objetos
 * guardados en estructuras dinámicas (ArrayList, HashMap y EnumMap).
 *
 * @author simonsati
 */
public class Main {

    public static void main(String[] args) {
        HistorialCombate historial = new ArchivoHistorial();
        imprimirReportes(historial);
        configurarApariencia();
        java.awt.EventQueue.invokeLater(() -> {
            VentanaDuelo vista = new VentanaDuelo();
            ControladorDuelo controlador = new ControladorDuelo(vista, historial);
            controlador.iniciar();
        });
    }

    private static void imprimirReportes(HistorialCombate historial) {
        ArrayList<Monstruo> catalogo = new ArrayList<>(CatalogoMonstruos.crearCatalogo());
        List<RegistroCombate> registros = new ArrayList<>();
        try {
            registros = historial.cargar();
        } catch (PersistenciaException ex) {
            System.err.println("No se pudo leer el historial: " + ex.getMessage());
        }
        System.out.println(reporteMonstruosPorAtaque(catalogo));
        System.out.println(reporteDanioPorDuelista(registros));
    }

    /**
     * REPORTE 1: monstruos ordenados de mayor a menor ATQ, el promedio de ATQ
     * y cuántos monstruos hay de cada tipo.
     *
     * @param monstruos lista dinámica de monstruos
     * @return el texto del reporte
     */
    public static String reporteMonstruosPorAtaque(List<Monstruo> monstruos) {
        ArrayList<Monstruo> ordenados = new ArrayList<>(monstruos);
        ordenados.sort(Comparator.comparingInt(Monstruo::getAtaque).reversed());
        Map<String, Integer> cantidadPorTipo = new HashMap<>();
        int sumaAtaque = 0;

        StringBuilder reporte = new StringBuilder();
        reporte.append("==================================================================\n");
        reporte.append(" REPORTE 1 - Monstruos del catálogo ordenados por ATQ\n");
        reporte.append("==================================================================\n");
        reporte.append(String.format(" %-3s %-30s %-7s %6s %6s%n", "#", "Monstruo", "Tipo", "ATQ", "DEF"));
        int posicion = 1;
        for (Monstruo monstruo : ordenados) {
            reporte.append(String.format(" %-3d %-30s %-7s %6d %6d%n", posicion++, monstruo.getNombre(),
                    monstruo.tipoDeCarta(), monstruo.getAtaque(), monstruo.getDefensa()));
            sumaAtaque += monstruo.getAtaque();
            cantidadPorTipo.merge(monstruo.tipoDeCarta(), 1, Integer::sum);
        }
        if (ordenados.isEmpty()) {
            reporte.append(" No hay monstruos para reportar.\n");
        } else {
            reporte.append(String.format(" Promedio de ATQ: %.1f%n", (double) sumaAtaque / ordenados.size()));
            reporte.append(" Monstruo más fuerte: ").append(ordenados.get(0).getNombre()).append('\n');
            for (Map.Entry<String, Integer> entrada : cantidadPorTipo.entrySet()) {
                reporte.append(" Monstruos de tipo ").append(entrada.getKey()).append(": ")
                        .append(entrada.getValue()).append('\n');
            }
        }
        return reporte.toString();
    }

    /**
     * REPORTE 2: daño total recibido por cada duelista y número de ataques
     * por tipo de enfrentamiento, según el historial de combate.
     *
     * @param registros lista dinámica con los registros del historial
     * @return el texto del reporte
     */
    public static String reporteDanioPorDuelista(List<RegistroCombate> registros) {
        Map<String, Integer> danioRecibido = new HashMap<>();
        Map<TipoEnfrentamiento, Integer> ataquesPorTipo = new EnumMap<>(TipoEnfrentamiento.class);
        int danioTotal = 0;
        for (RegistroCombate registro : registros) {
            ataquesPorTipo.merge(registro.getTipo(), 1, Integer::sum);
            if (registro.getDanio() > 0) {
                danioRecibido.merge(registro.getAfectado(), registro.getDanio(), Integer::sum);
                danioTotal += registro.getDanio();
            }
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("==================================================================\n");
        reporte.append(" REPORTE 2 - Daño por duelista según historial_combate.txt\n");
        reporte.append("==================================================================\n");
        if (registros.isEmpty()) {
            reporte.append(" Todavía no hay ataques registrados en el historial.\n");
            return reporte.toString();
        }
        reporte.append(" Ataques registrados: ").append(registros.size()).append('\n');
        reporte.append(" Daño total infligido: ").append(danioTotal).append('\n');
        reporte.append(" Daño recibido por duelista:\n");
        for (Map.Entry<String, Integer> entrada : danioRecibido.entrySet()) {
            reporte.append(String.format("   - %-20s %6d%n", entrada.getKey(), entrada.getValue()));
        }
        reporte.append(" Ataques por tipo de enfrentamiento:\n");
        for (Map.Entry<TipoEnfrentamiento, Integer> entrada : ataquesPorTipo.entrySet()) {
            reporte.append(String.format("   - %-20s %6d%n", entrada.getKey().getEtiqueta(), entrada.getValue()));
        }
        return reporte.toString();
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
