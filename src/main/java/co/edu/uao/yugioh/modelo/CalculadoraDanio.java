package co.edu.uao.yugioh.modelo;

import java.util.Objects;

/**
 * Lógica matemática del cálculo de daño según la tabla oficial de posiciones.
 * Es independiente de la interfaz para poder validarla con JUnit.
 * <ul>
 * <li><b>Ataque vs Ataque:</b> se destruye el monstruo con menos ATQ y su
 * dueño recibe la diferencia. Si empatan se destruyen ambos y nadie recibe
 * daño (si ambos tienen 0 de ATQ no se destruye ninguno).</li>
 * <li><b>Ataque vs Defensa:</b> si el ATQ supera la DEF el defensor se
 * destruye y nadie recibe daño; si es menor, el atacante recibe la diferencia;
 * si son iguales no pasa nada.</li>
 * <li><b>Ataque directo:</b> el rival recibe todo el ATQ del atacante.</li>
 * </ul>
 *
 * @author simonsati
 */
public final class CalculadoraDanio {

    private CalculadoraDanio() {
        // Clase utilitaria: solo tiene métodos estáticos.
    }

    /**
     * Calcula un ataque contra un monstruo (Ataque vs Ataque o Ataque vs Defensa).
     *
     * @param atacante monstruo que ataca (debe estar en posición de ataque)
     * @param defensor monstruo atacado
     * @return el resultado del cálculo
     */
    public static ResultadoAtaque calcular(Monstruo atacante, Monstruo defensor) {
        validarAtacante(atacante);
        Objects.requireNonNull(defensor, "El monstruo defensor es obligatorio.");
        if (atacante == defensor) {
            throw new IllegalArgumentException("Un monstruo no puede atacarse a sí mismo.");
        }
        return defensor.estaEnAtaque()
                ? ataqueContraAtaque(atacante, defensor)
                : ataqueContraDefensa(atacante, defensor);
    }

    /**
     * Calcula un ataque directo (el rival no tiene monstruos que lo defiendan).
     *
     * @param atacante monstruo que ataca (debe estar en posición de ataque)
     * @return el resultado del cálculo
     */
    public static ResultadoAtaque calcular(Monstruo atacante) {
        validarAtacante(atacante);
        int danio = atacante.getAtaque();
        return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_DIRECTO, 0, danio, false, false,
                atacante.getNombre() + " ataca directamente con " + danio + " de ATQ: el rival recibe "
                + danio + " de daño.");
    }

    private static ResultadoAtaque ataqueContraAtaque(Monstruo atacante, Monstruo defensor) {
        int atq = atacante.getAtaque();
        int atqDefensor = defensor.getAtaque();
        String comparacion = atacante.getNombre() + " (ATQ " + atq + ") contra " + defensor.getNombre()
                + " (ATQ " + atqDefensor + "): ";
        if (atq > atqDefensor) {
            int danio = atq - atqDefensor;
            return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, 0, danio, false, true,
                    comparacion + "se destruye " + defensor.getNombre() + " y su dueño recibe " + danio + " de daño.");
        }
        if (atq < atqDefensor) {
            int danio = atqDefensor - atq;
            return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, danio, 0, true, false,
                    comparacion + "se destruye " + atacante.getNombre() + " y su dueño recibe " + danio + " de daño.");
        }
        if (atq == 0) {
            return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, 0, 0, false, false,
                    comparacion + "ambos tienen 0 de ATQ, así que no se destruye ninguno y nadie recibe daño.");
        }
        return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_ATAQUE, 0, 0, true, true,
                comparacion + "empate, se destruyen los dos monstruos y nadie recibe daño.");
    }

    private static ResultadoAtaque ataqueContraDefensa(Monstruo atacante, Monstruo defensor) {
        int atq = atacante.getAtaque();
        int def = defensor.getDefensa();
        String comparacion = atacante.getNombre() + " (ATQ " + atq + ") contra " + defensor.getNombre()
                + " en defensa (DEF " + def + "): ";
        if (atq > def) {
            return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, 0, 0, false, true,
                    comparacion + "se destruye " + defensor.getNombre() + ". En defensa nadie recibe daño.");
        }
        if (atq < def) {
            int danio = def - atq;
            return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, danio, 0, false, false,
                    comparacion + "no se destruye ninguno y el dueño del atacante recibe " + danio + " de daño.");
        }
        return new ResultadoAtaque(TipoEnfrentamiento.ATAQUE_VS_DEFENSA, 0, 0, false, false,
                comparacion + "empate, no se destruye ninguno y nadie recibe daño.");
    }

    private static void validarAtacante(Monstruo atacante) {
        Objects.requireNonNull(atacante, "El monstruo atacante es obligatorio.");
        if (!atacante.estaEnAtaque()) {
            throw new IllegalArgumentException(atacante.getNombre() + " está en posición de defensa y no puede atacar.");
        }
    }
}
