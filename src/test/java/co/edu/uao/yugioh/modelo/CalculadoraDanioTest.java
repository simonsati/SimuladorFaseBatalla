package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de la tabla oficial de cálculo de daño (RF3).
 *
 * @author simonsati
 */
class CalculadoraDanioTest {

    private static Monstruo enAtaque(String nombre, int ataque, int defensa) {
        return new MonstruoNormal(nombre, ataque, defensa, Posicion.ATAQUE, "");
    }

    private static Monstruo enDefensa(String nombre, int ataque, int defensa) {
        return new MonstruoNormal(nombre, ataque, defensa, Posicion.DEFENSA, "");
    }

    @Test
    @DisplayName("Ataque vs Ataque: gana el atacante, se destruye el defensor y su dueño recibe la diferencia")
    void ataqueContraAtaqueGanaAtacante() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Mago Oscuro", 2500, 2100),
                enAtaque("Cyber Dragón", 2100, 1600));

        assertEquals(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, r.getTipo());
        assertEquals(400, r.getDanioAlDefensor());
        assertEquals(0, r.getDanioAlAtacante());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
        assertEquals(400, r.getDanioInfligido());
    }

    @Test
    @DisplayName("Ataque vs Ataque: pierde el atacante, se destruye y su dueño recibe la diferencia")
    void ataqueContraAtaquePierdeAtacante() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Guardián Celta", 1400, 1200),
                enAtaque("Dragón Blanco de Ojos Azules", 3000, 2500));

        assertEquals(1600, r.getDanioAlAtacante());
        assertEquals(0, r.getDanioAlDefensor());
        assertTrue(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    @DisplayName("Ataque vs Ataque: con el mismo ATQ se destruyen ambos y nadie recibe daño")
    void ataqueContraAtaqueEmpate() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Mago Oscuro", 2500, 2100),
                enAtaque("Calavera Convocada", 2500, 1200));

        assertTrue(r.isAtacanteDestruido());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.huboDanio());
    }

    @Test
    @DisplayName("Ataque vs Ataque: si ambos tienen 0 de ATQ ninguno se destruye")
    void ataqueContraAtaqueAmbosEnCero() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Ficha A", 0, 0), enAtaque("Ficha B", 0, 0));

        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
        assertEquals(0, r.getDanioInfligido());
    }

    @Test
    @DisplayName("Ataque vs Defensa: si el ATQ supera la DEF el defensor se destruye y nadie recibe daño")
    void ataqueContraDefensaGanaAtacante() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Mago Oscuro", 2500, 2100),
                enDefensa("Elfa Mística", 800, 2000));

        assertEquals(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, r.getTipo());
        assertTrue(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.huboDanio());
    }

    @Test
    @DisplayName("Ataque vs Defensa: si el ATQ es igual a la DEF no pasa nada")
    void ataqueContraDefensaEmpate() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Soldado", 2000, 1000),
                enDefensa("Elfa Mística", 800, 2000));

        assertFalse(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
        assertEquals(0, r.getDanioInfligido());
    }

    @Test
    @DisplayName("Ataque vs Defensa: si el ATQ es menor que la DEF el atacante recibe la diferencia")
    void ataqueContraDefensaPierdeAtacante() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Guardián Celta", 1400, 1200),
                enDefensa("Soldado Gigante de Piedra", 1300, 2000));

        assertEquals(600, r.getDanioAlAtacante());
        assertEquals(0, r.getDanioAlDefensor());
        assertFalse(r.isDefensorDestruido());
        assertFalse(r.isAtacanteDestruido());
    }

    @Test
    @DisplayName("Ataque vs Defensa: se compara contra la DEF aunque el defensor tenga más ATQ")
    void ataqueContraDefensaUsaLaDefensa() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Guardián Celta", 1400, 1200),
                enDefensa("Calavera Convocada", 2500, 1200));

        assertTrue(r.isDefensorDestruido());
        assertFalse(r.huboDanio());
    }

    @Test
    @DisplayName("Ataque directo (sobrecarga): el rival recibe todo el ATQ")
    void ataqueDirecto() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Dragón Blanco de Ojos Azules", 3000, 2500));

        assertEquals(TipoEnfrentamiento.ATAQUE_DIRECTO, r.getTipo());
        assertEquals(3000, r.getDanioAlDefensor());
        assertEquals(0, r.getDanioAlAtacante());
        assertFalse(r.isAtacanteDestruido());
        assertFalse(r.isDefensorDestruido());
    }

    @Test
    @DisplayName("Un monstruo en posición de defensa no puede atacar")
    void atacanteEnDefensa() {
        Monstruo elfa = enDefensa("Elfa Mística", 800, 2000);

        assertThrows(IllegalArgumentException.class, () -> CalculadoraDanio.calcular(elfa));
        assertThrows(IllegalArgumentException.class,
                () -> CalculadoraDanio.calcular(elfa, enAtaque("Kuriboh", 300, 200)));
    }

    @Test
    @DisplayName("Datos inválidos: atacante o defensor nulos y ataque contra sí mismo")
    void datosInvalidos() {
        Monstruo mago = enAtaque("Mago Oscuro", 2500, 2100);

        assertThrows(NullPointerException.class, () -> CalculadoraDanio.calcular(null));
        assertThrows(NullPointerException.class, () -> CalculadoraDanio.calcular(mago, null));
        assertThrows(IllegalArgumentException.class, () -> CalculadoraDanio.calcular(mago, mago));
    }

    @Test
    @DisplayName("La descripción explica el resultado con los nombres de los monstruos")
    void descripcionDelResultado() {
        ResultadoAtaque r = CalculadoraDanio.calcular(enAtaque("Guardián Celta", 1400, 1200),
                enDefensa("Soldado Gigante de Piedra", 1300, 2000));

        assertTrue(r.getDescripcion().contains("Guardián Celta"));
        assertTrue(r.getDescripcion().contains("600"));
        assertEquals(r.getDescripcion(), r.toString());
    }

    @Test
    @DisplayName("Los tipos de enfrentamiento se recuperan desde su etiqueta")
    void tiposDeEnfrentamiento() {
        assertEquals(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, TipoEnfrentamiento.desdeEtiqueta("Ataque vs Defensa"));
        assertEquals(TipoEnfrentamiento.ATAQUE_DIRECTO, TipoEnfrentamiento.desdeEtiqueta("ataque directo"));
        assertThrows(IllegalArgumentException.class, () -> TipoEnfrentamiento.desdeEtiqueta("Magia"));
    }
}
