package co.edu.uao.yugioh.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Duelo entre dos jugadores. Controla de quién es el turno
 * y aplica las reglas de la Fase de Batalla usando {@link CalculadoraDanio}.
 *
 * @author simonsati
 */
public class Duelo {

    private final Jugador jugador1;
    private final Jugador jugador2;
    private final HistorialCombate historial;
    private final List<RegistroCombate> registros = new ArrayList<>();
    private ResultadoAtaque ultimoResultado;
    private Jugador jugadorEnTurno;
    private int turno;
    private Jugador ganador;

    /**
     * Crea el duelo. Empieza atacando el primer jugador.
     *
     * @param historial dónde se guarda el registro de cada ataque
     */
    public Duelo(Jugador jugador1, Jugador jugador2, HistorialCombate historial) {
        this.jugador1 = Objects.requireNonNull(jugador1, "Falta el primer duelista.");
        this.jugador2 = Objects.requireNonNull(jugador2, "Falta el segundo duelista.");
        if (jugador1 == jugador2 || jugador1.getNombre().equalsIgnoreCase(jugador2.getNombre())) {
            throw new IllegalArgumentException("Los dos duelistas deben tener nombres diferentes.");
        }
        this.historial = Objects.requireNonNull(historial, "El historial es obligatorio.");
        this.jugadorEnTurno = jugador1;
        this.turno = 1;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }

    public Jugador getJugadorEnTurno() {
        return jugadorEnTurno;
    }

    /**
     * @return el rival del jugador que tiene el turno
     */
    public Jugador getRival() {
        return getRivalDe(jugadorEnTurno);
    }

    public Jugador getRivalDe(Jugador duelista) {
        if (duelista == jugador1) {
            return jugador2;
        }
        if (duelista == jugador2) {
            return jugador1;
        }
        throw new IllegalArgumentException("Ese duelista no participa en este duelo.");
    }

    public int getTurno() {
        return turno;
    }

    public boolean haTerminado() {
        return ganador != null;
    }

    /**
     * @return el ganador, o {@code null} si el duelo sigue en curso
     */
    public Jugador getGanador() {
        return ganador;
    }

    /**
     * @return los ataques de este duelo (lista de solo lectura)
     */
    public List<RegistroCombate> getRegistros() {
        return Collections.unmodifiableList(registros);
    }

    /**
     * @return el cálculo del último ataque (con la explicación de la regla aplicada),
     *         o {@code null} si todavía no hay ataques
     */
    public ResultadoAtaque getUltimoResultado() {
        return ultimoResultado;
    }

    /**
     * Pasa el turno al rival.
     * Los monstruos del nuevo jugador en turno vuelven a poder atacar.
     */
    public void terminarTurno() throws ReglaDeBatallaException {
        validarDueloEnCurso();
        jugadorEnTurno = getRival();
        turno++;
        jugadorEnTurno.prepararNuevoTurno();
    }

    /**
     * Invoca un monstruo en el campo de uno de los duelistas.
     *
     * @throws ReglaDeBatallaException si el campo está lleno o el monstruo ya está en juego
     */
    public void invocar(Jugador duelista, Monstruo monstruo) throws ReglaDeBatallaException {
        validarDueloEnCurso();
        if (duelista != jugador1 && duelista != jugador2) {
            throw new IllegalArgumentException("Ese duelista no participa en este duelo.");
        }
        Objects.requireNonNull(monstruo, "El monstruo es obligatorio.");
        if (jugador1.controla(monstruo) || jugador2.controla(monstruo)) {
            throw new ReglaDeBatallaException(monstruo.getNombre() + " ya está en el campo.");
        }
        duelista.invocar(monstruo);
    }

    /**
     * Ataque contra un monstruo del rival (Ataque vs Ataque o Ataque vs Defensa).
     *
     * @return el registro del ataque
     * @throws ReglaDeBatallaException si el ataque no está permitido
     * @throws PersistenciaException si el ataque se resolvió pero no se pudo guardar en el historial
     */
    public RegistroCombate atacar(Monstruo atacante, Monstruo objetivo)
            throws ReglaDeBatallaException, PersistenciaException {
        validarAtacante(atacante);
        Objects.requireNonNull(objetivo, "Debes elegir un objetivo.");
        Jugador rival = getRival();
        if (!rival.controla(objetivo)) {
            throw new ReglaDeBatallaException(objetivo.getNombre() + " no está en el campo de " + rival.getNombre() + ".");
        }
        return resolver(atacante, objetivo, CalculadoraDanio.calcular(atacante, objetivo));
    }

    /**
     * Ataque directo: solo se permite si el rival no tiene monstruos en el campo.
     *
     * @return el registro del ataque
     * @throws ReglaDeBatallaException si el ataque no está permitido
     * @throws PersistenciaException si el ataque se resolvió pero no se pudo guardar en el historial
     */
    public RegistroCombate atacar(Monstruo atacante) throws ReglaDeBatallaException, PersistenciaException {
        validarAtacante(atacante);
        Jugador rival = getRival();
        if (rival.tieneMonstruosEnCampo()) {
            throw new ReglaDeBatallaException("No hay ataque directo: " + rival.getNombre()
                    + " todavía tiene monstruos en el campo.");
        }
        return resolver(atacante, null, CalculadoraDanio.calcular(atacante));
    }

