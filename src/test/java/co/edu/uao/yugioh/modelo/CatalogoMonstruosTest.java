package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de los monstruos de ejemplo.
 *
 * @author simonsati
 */
class CatalogoMonstruosTest {

    @Test
    @DisplayName("Los mazos de ejemplo caben en el campo y tienen datos válidos")
    void mazosDeEjemplo() throws ReglaDeBatallaException {
        Jugador yugi = new Jugador("Yugi");
        Jugador kaiba = new Jugador("Kaiba");
        for (Monstruo monstruo : CatalogoMonstruos.crearMazoDeYugi()) {
            yugi.invocar(monstruo);
        }
        for (Monstruo monstruo : CatalogoMonstruos.crearMazoDeKaiba()) {
            kaiba.invocar(monstruo);
        }

        assertEquals(4, yugi.getCampo().size());
        assertEquals(3, kaiba.getCampo().size());
    }

    @Test
    @DisplayName("El catálogo trae monstruos de los dos tipos y crea objetos nuevos cada vez")
    void catalogoCompleto() {
        List<Monstruo> catalogo = CatalogoMonstruos.crearCatalogo();

        assertEquals(11, catalogo.size());
        assertTrue(catalogo.stream().anyMatch(m -> m instanceof MonstruoNormal));
        assertTrue(catalogo.stream().anyMatch(m -> m instanceof MonstruoEfecto));
        for (Monstruo monstruo : catalogo) {
            assertTrue(monstruo.getAtaque() >= Monstruo.PUNTOS_MINIMOS && monstruo.getAtaque() <= Monstruo.PUNTOS_MAXIMOS);
            assertTrue(monstruo.getDefensa() >= Monstruo.PUNTOS_MINIMOS && monstruo.getDefensa() <= Monstruo.PUNTOS_MAXIMOS);
        }
        assertNotSame(CatalogoMonstruos.crearMazoDeYugi().get(0), CatalogoMonstruos.crearMazoDeYugi().get(0));
    }
}
