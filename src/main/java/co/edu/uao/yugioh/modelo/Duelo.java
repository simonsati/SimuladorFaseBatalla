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
    private final List<RegistroCombate> registros = new ArrayList<>();
    private ResultadoAtaque ultimoResultado;
    private Jugador jugadorEnTurno;
    private int turno;

    /**
     * Crea el duelo. Empieza atacando el primer jugador.
     */
    public Duelo(Jugador jugador1, Jugador jugador2) {
        this.jugador1 = Objects.requireNonNull(jugador1, "Falta el primer duelista.");
        this.jugador2 = Objects.requireNonNull(jugador2, "Falta el segundo duelista.");
        if (jugador1 == jugador2 || jugador1.getNombre().equalsIgnoreCase(jugador2.getNombre())) {
            throw new IllegalArgumentException("Los dos duelistas deben tener nombres diferentes.");
        }
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
     */
    public void terminarTurno() {
        jugadorEnTurno = getRival();
        turno++;
    }

    /**
     * Invoca un monstruo en el campo de uno de los duelistas.
     *
     * @throws ReglaDeBatallaException si el campo está lleno o el monstruo ya está en juego
     */
    public void invocar(Jugador duelista, Monstruo monstruo) throws ReglaDeBatallaException {
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
     */
    public RegistroCombate atacar(Monstruo atacante, Monstruo objetivo) throws ReglaDeBatallaException {
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
     */
    public RegistroCombate atacar(Monstruo atacante) throws ReglaDeBatallaException {
        validarAtacante(atacante);
        Jugador rival = getRival();
        if (rival.tieneMonstruosEnCampo()) {
            throw new ReglaDeBatallaException("No hay ataque directo: " + rival.getNombre()
                    + " todavía tiene monstruos en el campo.");
        }
        return resolver(atacante, null, CalculadoraDanio.calcular(atacante));
    }

    private void validarAtacante(Monstruo atacante) throws ReglaDeBatallaException {
        Objects.requireNonNull(atacante, "Debes elegir un monstruo atacante.");
        if (!jugadorEnTurno.controla(atacante)) {
            throw new ReglaDeBatallaException(atacante.getNombre() + " no está en el campo de "
                    + jugadorEnTurno.getNombre() + ", que es quien tiene el turno.");
        }
        if (!atacante.estaEnAtaque()) {
            throw new ReglaDeBatallaException(atacante.getNombre() + " está en posición de defensa y no puede atacar.");
        }
    }

    /**
     * Aplica el resultado del cálculo: reduce LP, manda al cementerio los
     * monstruos destruidos y crea el registro del ataque.
     */
    private RegistroCombate resolver(Monstruo atacante, Monstruo objetivo, ResultadoAtaque resultado) {
        Jugador duelistaAtacante = jugadorEnTurno;
        Jugador rival = getRival();
        ultimoResultado = resultado;

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

        RegistroCombate registro = new RegistroCombate(LocalDateTime.now(), turno, duelistaAtacante.getNombre(),
                atacante.getNombre(), rival.getNombre(),
                objetivo == null ? RegistroCombate.SIN_DEFENSOR : objetivo.getNombre(),
                resultado.getTipo(), resultado.getDanioInfligido(), afectado, texto);
        registros.add(registro);
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
}
