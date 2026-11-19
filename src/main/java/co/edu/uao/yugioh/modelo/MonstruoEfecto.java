package co.edu.uao.yugioh.modelo;

/**
 * Monstruo de Efecto. El efecto se guarda como texto informativo: el simulador
 * aplica únicamente las reglas de daño de la Fase de Batalla.
 *
 * @author simonsati
 */
public class MonstruoEfecto extends Monstruo {

    /** Nombre del tipo que se muestra en la interfaz. */
    public static final String TIPO = "Efecto";

    /** Longitud máxima del texto del efecto. */
    public static final int LONGITUD_MAXIMA_EFECTO = 120;

    private String efecto;

    public MonstruoEfecto(String nombre, int ataque, int defensa, Posicion posicion, String efecto) {
        super(nombre, ataque, defensa, posicion);
        setEfecto(efecto);
    }

    public String getEfecto() {
        return efecto;
    }

    public final void setEfecto(String efecto) {
        String texto = efecto == null ? "" : efecto.trim();
        if (texto.length() > LONGITUD_MAXIMA_EFECTO) {
            throw new IllegalArgumentException("El efecto admite máximo " + LONGITUD_MAXIMA_EFECTO + " caracteres.");
        }
        this.efecto = texto;
    }

    @Override
    public String tipoDeCarta() {
        return TIPO;
    }

    @Override
    public String describir() {
        return efecto.isEmpty() ? "Efecto sin descripción." : "Efecto: " + efecto;
    }

    @Override
    public String toString() {
        return "[" + TIPO + "] " + super.toString();
    }
}
