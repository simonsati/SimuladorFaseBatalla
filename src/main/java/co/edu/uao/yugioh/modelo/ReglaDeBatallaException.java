package co.edu.uao.yugioh.modelo;

/**
 * Se lanza cuando una acción del duelo viola una regla del juego (por ejemplo,
 * atacar con un monstruo en defensa o invocar con el campo lleno).
 *
 * @author simonsati
 */
public class ReglaDeBatallaException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReglaDeBatallaException(String mensaje) {
        super(mensaje);
    }
}
