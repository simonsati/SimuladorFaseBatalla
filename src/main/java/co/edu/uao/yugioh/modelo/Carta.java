package co.edu.uao.yugioh.modelo;

/**
 * Carta genérica del juego. Es abstracta: cada tipo de carta define su propio
 * tipo con {@link #tipoDeCarta()}.
 *
 * @author simonsati
 */
public abstract class Carta {

    /** Longitud máxima del nombre de una carta. */
    public static final int LONGITUD_MAXIMA_NOMBRE = 40;

    /** Contador compartido por todas las cartas para asignar identificadores únicos. */
    private static int cartasCreadas = 0;

    private final int id;
    private String nombre;

    protected Carta(String nombre) {
        this.nombre = validarNombre(nombre);
        this.id = ++cartasCreadas;
    }

    /**
     * @return identificador único de la carta (no cambia nunca)
     */
    public final int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarNombre(nombre);
    }

    /**
     * @return el tipo de la carta que se muestra en tablas y reportes
     */
    public abstract String tipoDeCarta();

    /**
     * @return cuántas cartas se han creado desde que inició el programa
     */
    public static int getCartasCreadas() {
        return cartasCreadas;
    }

    protected static String validarNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la carta no puede estar vacío.");
        }
        String limpio = nombre.trim();
        if (limpio.length() > LONGITUD_MAXIMA_NOMBRE) {
            throw new IllegalArgumentException(
                    "El nombre de la carta admite máximo " + LONGITUD_MAXIMA_NOMBRE + " caracteres.");
        }
        return limpio;
    }
}
