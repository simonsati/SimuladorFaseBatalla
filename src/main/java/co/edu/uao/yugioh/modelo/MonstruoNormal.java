package co.edu.uao.yugioh.modelo;

/**
 * Monstruo Normal: no tiene efecto, solo un texto de ambientación.
 *
 * @author simonsati
 */
public class MonstruoNormal extends Monstruo {

    /** Nombre del tipo que se muestra en la interfaz. */
    public static final String TIPO = "Normal";

    /** Longitud máxima de la descripción. */
    public static final int LONGITUD_MAXIMA_DESCRIPCION = 120;

    private String descripcion;

    public MonstruoNormal(String nombre, int ataque, int defensa, Posicion posicion, String descripcion) {
        super(nombre, ataque, defensa, posicion);
        setDescripcion(descripcion);
    }

    /**
     * Crea un monstruo Normal en posición de ataque y sin descripción.
     */
    public MonstruoNormal(String nombre, int ataque, int defensa) {
        this(nombre, ataque, defensa, Posicion.ATAQUE, "");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public final void setDescripcion(String descripcion) {
        String texto = descripcion == null ? "" : descripcion.trim();
        if (texto.length() > LONGITUD_MAXIMA_DESCRIPCION) {
            throw new IllegalArgumentException(
                    "La descripción admite máximo " + LONGITUD_MAXIMA_DESCRIPCION + " caracteres.");
        }
        this.descripcion = texto;
    }

    @Override
    public String tipoDeCarta() {
        return TIPO;
    }

    @Override
    public String describir() {
        return descripcion.isEmpty() ? "Monstruo Normal sin descripción." : descripcion;
    }

    @Override
    public String toString() {
        return "[" + TIPO + "] " + super.toString();
    }
}
