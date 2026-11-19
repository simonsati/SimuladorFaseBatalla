package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private HistorialEnMemoria historial;
    private Duelo duelo;

    @BeforeEach
    void prepararDuelo() {
        yugi = new Jugador("Yugi");
        kaiba = new Jugador("Kaiba");
        historial = new HistorialEnMemoria();
        duelo = new Duelo(yugi, kaiba, historial);
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
    void terminarTurnoAlternaDuelistas() throws ReglaDeBatallaException {
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
        assertThrows(IllegalArgumentException.class, () -> new Duelo(yugi, new Jugador("yugi"), historial));
        assertThrows(IllegalArgumentException.class, () -> new Duelo(yugi, yugi, historial));
        assertThrows(NullPointerException.class, () -> new Duelo(null, kaiba, historial));
        assertThrows(NullPointerException.class, () -> new Duelo(yugi, kaiba, null));
        assertThrows(IllegalArgumentException.class, () -> duelo.getRivalDe(new Jugador("Joey")));
        assertSame(yugi, duelo.getRivalDe(kaiba));
    }

    @Test
    @DisplayName("RF2: se invoca un monstruo en el campo del duelista elegido")
    void invocarEnElCampoDelDuelista() throws ReglaDeBatallaException {
        Monstruo dragon = new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500);

        duelo.invocar(kaiba, dragon);

        assertTrue(kaiba.controla(dragon));
        assertEquals(0, yugi.getCampo().size());
    }

    @Test
    @DisplayName("RF2: un monstruo que ya está en juego no se puede invocar otra vez")
    void monstruoYaEnJuego() throws ReglaDeBatallaException {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        duelo.invocar(yugi, mago);

        assertThrows(ReglaDeBatallaException.class, () -> duelo.invocar(kaiba, mago));
        assertThrows(IllegalArgumentException.class,
                () -> duelo.invocar(new Jugador("Joey"), new MonstruoNormal("Bebé Dragón", 1200, 700)));
    }

    @Test
    @DisplayName("RF3: Ataque vs Ataque reduce los LP del perdedor y destruye su monstruo")
    void ataqueEntreMonstruos() throws Exception {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        Monstruo cyber = new MonstruoEfecto("Cyber Dragón", 2100, 1600, Posicion.ATAQUE, "");
        duelo.invocar(yugi, mago);
        duelo.invocar(kaiba, cyber);

        RegistroCombate registro = duelo.atacar(mago, cyber);

        assertEquals(8000, yugi.getPuntosVida());
        assertEquals(7600, kaiba.getPuntosVida());
        assertFalse(kaiba.controla(cyber));
        assertEquals(1, kaiba.getCementerio().size());
        assertTrue(yugi.controla(mago));
        assertEquals("Mago Oscuro", registro.getAtacante());
        assertEquals("Cyber Dragón", registro.getDefensor());
        assertEquals("Yugi", registro.getDuelistaAtacante());
        assertEquals("Kaiba", registro.getDuelistaDefensor());
        assertEquals(400, registro.getDanio());
        assertEquals("Kaiba", registro.getAfectado());
        assertEquals(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, registro.getTipo());
        assertEquals("Destruido: Cyber Dragón; Kaiba recibe 400 de daño", registro.getResultado());
        assertEquals(1, duelo.getRegistros().size());
        assertSame(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, duelo.getUltimoResultado().getTipo());
    }

    @Test
    @DisplayName("RF3: si el atacante pierde contra una defensa, su dueño recibe el daño")
    void ataqueContraDefensaMasFuerte() throws Exception {
        Monstruo guardian = new MonstruoNormal("Guardián Celta", 1400, 1200);
        Monstruo soldado = new MonstruoNormal("Soldado Gigante de Piedra", 1300, 2000, Posicion.DEFENSA, "");
        duelo.invocar(yugi, guardian);
        duelo.invocar(kaiba, soldado);

        RegistroCombate registro = duelo.atacar(guardian, soldado);

        assertEquals(7400, yugi.getPuntosVida());
        assertEquals(8000, kaiba.getPuntosVida());
        assertTrue(kaiba.controla(soldado));
        assertEquals("Yugi", registro.getAfectado());
        assertEquals("Sin destrucciones; Yugi recibe 600 de daño", registro.getResultado());
    }

    @Test
    @DisplayName("RF3: el ataque directo solo se permite si el rival no tiene monstruos")
    void ataqueDirecto() throws Exception {
        Monstruo dragon = new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500);
        Monstruo kuriboh = new MonstruoEfecto("Kuriboh", 300, 200, Posicion.DEFENSA, "");
        duelo.invocar(yugi, dragon);
        duelo.invocar(kaiba, kuriboh);

        assertThrows(ReglaDeBatallaException.class, () -> duelo.atacar(dragon));

        duelo.atacar(dragon, kuriboh);
        assertFalse(kaiba.tieneMonstruosEnCampo());
        duelo.terminarTurno();
        duelo.terminarTurno();
        RegistroCombate registro = duelo.atacar(dragon);

        assertEquals(5000, kaiba.getPuntosVida());
        assertTrue(registro.esAtaqueDirecto());
        assertEquals(RegistroCombate.SIN_DEFENSOR, registro.getDefensor());
        assertEquals("Kaiba recibe 3000 de daño directo", registro.getResultado());
    }

    @Test
    @DisplayName("RF3: solo atacan los monstruos del duelista en turno, en posición de ataque y contra el rival")
    void validacionesDelAtaque() throws ReglaDeBatallaException {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        Monstruo elfa = new MonstruoNormal("Elfa Mística", 800, 2000, Posicion.DEFENSA, "");
        Monstruo dragon = new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500);
        duelo.invocar(yugi, mago);
        duelo.invocar(yugi, elfa);
        duelo.invocar(kaiba, dragon);

        assertThrows(ReglaDeBatallaException.class, () -> duelo.atacar(dragon, mago));
        assertThrows(ReglaDeBatallaException.class, () -> duelo.atacar(elfa, dragon));
        assertThrows(ReglaDeBatallaException.class, () -> duelo.atacar(mago, elfa));
        assertThrows(NullPointerException.class, () -> duelo.atacar(mago, null));
        assertEquals(8000, yugi.getPuntosVida());
        assertEquals(8000, kaiba.getPuntosVida());
        assertTrue(duelo.getRegistros().isEmpty());
        assertNull(duelo.getUltimoResultado());
    }

    @Test
    @DisplayName("RF3: cada monstruo ataca una sola vez por turno")
    void unAtaquePorTurno() throws Exception {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        duelo.invocar(yugi, mago);
        duelo.atacar(mago);

        assertThrows(ReglaDeBatallaException.class, () -> duelo.atacar(mago));
        assertTrue(yugi.listarMonstruosQuePuedenAtacar().isEmpty());

        duelo.terminarTurno();
        duelo.terminarTurno();
        assertEquals(1, yugi.listarMonstruosQuePuedenAtacar().size());
    }

    @Test
    @DisplayName("RF3: el cambio de posición se permite una vez por turno, sin haber atacado y solo con monstruos propios")
    void cambioDePosicion() throws Exception {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        Monstruo guardian = new MonstruoNormal("Guardián Celta", 1400, 1200);
        Monstruo dragon = new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500);
        duelo.invocar(yugi, mago);
        duelo.invocar(yugi, guardian);
        duelo.invocar(kaiba, dragon);

        duelo.cambiarPosicion(mago);
        assertEquals(Posicion.DEFENSA, mago.getPosicion());
        assertThrows(ReglaDeBatallaException.class, () -> duelo.cambiarPosicion(mago));
        assertThrows(ReglaDeBatallaException.class, () -> duelo.cambiarPosicion(dragon));

        duelo.atacar(guardian, dragon);
        assertFalse(yugi.controla(guardian));

        duelo.terminarTurno();
        duelo.cambiarPosicion(dragon);
        assertEquals(Posicion.DEFENSA, dragon.getPosicion());
    }

    @Test
    @DisplayName("RF3: cuando los LP del rival llegan a 0 el duelo termina y hay ganador")
    void finDelDuelo() throws Exception {
        Duelo corto = new Duelo(new Jugador("Yugi", 1000), new Jugador("Kaiba", 1000), historial);
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        corto.invocar(corto.getJugador1(), mago);

        RegistroCombate registro = corto.atacar(mago);

        assertTrue(corto.haTerminado());
        assertSame(corto.getJugador1(), corto.getGanador());
        assertEquals(0, corto.getJugador2().getPuntosVida());
        assertTrue(registro.getResultado().endsWith("¡Yugi gana el duelo!"));
        assertThrows(ReglaDeBatallaException.class, () -> corto.terminarTurno());
        assertThrows(ReglaDeBatallaException.class,
                () -> corto.invocar(corto.getJugador2(), new MonstruoNormal("Tarde", 100, 100)));
    }

    @Test
    @DisplayName("RF4: cada ataque se guarda en el historial")
    void cadaAtaqueSeGuardaEnElHistorial() throws Exception {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        Monstruo elfa = new MonstruoNormal("Elfa Mística", 800, 2000, Posicion.DEFENSA, "");
        duelo.invocar(yugi, mago);
        duelo.invocar(kaiba, elfa);

        RegistroCombate registro = duelo.atacar(mago, elfa);

        assertEquals(1, historial.cargar().size());
        assertEquals(registro, historial.cargar().get(0));
        assertEquals(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, registro.getTipo());
        assertEquals(RegistroCombate.NADIE, registro.getAfectado());
    }

    @Test
    @DisplayName("RF4: si falla el guardado, el ataque ya resuelto se conserva y se informa el error")
    void errorAlGuardarElHistorial() throws Exception {
        Monstruo mago = new MonstruoNormal("Mago Oscuro", 2500, 2100);
        duelo.invocar(yugi, mago);
        historial.fallar = true;

        assertThrows(PersistenciaException.class, () -> duelo.atacar(mago));
        assertEquals(5500, kaiba.getPuntosVida());
        assertEquals(1, duelo.getRegistros().size());
    }

    /**
     * Historial en memoria para probar el duelo sin escribir archivos.
     */
    private static class HistorialEnMemoria implements HistorialCombate {

        private final List<RegistroCombate> registros = new ArrayList<>();
        private boolean fallar;

        @Override
        public void guardar(RegistroCombate registro) throws PersistenciaException {
            if (fallar) {
                throw new PersistenciaException("Fallo simulado", new IOException("disco lleno"));
            }
            registros.add(registro);
        }

        @Override
        public List<RegistroCombate> cargar() {
            return new ArrayList<>(registros);
        }

        @Override
        public void limpiar() {
            registros.clear();
        }

        @Override
        public String getUbicacion() {
            return "memoria";
        }
    }
}
