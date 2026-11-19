package co.edu.uao.yugioh.modelo;

import java.util.Objects;

/**
 * Duelo entre dos jugadores. Controla de quién es el turno
 * y en qué número de turno va el duelo.
 *
 * @author simonsati
 */
public class Duelo {

    private final Jugador jugador1;
    private final Jugador jugador2;
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
}
