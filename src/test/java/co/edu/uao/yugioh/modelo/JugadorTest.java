package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de la gestión de Puntos de Vida (LP)
 * de cada jugador.
 *
 * @author simonsati
 */
class JugadorTest {

    @Test
    @DisplayName("RF1: un duelista nuevo empieza con los 8000 LP oficiales")
    void empiezaConLosLpOficiales() {
        Jugador jugador = new Jugador("Yugi");

        assertEquals("Yugi", jugador.getNombre());
        assertEquals(Jugador.LP_OFICIALES, jugador.getLpIniciales());
        assertEquals(8000, jugador.getPuntosVida());
        assertFalse(jugador.estaDerrotado());
    }

    @Test
    @DisplayName("RF1: se pueden asignar LP iniciales personalizados y el nombre se limpia")
    void lpInicialesPersonalizados() {
        Jugador jugador = new Jugador("  Kaiba  ", 4000);

        assertEquals("Kaiba", jugador.getNombre());
        assertEquals(4000, jugador.getLpIniciales());
        assertEquals(4000, jugador.getPuntosVida());
    }

    @Test
    @DisplayName("RF1: LP iniciales fuera de rango lanzan excepción")
    void lpInicialesFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Yugi", Jugador.LP_MINIMOS - 1));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Yugi", Jugador.LP_MAXIMOS + 1));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Yugi", -8000));
    }

    @Test
    @DisplayName("RF1: el nombre no puede estar vacío ni ser demasiado largo")
    void nombreInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Jugador(""));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("   "));
        assertThrows(IllegalArgumentException.class, () -> new Jugador(null));
        assertThrows(IllegalArgumentException.class, () -> new Jugador("Un nombre de duelista largo"));
    }

    @Test
    @DisplayName("Gestión de LP: recibir daño resta los puntos de vida")
    void recibirDanioRestaLp() {
        Jugador jugador = new Jugador("Yugi");

        int restantes = jugador.recibirDanio(1500);

        assertEquals(6500, restantes);
        assertEquals(6500, jugador.getPuntosVida());
    }

    @Test
    @DisplayName("Gestión de LP: los LP nunca quedan negativos y el duelista queda derrotado")
    void lpNoBajanDeCero() {
        Jugador jugador = new Jugador("Yugi", 1000);

        assertEquals(0, jugador.recibirDanio(3000));
        assertTrue(jugador.estaDerrotado());
    }

    @Test
    @DisplayName("Gestión de LP: el daño acumulado puede dejar los LP exactamente en cero")
    void danioAcumuladoHastaCero() {
        Jugador jugador = new Jugador("Kaiba");

        jugador.recibirDanio(3000);
        assertFalse(jugador.estaDerrotado());
        jugador.recibirDanio(5000);

        assertEquals(0, jugador.getPuntosVida());
        assertTrue(jugador.estaDerrotado());
    }

    @Test
    @DisplayName("Gestión de LP: un daño de cero no cambia los LP")
    void danioCeroNoCambiaLp() {
        Jugador jugador = new Jugador("Yugi");

        jugador.recibirDanio(0);

        assertEquals(8000, jugador.getPuntosVida());
    }

    @Test
    @DisplayName("Gestión de LP: un daño negativo lanza excepción")
    void danioNegativoLanzaExcepcion() {
        Jugador jugador = new Jugador("Yugi");

        assertThrows(IllegalArgumentException.class, () -> jugador.recibirDanio(-100));
        assertEquals(8000, jugador.getPuntosVida());
    }
}
