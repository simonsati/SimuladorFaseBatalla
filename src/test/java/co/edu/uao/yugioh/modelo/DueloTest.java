package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de la clase Duelo: turnos y reglas de la Fase de Batalla.
 *
 * @author simonsati
 */
class DueloTest {

    private Jugador yugi;
    private Jugador kaiba;
    private Duelo duelo;

    @BeforeEach
    void prepararDuelo() {
        yugi = new Jugador("Yugi");
        kaiba = new Jugador("Kaiba");
        duelo = new Duelo(yugi, kaiba);
    }

    @Test
    @DisplayName("RF1: el duelo empieza en el turno 1 con el primer duelista")
    void empiezaElPrimerDuelista() {
        assertEquals(1, duelo.getTurno());
        assertSame(yugi, duelo.getJugadorEnTurno());
        assertSame(kaiba, duelo.getRival());
        assertSame(yugi, duelo.getJugador1());
        assertSame(kaiba, duelo.getJugador2());
    }

    @Test
    @DisplayName("RF1: terminar el turno alterna a los duelistas")
    void terminarTurnoAlternaDuelistas() {
        duelo.terminarTurno();
        assertEquals(2, duelo.getTurno());
        assertSame(kaiba, duelo.getJugadorEnTurno());

        duelo.terminarTurno();
        assertEquals(3, duelo.getTurno());
        assertSame(yugi, duelo.getJugadorEnTurno());
    }

    @Test
    @DisplayName("RF1: los duelistas deben ser distintos y participar en el duelo")
    void duelistasValidos() {
        assertThrows(IllegalArgumentException.class, () -> new Duelo(yugi, new Jugador("yugi")));
        assertThrows(IllegalArgumentException.class, () -> new Duelo(yugi, yugi));
        assertThrows(NullPointerException.class, () -> new Duelo(null, kaiba));
        assertThrows(IllegalArgumentException.class, () -> duelo.getRivalDe(new Jugador("Joey")));
        assertSame(yugi, duelo.getRivalDe(kaiba));
    }
}
