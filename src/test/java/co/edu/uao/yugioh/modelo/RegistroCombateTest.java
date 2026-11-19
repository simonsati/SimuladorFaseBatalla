package co.edu.uao.yugioh.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas del registro de cada ataque (atacante, defensor, daño y resultado).
 *
 * @author simonsati
 */
class RegistroCombateTest {

    private static final LocalDateTime FECHA = LocalDateTime.of(2026, 11, 19, 21, 5, 42, 987_000_000);

    private static RegistroCombate crear(int turno, int danio, String resultado) {
        return new RegistroCombate(FECHA, turno, "Yugi", "Mago Oscuro", "Kaiba", "Cyber Dragón",
                TipoEnfrentamiento.ATAQUE_VS_ATAQUE, danio, "Kaiba", resultado);
    }

    @Test
    @DisplayName("El registro guarda los datos del ataque y la fecha sin milisegundos")
    void guardaLosDatos() {
        RegistroCombate registro = crear(2, 400, "Destruido: Cyber Dragón; Kaiba recibe 400 de daño");

        assertEquals("2026-11-19 21:05:42", registro.getFechaHoraTexto());
        assertEquals(0, registro.getFechaHora().getNano());
        assertEquals(2, registro.getTurno());
        assertEquals("Mago Oscuro", registro.getAtacante());
        assertEquals("Cyber Dragón", registro.getDefensor());
        assertEquals(400, registro.getDanio());
        assertFalse(registro.esAtaqueDirecto());
        assertTrue(registro.toString().contains("Turno 2"));
    }

    @Test
    @DisplayName("Turno, daño y campos de texto se validan")
    void validaciones() {
        assertThrows(IllegalArgumentException.class, () -> crear(0, 400, "x"));
        assertThrows(IllegalArgumentException.class, () -> crear(1, -5, "x"));
        assertThrows(IllegalArgumentException.class, () -> crear(1, 400, " "));
        assertThrows(NullPointerException.class, () -> new RegistroCombate(null, 1, "Yugi", "Mago Oscuro",
                "Kaiba", "Kuriboh", TipoEnfrentamiento.ATAQUE_VS_DEFENSA, 0, RegistroCombate.NADIE, "x"));
    }

    @Test
    @DisplayName("Dos registros con los mismos datos son iguales")
    void igualdad() {
        RegistroCombate a = crear(1, 400, "Destruido: Cyber Dragón; Kaiba recibe 400 de daño");
        RegistroCombate b = crear(1, 400, "Destruido: Cyber Dragón; Kaiba recibe 400 de daño");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, crear(2, 400, "Destruido: Cyber Dragón; Kaiba recibe 400 de daño"));
    }
}
