package co.edu.uao.yugioh.controlador;

import co.edu.uao.yugioh.modelo.Duelo;
import co.edu.uao.yugioh.modelo.Jugador;
import co.edu.uao.yugioh.vista.VentanaDuelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controlador del patrón MVC. Registra los eventos de la vista, valida lo que
 * escribe el usuario, le pide al modelo que ejecute cada acción y después
 * actualiza los componentes de la ventana. No contiene reglas del juego: esas
 * están en el paquete modelo.
 *
 * @author simonsati
 */
public class ControladorDuelo implements ActionListener {

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
    }

    /**
     * Los campos numéricos solo aceptan dígitos (no se pueden escribir letras).
     */
    private void configurarCampos() {
        FiltroNumerico.aplicar(vista.getTxtLpIniciales(), 5);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        Object fuente = evento.getSource();
        if (fuente == vista.getBtnIniciarDuelo()) {
            iniciarDuelo();
        }
    }

    // ------------------------------------------------------------------
    // RF1 - Gestión de jugadores
    // ------------------------------------------------------------------

    private void iniciarDuelo() {
        crearDuelo(vista.getTxtJugador1().getText(), vista.getTxtJugador2().getText(),
                vista.getTxtLpIniciales().getText());
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
        actualizarVista();
        mostrarEstado("Duelo iniciado: " + nombre1 + " contra " + nombre2 + ", cada uno con " + lpIniciales
                + " LP. Empieza " + nombre1 + ".");
        return true;
    }

    // ------------------------------------------------------------------
    // Estado general de la ventana
    // ------------------------------------------------------------------

    private void actualizarVista() {
        boolean hayDuelo = duelo != null;
        if (!hayDuelo) {
            vista.getLblTurno().setText("Duelo sin iniciar");
            return;
        }
        vista.getLblTurno().setText("Turno " + duelo.getTurno() + " · Ataca " + duelo.getJugadorEnTurno().getNombre());
    }

    private void mostrarEstado(String mensaje) {
        vista.getLblEstado().setText(mensaje);
    }
}
