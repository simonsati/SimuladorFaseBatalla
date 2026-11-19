package co.edu.uao.yugioh.controlador;

import co.edu.uao.yugioh.modelo.Duelo;
import co.edu.uao.yugioh.modelo.Jugador;
import co.edu.uao.yugioh.modelo.Monstruo;
import co.edu.uao.yugioh.modelo.MonstruoEfecto;
import co.edu.uao.yugioh.modelo.MonstruoNormal;
import co.edu.uao.yugioh.modelo.Posicion;
import co.edu.uao.yugioh.modelo.ReglaDeBatallaException;
import co.edu.uao.yugioh.vista.VentanaDuelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 * Controlador del patrón MVC. Registra los eventos de la vista, valida lo que
 * escribe el usuario, le pide al modelo que ejecute cada acción y después
 * actualiza los componentes de la ventana. No contiene reglas del juego: esas
 * están en el paquete modelo.
 *
 * @author simonsati
 */
public class ControladorDuelo implements ActionListener {

    private static final int PESTANA_MONSTRUOS = 1;

    private final VentanaDuelo vista;
    private Duelo duelo;

    public ControladorDuelo(VentanaDuelo vista) {
        this.vista = vista;
        registrarEventos();
        configurarCampos();
    }

    /**
     * Muestra la ventana con el estado inicial.
     */
    public void iniciar() {
        actualizarVista();
        vista.setVisible(true);
    }

    private void registrarEventos() {
        vista.getBtnIniciarDuelo().addActionListener(this);
        vista.getBtnInvocar().addActionListener(this);
        vista.getBtnLimpiarMonstruo().addActionListener(this);
    }

    /**
     * Los campos numéricos solo aceptan dígitos (no se pueden escribir letras).
     */
    private void configurarCampos() {
        FiltroNumerico.aplicar(vista.getTxtLpIniciales(), 5);
        FiltroNumerico.aplicar(vista.getTxtAtaque(), 4);
        FiltroNumerico.aplicar(vista.getTxtDefensa(), 4);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object fuente = evento.getSource();
        if (fuente == vista.getBtnIniciarDuelo()) {
            iniciarDuelo();
        } else if (fuente == vista.getBtnInvocar()) {
            invocarMonstruo();
        } else if (fuente == vista.getBtnLimpiarMonstruo()) {
            limpiarFormularioMonstruo();
        }
    }

    // ------------------------------------------------------------------
    // RF1 - Gestión de jugadores
    // ------------------------------------------------------------------

    private void iniciarDuelo() {
        boolean creado = crearDuelo(vista.getTxtJugador1().getText(), vista.getTxtJugador2().getText(),
                vista.getTxtLpIniciales().getText());
        if (creado) {
            vista.getTabsPrincipal().setSelectedIndex(PESTANA_MONSTRUOS);
        }
    }

