package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de las cartas de monstruo (RF2): atributos, validaciones,
 * herencia y polimorfismo.
 *
 * @author simonsati
 */
class MonstruoTest {

    @Test
    @DisplayName("RF2: un monstruo Normal guarda nombre, ATQ, DEF y posición")
    void creaMonstruoNormal() {
        MonstruoNormal mago = new MonstruoNormal("Mago Oscuro", 2500, 2100, Posicion.ATAQUE, "Hechicero oscuro.");

        assertEquals("Mago Oscuro", mago.getNombre());
        assertEquals(2500, mago.getAtaque());
        assertEquals(2100, mago.getDefensa());
        assertEquals(Posicion.ATAQUE, mago.getPosicion());
        assertEquals("Hechicero oscuro.", mago.describir());
        assertTrue(mago.estaEnAtaque());
    }

    @Test
    @DisplayName("Sobrecarga: el constructor corto crea el monstruo en ataque y sin descripción")
    void constructorSobrecargado() {
        MonstruoNormal guardian = new MonstruoNormal("Guardián Celta", 1400, 1200);

        assertEquals(Posicion.ATAQUE, guardian.getPosicion());
        assertEquals("", guardian.getDescripcion());
        assertEquals("Monstruo Normal sin descripción.", guardian.describir());
    }

    @Test
    @DisplayName("Sobrescritura: cada subclase responde con su propio tipo y detalle")
    void polimorfismoDeSobrescritura() {
        List<Monstruo> monstruos = List.of(
                new MonstruoNormal("Dragón Blanco de Ojos Azules", 3000, 2500),
                new MonstruoEfecto("Cyber Dragón", 2100, 1600, Posicion.ATAQUE, "Invocación especial."));

        assertEquals("Normal", monstruos.get(0).tipoDeCarta());
        assertEquals("Efecto", monstruos.get(1).tipoDeCarta());
        assertEquals("Efecto: Invocación especial.", monstruos.get(1).describir());
        assertTrue(monstruos.get(0).toString().startsWith("[Normal] Dragón Blanco de Ojos Azules"));
        assertTrue(monstruos.get(1).toString().startsWith("[Efecto] Cyber Dragón"));
    }

    @Test
    @DisplayName("RF2: ATQ y DEF deben estar entre 0 y 5000")
    void puntosFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> new MonstruoNormal("Error", -1, 100));
        assertThrows(IllegalArgumentException.class, () -> new MonstruoNormal("Error", 100, 5001));
        MonstruoNormal valido = new MonstruoNormal("Límite", 0, 5000);
        assertEquals(0, valido.getAtaque());
        assertEquals(5000, valido.getDefensa());
        assertThrows(IllegalArgumentException.class, () -> valido.setAtaque(6000));
        assertThrows(IllegalArgumentException.class, () -> valido.setDefensa(-50));
    }

    @Test
    @DisplayName("RF2: el nombre es obligatorio y tiene un límite de caracteres")
    void nombreInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new MonstruoNormal("  ", 100, 100));
        assertThrows(IllegalArgumentException.class,
                () -> new MonstruoNormal("x".repeat(Carta.LONGITUD_MAXIMA_NOMBRE + 1), 100, 100));
        assertThrows(NullPointerException.class,
                () -> new MonstruoEfecto("Sin posición", 100, 100, null, ""));
    }

    @Test
    @DisplayName("static: cada carta recibe un identificador único y consecutivo")
    void identificadoresUnicos() {
        Monstruo primero = new MonstruoNormal("Uno", 100, 100);
        Monstruo segundo = new MonstruoEfecto("Dos", 200, 200, Posicion.DEFENSA, "");

        assertEquals(primero.getId() + 1, segundo.getId());
        assertTrue(Carta.getCartasCreadas() >= segundo.getId());
    }

    @Test
    @DisplayName("RF2: la posición se puede leer desde texto y tiene una opuesta")
    void posiciones() {
        assertEquals(Posicion.DEFENSA, Posicion.ATAQUE.opuesta());
        assertEquals(Posicion.ATAQUE, Posicion.DEFENSA.opuesta());
        assertEquals(Posicion.DEFENSA, Posicion.desdeTexto("defensa"));
        assertEquals(Posicion.ATAQUE, Posicion.desdeTexto(" Ataque "));
        assertThrows(IllegalArgumentException.class, () -> Posicion.desdeTexto("Boca abajo"));
    }

    @Test
    @DisplayName("Los puntos de posición son el ATQ en ataque y la DEF en defensa")
    void puntosDePosicion() {
        Monstruo soldado = new MonstruoNormal("Soldado Gigante de Piedra", 1300, 2000, Posicion.DEFENSA, "");
        assertFalse(soldado.estaEnAtaque());
        assertEquals(2000, soldado.getPuntosDePosicion());

        soldado.setPosicion(Posicion.ATAQUE);
        assertEquals(1300, soldado.getPuntosDePosicion());
    }
}
