package co.edu.uao.yugioh.controlador;

import co.edu.uao.yugioh.modelo.Duelo;
import co.edu.uao.yugioh.modelo.Jugador;
import co.edu.uao.yugioh.modelo.Monstruo;
import co.edu.uao.yugioh.modelo.MonstruoEfecto;
import co.edu.uao.yugioh.modelo.MonstruoNormal;
import co.edu.uao.yugioh.modelo.Posicion;
import co.edu.uao.yugioh.modelo.ReglaDeBatallaException;
import co.edu.uao.yugioh.modelo.RegistroCombate;
import co.edu.uao.yugioh.modelo.ResultadoAtaque;
import co.edu.uao.yugioh.vista.VentanaDuelo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
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
    private static final int PESTANA_BATALLA = 2;
    private static final String OPCION_ATAQUE_DIRECTO = "Ataque directo al rival";
    private static final String SIN_ATACANTES = "No hay monstruos listos para atacar";

    private final VentanaDuelo vista;
    private Duelo duelo;
    private final List<Monstruo> atacantesDisponibles = new ArrayList<>();
    private final List<Monstruo> objetivosDisponibles = new ArrayList<>();

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
        vista.getBtnAtacar().addActionListener(this);
        vista.getBtnCambiarPosicion().addActionListener(this);
        vista.getBtnTerminarTurno().addActionListener(this);
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
        } else if (fuente == vista.getBtnAtacar()) {
            atacar();
        } else if (fuente == vista.getBtnCambiarPosicion()) {
            cambiarPosicion();
        } else if (fuente == vista.getBtnTerminarTurno()) {
            terminarTurno();
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
        vista.getTxtResultado().setText("Duelo listo. Cuando haya monstruos en el campo, elige un atacante y un objetivo.");
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
    // RF3 - Lógica de combate
    // ------------------------------------------------------------------

    private void atacar() {
        if (duelo == null) {
            return;
        }
        int indiceAtacante = vista.getCmbAtacante().getSelectedIndex();
        if (indiceAtacante < 0 || indiceAtacante >= atacantesDisponibles.size()) {
            vista.mostrarAdvertencia("Sin atacante", duelo.getJugadorEnTurno().getNombre()
                    + " no tiene monstruos en posición de ataque que puedan atacar en este turno.");
            return;
        }
        Monstruo atacante = atacantesDisponibles.get(indiceAtacante);
        Jugador duelistaAtacante = duelo.getJugadorEnTurno();
        Jugador rival = duelo.getRival();
        int lpAtacanteAntes = duelistaAtacante.getPuntosVida();
        int lpRivalAntes = rival.getPuntosVida();
        RegistroCombate registro;
        try {
            if (objetivosDisponibles.isEmpty()) {
                registro = duelo.atacar(atacante);
            } else {
                int indiceObjetivo = Math.max(0, vista.getCmbObjetivo().getSelectedIndex());
                registro = duelo.atacar(atacante, objetivosDisponibles.get(indiceObjetivo));
            }
        } catch (ReglaDeBatallaException ex) {
            vista.mostrarAdvertencia("Ataque no permitido", ex.getMessage());
            return;
        }
        ResultadoAtaque resultado = duelo.getUltimoResultado();
        vista.getTxtResultado().setText("Turno " + registro.getTurno() + " · " + resultado.getTipo() + "\n"
                + resultado.getDescripcion() + "\n\n"
                + "LP de " + duelistaAtacante.getNombre() + ": " + lpAtacanteAntes + " → " + duelistaAtacante.getPuntosVida()
                + "\nLP de " + rival.getNombre() + ": " + lpRivalAntes + " → " + rival.getPuntosVida());
        vista.getTxtResultado().setCaretPosition(0);
        actualizarVista();
        String resumen = registro.getResultado();
        mostrarEstado("Turno " + registro.getTurno() + ": " + resumen + (resumen.endsWith("!") ? "" : "."));
        if (duelo.haTerminado()) {
            Jugador ganador = duelo.getGanador();
            vista.mostrarInformacion("¡Fin del duelo!", ganador.getNombre() + " gana el duelo con "
                    + ganador.getPuntosVida() + " LP restantes.");
        }
    }

    private void cambiarPosicion() {
        if (duelo == null) {
            return;
        }
        Jugador enTurno = duelo.getJugadorEnTurno();
        JTable tabla = enTurno == duelo.getJugador1() ? vista.getTblCampoJugador1() : vista.getTblCampoJugador2();
        int fila = tabla.getSelectedRow();
        if (fila < 0 || fila >= enTurno.getCampo().size()) {
            vista.mostrarAdvertencia("Cambiar posición", "Selecciona en la tabla de " + enTurno.getNombre()
                    + " el monstruo que quieres cambiar de posición.");
            return;
        }
        Monstruo monstruo = enTurno.getCampo().get(fila);
        try {
            duelo.cambiarPosicion(monstruo);
            actualizarVista();
            mostrarEstado(monstruo.getNombre() + " ahora está en posición de " + monstruo.getPosicion() + ".");
        } catch (ReglaDeBatallaException ex) {
            vista.mostrarAdvertencia("Cambiar posición", ex.getMessage());
        }
    }

    private void terminarTurno() {
        if (duelo == null) {
            return;
        }
        try {
            duelo.terminarTurno();
            actualizarVista();
            mostrarEstado("Turno " + duelo.getTurno() + ": ahora ataca " + duelo.getJugadorEnTurno().getNombre() + ".");
        } catch (ReglaDeBatallaException ex) {
            vista.mostrarAdvertencia("Fin de turno", ex.getMessage());
        }
    }

    private void actualizarArena() {
        actualizarJugador(duelo.getJugador1(), vista.getLblNombreJugador1(), vista.getBarLpJugador1(),
                vista.getLblInfoJugador1(), vista.getTblCampoJugador1(), vista.getLblCementerioJugador1());
        actualizarJugador(duelo.getJugador2(), vista.getLblNombreJugador2(), vista.getBarLpJugador2(),
                vista.getLblInfoJugador2(), vista.getTblCampoJugador2(), vista.getLblCementerioJugador2());

        Jugador enTurno = duelo.getJugadorEnTurno();
        Jugador rival = duelo.getRival();
        vista.getLblEnTurno().setText(duelo.haTerminado()
                ? "Ganó " + duelo.getGanador().getNombre()
                : "Ataca " + enTurno.getNombre() + " → " + rival.getNombre());

        atacantesDisponibles.clear();
        atacantesDisponibles.addAll(enTurno.listarMonstruosQuePuedenAtacar());
        List<String> opcionesAtacante = new ArrayList<>();
        for (Monstruo monstruo : atacantesDisponibles) {
            opcionesAtacante.add(monstruo.getNombre() + " (ATQ " + monstruo.getAtaque() + ")");
        }
        if (opcionesAtacante.isEmpty()) {
            opcionesAtacante.add(SIN_ATACANTES);
        }
        vista.getCmbAtacante().setModel(new DefaultComboBoxModel<>(opcionesAtacante.toArray(new String[0])));

        objetivosDisponibles.clear();
        objetivosDisponibles.addAll(rival.getCampo());
        List<String> opcionesObjetivo = new ArrayList<>();
        for (Monstruo monstruo : objetivosDisponibles) {
            opcionesObjetivo.add(monstruo.getNombre() + (monstruo.estaEnAtaque()
                    ? " (ATQ " + monstruo.getAtaque() + ", en ataque)"
                    : " (DEF " + monstruo.getDefensa() + ", en defensa)"));
        }
        if (opcionesObjetivo.isEmpty()) {
            opcionesObjetivo.add(OPCION_ATAQUE_DIRECTO + " (" + rival.getNombre() + ")");
        }
        vista.getCmbObjetivo().setModel(new DefaultComboBoxModel<>(opcionesObjetivo.toArray(new String[0])));

        boolean enCurso = !duelo.haTerminado();
        vista.getBtnAtacar().setEnabled(enCurso && !atacantesDisponibles.isEmpty());
        vista.getBtnCambiarPosicion().setEnabled(enCurso);
        vista.getBtnTerminarTurno().setEnabled(enCurso);
    }

    private void actualizarJugador(Jugador jugador, JLabel lblNombre, JProgressBar barLp, JLabel lblInfo,
            JTable tabla, JLabel lblCementerio) {
        boolean enTurno = jugador == duelo.getJugadorEnTurno() && !duelo.haTerminado();
        lblNombre.setText(jugador.getNombre() + (enTurno ? "  ·  en turno" : ""));
        barLp.setMaximum(jugador.getLpIniciales());
        barLp.setValue(jugador.getPuntosVida());
        barLp.setString(jugador.getPuntosVida() + " / " + jugador.getLpIniciales() + " LP");
        lblInfo.setText("Campo: " + jugador.getCampo().size() + "/" + Jugador.MAX_MONSTRUOS_EN_CAMPO
                + " · Cementerio: " + jugador.getCementerio().size());

        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        for (Monstruo monstruo : jugador.getCampo()) {
            modelo.addRow(new Object[]{monstruo.getNombre(), monstruo.getAtaque(), monstruo.getDefensa(),
                monstruo.getPosicion().getEtiqueta(), estadoDe(monstruo, enTurno)});
        }

        if (jugador.getCementerio().isEmpty()) {
            lblCementerio.setText("Cementerio: vacío");
        } else {
            StringBuilder nombres = new StringBuilder();
            for (Monstruo monstruo : jugador.getCementerio()) {
                if (nombres.length() > 0) {
                    nombres.append(", ");
                }
                nombres.append(monstruo.getNombre());
            }
            lblCementerio.setText("<html>Cementerio: " + nombres + "</html>");
        }
    }

    private static String estadoDe(Monstruo monstruo, boolean duenoEnTurno) {
        if (monstruo.isAtacoEsteTurno()) {
            return "Ya atacó";
        }
        if (!monstruo.estaEnAtaque()) {
            return "En defensa";
        }
        return duenoEnTurno ? "Listo" : "En guardia";
    }

    // ------------------------------------------------------------------
    // Estado general de la ventana
    // ------------------------------------------------------------------

    private void actualizarVista() {
        boolean hayDuelo = duelo != null;
        vista.getTabsPrincipal().setEnabledAt(PESTANA_MONSTRUOS, hayDuelo);
        vista.getTabsPrincipal().setEnabledAt(PESTANA_BATALLA, hayDuelo);
        if (!hayDuelo) {
            vista.getLblTurno().setText("Duelo sin iniciar");
            return;
        }
        if (duelo.haTerminado()) {
            vista.getLblTurno().setText("Duelo terminado · Ganó " + duelo.getGanador().getNombre());
        } else {
            vista.getLblTurno().setText("Turno " + duelo.getTurno() + " · Ataca " + duelo.getJugadorEnTurno().getNombre());
        }
        actualizarTablaMonstruos();
        actualizarArena();
    }

    private void mostrarEstado(String mensaje) {
        vista.getLblEstado().setText(mensaje);
    }
}
