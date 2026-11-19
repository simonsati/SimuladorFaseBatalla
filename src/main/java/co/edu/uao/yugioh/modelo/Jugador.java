package co.edu.uao.yugioh.modelo;

/**
 * Duelista que participa en la Fase de Batalla. Administra sus Puntos de Vida
 * (LP).
 *
 * @author simonsati
 */
public class Jugador {

    /** LP con los que empieza cada duelista en un duelo oficial. */
    public static final int LP_OFICIALES = 8000;

    /** LP iniciales mínimos que acepta el simulador. */
    public static final int LP_MINIMOS = 100;

    /** LP iniciales máximos que acepta el simulador. */
    public static final int LP_MAXIMOS = 99999;

    /** Longitud máxima del nombre de un duelista. */
    public static final int LONGITUD_MAXIMA_NOMBRE = 20;

    private final String nombre;
    private final int lpIniciales;
    private int puntosVida;

    /**
     * Crea un duelista con los LP oficiales (8000).
     */
    public Jugador(String nombre) {
        this(nombre, LP_OFICIALES);
    }

    /**
     * Crea un duelista con un contador inicial de LP personalizado.
     *
     * @throws IllegalArgumentException si el nombre está vacío o los LP están fuera de rango
     */
    public Jugador(String nombre, int lpIniciales) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del duelista no puede estar vacío.");
        }
        if (nombre.trim().length() > LONGITUD_MAXIMA_NOMBRE) {
            throw new IllegalArgumentException(
                    "El nombre del duelista admite máximo " + LONGITUD_MAXIMA_NOMBRE + " caracteres.");
        }
        if (lpIniciales < LP_MINIMOS || lpIniciales > LP_MAXIMOS) {
            throw new IllegalArgumentException(
                    "Los LP iniciales deben estar entre " + LP_MINIMOS + " y " + LP_MAXIMOS + ".");
        }
        this.nombre = nombre.trim();
        this.lpIniciales = lpIniciales;
        this.puntosVida = lpIniciales;
    }

    /**
     * Resta daño a los Puntos de Vida. Los LP nunca quedan por debajo de cero.
     *
     * @param danio daño recibido (no puede ser negativo)
     * @return los LP que le quedan al duelista
     */
    public int recibirDanio(int danio) {
        if (danio < 0) {
            throw new IllegalArgumentException("El daño no puede ser negativo.");
        }
        puntosVida = Math.max(0, puntosVida - danio);
        return puntosVida;
    }

    /**
     * @return {@code true} si los LP llegaron a cero
     */
    public boolean estaDerrotado() {
        return puntosVida == 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getLpIniciales() {
        return lpIniciales;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    @Override
    public String toString() {
        return nombre + " (" + puntosVida + " LP)";
    }
}