    /**
     * Cambia la posición (Ataque/Defensa) de un monstruo del jugador en turno.
     * Solo se puede una vez por turno y no después de atacar.
     */
    public void cambiarPosicion(Monstruo monstruo) throws ReglaDeBatallaException {
        validarDueloEnCurso();
        Objects.requireNonNull(monstruo, "Debes elegir un monstruo.");
        if (!jugadorEnTurno.controla(monstruo)) {
            throw new ReglaDeBatallaException("Solo puedes cambiar la posición de los monstruos de "
                    + jugadorEnTurno.getNombre() + ", que tiene el turno.");
        }
        if (monstruo.isAtacoEsteTurno()) {
            throw new ReglaDeBatallaException(monstruo.getNombre() + " ya atacó en este turno y no puede cambiar de posición.");
        }
        if (monstruo.isCambioPosicionEsteTurno()) {
            throw new ReglaDeBatallaException(monstruo.getNombre() + " ya cambió de posición en este turno.");
        }
        monstruo.cambiarPosicion();
    }

    private void validarAtacante(Monstruo atacante) throws ReglaDeBatallaException {
        validarDueloEnCurso();
        Objects.requireNonNull(atacante, "Debes elegir un monstruo atacante.");
        if (!jugadorEnTurno.controla(atacante)) {
            throw new ReglaDeBatallaException(atacante.getNombre() + " no está en el campo de "
                    + jugadorEnTurno.getNombre() + ", que es quien tiene el turno.");
        }
        if (!atacante.estaEnAtaque()) {
            throw new ReglaDeBatallaException(atacante.getNombre() + " está en posición de defensa y no puede atacar.");
        }
        if (atacante.isAtacoEsteTurno()) {
            throw new ReglaDeBatallaException(atacante.getNombre() + " ya atacó en este turno.");
        }
    }

    /**
     * Aplica el resultado del cálculo: reduce LP, manda al cementerio los
     * monstruos destruidos y crea el registro del ataque.
     */
    private RegistroCombate resolver(Monstruo atacante, Monstruo objetivo, ResultadoAtaque resultado)
            throws PersistenciaException {
        Jugador duelistaAtacante = jugadorEnTurno;
        Jugador rival = getRival();
        ultimoResultado = resultado;
        atacante.registrarAtaque();

        duelistaAtacante.recibirDanio(resultado.getDanioAlAtacante());
        rival.recibirDanio(resultado.getDanioAlDefensor());
        if (resultado.isAtacanteDestruido()) {
            duelistaAtacante.enviarAlCementerio(atacante);
        }
        if (resultado.isDefensorDestruido()) {
            rival.enviarAlCementerio(objetivo);
        }

        String afectado = RegistroCombate.NADIE;
        if (resultado.getDanioAlAtacante() > 0) {
            afectado = duelistaAtacante.getNombre();
        } else if (resultado.getDanioAlDefensor() > 0) {
            afectado = rival.getNombre();
        }
        String texto = describir(atacante, objetivo, resultado, afectado);
        if (rival.estaDerrotado()) {
            ganador = duelistaAtacante;
        } else if (duelistaAtacante.estaDerrotado()) {
            ganador = rival;
        }
        if (ganador != null) {
            texto += ". ¡" + ganador.getNombre() + " gana el duelo!";
        }

        RegistroCombate registro = new RegistroCombate(LocalDateTime.now(), turno, duelistaAtacante.getNombre(),
                atacante.getNombre(), rival.getNombre(),
                objetivo == null ? RegistroCombate.SIN_DEFENSOR : objetivo.getNombre(),
                resultado.getTipo(), resultado.getDanioInfligido(), afectado, texto);
        registros.add(registro);
        historial.guardar(registro);
        return registro;
    }

    private static String describir(Monstruo atacante, Monstruo objetivo, ResultadoAtaque resultado, String afectado) {
        if (resultado.getTipo() == TipoEnfrentamiento.ATAQUE_DIRECTO) {
            return afectado + " recibe " + resultado.getDanioInfligido() + " de daño directo";
        }
        String destruidos;
        if (resultado.isAtacanteDestruido() && resultado.isDefensorDestruido()) {
            destruidos = "Destruidos: " + atacante.getNombre() + " y " + objetivo.getNombre();
        } else if (resultado.isAtacanteDestruido()) {
            destruidos = "Destruido: " + atacante.getNombre();
        } else if (resultado.isDefensorDestruido()) {
            destruidos = "Destruido: " + objetivo.getNombre();
        } else {
            destruidos = "Sin destrucciones";
        }
        String danio = resultado.huboDanio()
                ? afectado + " recibe " + resultado.getDanioInfligido() + " de daño"
                : "sin daño";
        return destruidos + "; " + danio;
    }

    private void validarDueloEnCurso() throws ReglaDeBatallaException {
        if (haTerminado()) {
            throw new ReglaDeBatallaException("El duelo terminó: ganó " + ganador.getNombre()
                    + ". Inicia un duelo nuevo para seguir jugando.");
        }
    }
}