    /**
     * Valida los datos del formulario y crea el duelo con sus dos jugadores.
     *
     * @return {@code true} si el duelo se creó
     */
    private boolean crearDuelo(String nombre1, String nombre2, String textoLp) {
        nombre1 = nombre1.trim();
        nombre2 = nombre2.trim();
        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            vista.mostrarAdvertencia("Datos incompletos", "Escribe el nombre de los dos duelistas.");
            return false;
        }
        int lpIniciales;
        try {
            lpIniciales = Integer.parseInt(textoLp.trim());
        } catch (NumberFormatException ex) {
            vista.mostrarAdvertencia("LP inválidos", "Los LP iniciales deben ser un número entero entre "
                    + Jugador.LP_MINIMOS + " y " + Jugador.LP_MAXIMOS + ".");
            return false;
        }
        if (duelo != null && !vista.confirmar("Nuevo duelo",
                "Ya hay un duelo en curso. ¿Quieres reiniciarlo?\nSe pierden los monstruos y los LP actuales.")) {
            return false;
        }
        try {
            Jugador jugador1 = new Jugador(nombre1, lpIniciales);
            Jugador jugador2 = new Jugador(nombre2, lpIniciales);
            duelo = new Duelo(jugador1, jugador2);
        } catch (IllegalArgumentException ex) {
            vista.mostrarAdvertencia("Datos inválidos", ex.getMessage());
            return false;
        }
        prepararFormularioMonstruos();
        actualizarVista();
        mostrarEstado("Duelo iniciado: " + nombre1 + " contra " + nombre2 + ", cada uno con " + lpIniciales
                + " LP. Empieza " + nombre1 + ".");
        return true;
    }

    // ------------------------------------------------------------------
    // RF2 - Gestión de cartas
    // ------------------------------------------------------------------

    private void prepararFormularioMonstruos() {
        vista.getCmbDueno().setModel(new DefaultComboBoxModel<>(new String[]{
            duelo.getJugador1().getNombre(), duelo.getJugador2().getNombre()}));
        limpiarFormularioMonstruo();
    }

    private void invocarMonstruo() {
        if (duelo == null) {
            vista.mostrarAdvertencia("Sin duelo", "Primero registra a los duelistas e inicia el duelo.");
            return;
        }
        String nombre = vista.getTxtNombreMonstruo().getText().trim();
        String textoAtaque = vista.getTxtAtaque().getText().trim();
        String textoDefensa = vista.getTxtDefensa().getText().trim();
        if (nombre.isEmpty() || textoAtaque.isEmpty() || textoDefensa.isEmpty()) {
            vista.mostrarAdvertencia("Datos incompletos", "El monstruo necesita nombre, ATQ y DEF.");
            return;
        }
        try {
            int ataque = Integer.parseInt(textoAtaque);
            int defensa = Integer.parseInt(textoDefensa);
            Posicion posicion = Posicion.desdeTexto((String) vista.getCmbPosicion().getSelectedItem());
            String detalle = vista.getTxtDetalle().getText();
            Monstruo monstruo = MonstruoEfecto.TIPO.equals(vista.getCmbTipo().getSelectedItem())
                    ? new MonstruoEfecto(nombre, ataque, defensa, posicion, detalle)
                    : new MonstruoNormal(nombre, ataque, defensa, posicion, detalle);
            Jugador dueno = vista.getCmbDueno().getSelectedIndex() == 1 ? duelo.getJugador2() : duelo.getJugador1();
            duelo.invocar(dueno, monstruo);
            limpiarFormularioMonstruo();
            actualizarVista();
            mostrarEstado(monstruo.getNombre() + " fue invocado en el campo de " + dueno.getNombre()
                    + " en posición de " + posicion + ".");
        } catch (NumberFormatException ex) {
            vista.mostrarAdvertencia("Dato inválido", "ATQ y DEF deben ser números enteros entre "
                    + Monstruo.PUNTOS_MINIMOS + " y " + Monstruo.PUNTOS_MAXIMOS + ".");
        } catch (IllegalArgumentException ex) {
            vista.mostrarAdvertencia("Dato inválido", ex.getMessage());
        } catch (ReglaDeBatallaException ex) {
            vista.mostrarAdvertencia("Regla del juego", ex.getMessage());
        }
    }

    private void limpiarFormularioMonstruo() {
        vista.getTxtNombreMonstruo().setText("");
        vista.getTxtAtaque().setText("");
        vista.getTxtDefensa().setText("");
        vista.getTxtDetalle().setText("");
        vista.getCmbPosicion().setSelectedIndex(0);
        vista.getCmbTipo().setSelectedIndex(0);
        vista.getTxtNombreMonstruo().requestFocusInWindow();
    }

    private void actualizarTablaMonstruos() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTblMonstruos().getModel();
        modelo.setRowCount(0);
        for (Jugador jugador : List.of(duelo.getJugador1(), duelo.getJugador2())) {
            for (Monstruo monstruo : jugador.getCampo()) {
                modelo.addRow(new Object[]{jugador.getNombre(), monstruo.getNombre(), monstruo.tipoDeCarta(),
                    monstruo.getAtaque(), monstruo.getDefensa(), monstruo.getPosicion().getEtiqueta(),
                    monstruo.describir()});
            }
        }
    }

    // ------------------------------------------------------------------
    // Estado general de la ventana
    // ------------------------------------------------------------------

    private void actualizarVista() {
        boolean hayDuelo = duelo != null;
        vista.getTabsPrincipal().setEnabledAt(PESTANA_MONSTRUOS, hayDuelo);
        if (!hayDuelo) {
            vista.getLblTurno().setText("Duelo sin iniciar");
            return;
        }
        vista.getLblTurno().setText("Turno " + duelo.getTurno() + " · Ataca " + duelo.getJugadorEnTurno().getNombre());
        actualizarTablaMonstruos();
    }

    private void mostrarEstado(String mensaje) {
        vista.getLblEstado().setText(mensaje);
    }
}
